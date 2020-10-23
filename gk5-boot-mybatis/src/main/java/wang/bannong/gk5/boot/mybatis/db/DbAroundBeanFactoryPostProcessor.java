package wang.bannong.gk5.boot.mybatis.db;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import javax.sql.DataSource;

@Component
public class DbAroundBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    private static final Logger log = LogManager.getLogger(DbAroundBeanFactoryPostProcessor.class);

    private static final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

    private static String[]           mapperLocationArr;
    private static String             mapperLocations;
    private static String             mappersPath;
    private static String             primary;
    private static List<DbProperties> masters = new ArrayList<>();
    private static List<DbProperties> slaves  = new ArrayList<>();

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;

        YamlMapFactoryBean yamlMapFactoryBean = new YamlMapFactoryBean();
        yamlMapFactoryBean.setResources(new ClassPathResource("application.yml"));
        Map<String, Object> map = (Map<String, Object>) yamlMapFactoryBean.getObject().get("datasource");

        mappersPath = (String) map.get("mappersPath");
        if (mappersPath == null || mappersPath.length() == 0) {
            throw new RuntimeException("mappersPath cannot be null");
        }

        mapperLocations = (String) map.get("mapperLocations");
        if (mapperLocations == null || mapperLocations.length() == 0) {
            throw new RuntimeException("mapperLocations cannot be null");
        }
        mapperLocationArr = mapperLocations.trim().split(",");

        primary = (String) map.get("primary");
        List<Map<String, Object>> items = (List<Map<String, Object>>) map.get("dbs");
        if (items == null || items.size() == 0) {
            return;
        }
        for (Map<String, Object> item : items) {
            DbProperties dbProperties = new DbProperties();
            String key = (String) item.get("key");
            if (key.startsWith(DataSourcePrefix.master.name())) {
                masters.add(dbProperties);
            } else if (key.startsWith(DataSourcePrefix.slave.name())) {
                slaves.add(dbProperties);
            } else {
                throw new RuntimeException("illegal datasource key:" + key);
            }
            dbProperties.setKey(key);
            dbProperties.setHost((String) item.get("host"));
            dbProperties.setPort((Integer) item.get("port"));
            dbProperties.setDb((String) item.get("db"));
            dbProperties.setUsername((String) item.get("username"));
            dbProperties.setPassword((String) item.get("password"));
            dbProperties.setMinIdle((Integer) item.get("minIdle"));
            dbProperties.setMaxIdle((Integer) item.get("maxIdle"));
            dbProperties.setConnectionTimeout((Integer) item.get("connectionTimeout"));
        }
        log.info("init database connections......");

        if (masters != null && masters.size() > 0) {
            DbProperties dbProperties = null;
            // bn on 2020/2/8 默认采用一个主库，后期可以拓展增加多个主库，注意控制事务
            for (DbProperties dbPropertiesConf : masters) {
                if (primary.equals(dbPropertiesConf.getKey())) {
                    dbProperties = dbPropertiesConf;
                    break;
                }
            }
            if (dbProperties == null) {
                throw new RuntimeException("the primary database must be writable and begin with \"maste\"");
            }
            registerMaster(dbProperties, beanFactory);

            log.info("init read-database connections finished");
        }

        if (slaves != null && slaves.size() > 0) {
            for (DbProperties dbProperties : slaves) {
                registerSlave(dbProperties, beanFactory);
            }
            log.info("init write-database connections finished");
        }
    }

    private void registerMaster(DbProperties dbProperties, DefaultListableBeanFactory beanFactory) {
        String key = dbProperties.getKey();
        DataSource dataSource = DbSupporter.buildPoolProperties(dbProperties);
        beanFactory.registerSingleton(key + "DataSource", dataSource);

        // 引用了MyBatis-Plus中的SqlSessionFactoryBean取代org.mybatis.spring.SqlSessionFactoryBean
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setGlobalConfig(DbSupporter.globalConfig());
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setMapperLocations(Stream.of(Optional.ofNullable(mapperLocationArr).orElse(new String[0]))
                                                   .flatMap(location -> Stream.of(getResources(location)))
                                                   .toArray(Resource[]::new));
        String sqlSessionFactoryBeanName = key + "SqlSessionFactory";
        beanFactory.registerSingleton(sqlSessionFactoryBeanName, sqlSessionFactory);

        SqlSessionTemplate sqlSessionTemplate = null;
        try {
            sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory.getObject());
        } catch (Exception e) {
            log.error("init Writable SqlSessionTemplate fail，db={}，exception detail:", dbProperties, e);
            throw new RuntimeException(e);
        }
        String SqlSessionTemplateBeanName = key + "SqlSessionTemplate";
        beanFactory.registerSingleton(SqlSessionTemplateBeanName, sqlSessionTemplate);

        DataSourceTransactionManager txManager = new DataSourceTransactionManager(dataSource);
        beanFactory.registerSingleton(key + "TxManager", txManager);


        beanFactory.registerSingleton(key + "AnnotationDrivenTransactionManager",
                new TransactionManagementConfigurer() {
                    @Override
                    public PlatformTransactionManager annotationDrivenTransactionManager() {
                        return txManager;
                    }
                });

        TransactionTemplate transactionTemplate = new TransactionTemplate(txManager);
        beanFactory.registerSingleton(key + "TransactionTemplate", transactionTemplate);

    }

    private void registerSlave(DbProperties dbProperties, DefaultListableBeanFactory beanFactory) {
        String key = dbProperties.getKey();
        DataSource dataSource = DbSupporter.buildPoolProperties(dbProperties);
        beanFactory.registerSingleton(key + "DataSource", dataSource);

        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setGlobalConfig(DbSupporter.globalConfig());
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setMapperLocations(Stream.of(Optional.ofNullable(mapperLocationArr).orElse(new String[0]))
                                                   .flatMap(location -> Stream.of(getResources(location)))
                                                   .toArray(Resource[]::new));

        String sqlSessionFactoryBeanName = key + "SqlSessionFactory";
        beanFactory.registerSingleton(sqlSessionFactoryBeanName, sqlSessionFactory);

        PaginationInterceptor pageInterceptor = // 开启 count 的 join 优化,只针对 left join !!!
                new PaginationInterceptor().setCountSqlParser(new JsqlParserCountOptimize(true));
        sqlSessionFactory.setPlugins(new Interceptor[]{pageInterceptor});
        SqlSessionTemplate sqlSessionTemplate = null;
        try {
            sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory.getObject());
        } catch (Exception e) {
            log.error("init Readable SqlSessionTemplate fail，db={}，exception detail:", dbProperties, e);
            throw new RuntimeException(e);
        }
        String SqlSessionTemplateBeanName = key + "SqlSessionTemplate";
        beanFactory.registerSingleton(SqlSessionTemplateBeanName, sqlSessionTemplate);
    }


    private Resource[] getResources(String location) {
        try {
            return resourceResolver.getResources(location);
        } catch (IOException e) {
            return new Resource[0];
        }
    }

    public static String[] getMapperLocationArr() {
        return mapperLocationArr;
    }

    public static String getMapperLocations() {
        return mapperLocations;
    }

    public static String getMappersPath() {
        return mappersPath;
    }

    public static String getPrimary() {
        return primary;
    }

    public static List<DbProperties> getMasters() {
        return masters;
    }

    public static List<DbProperties> getSlaves() {
        return slaves;
    }
}
