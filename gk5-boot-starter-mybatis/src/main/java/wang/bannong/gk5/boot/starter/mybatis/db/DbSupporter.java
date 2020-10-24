package wang.bannong.gk5.boot.starter.mybatis.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.mapper.MapperScannerConfigurer;

import javax.sql.DataSource;

import wang.bannong.gk5.boot.starter.mybatis.MybatisXConstant;
import wang.bannong.gk5.boot.starter.mybatis.mapper.MapperBeanNameGenerator;

public class DbSupporter {

    private static final Logger log = LogManager.getLogger(DbSupporter.class);

    public static DataSource buildPoolProperties(DbProperties db) {
        HikariConfig config = new HikariConfig();
        String dataSourceUrl = String.format(MybatisXConstant.url, db.getHost(), db.getPort(), db.getDb());
        config.setJdbcUrl(dataSourceUrl);
        config.setDriverClassName(MybatisXConstant.driver);
        config.setUsername(db.getUsername());
        config.setPassword(db.getPassword());
        config.setAutoCommit(true);
        config.setIdleTimeout(600000);
        config.setMinimumIdle(db.getMinIdle());
        config.setMaximumPoolSize(db.getMaxIdle());
        config.setConnectionTimeout(db.getConnectionTimeout());
        config.setPoolName(String.format(MybatisXConstant.poolName, db.getKey()));

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return new HikariDataSource(config);
    }


    public static GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setBanner(false);
        globalConfig.setIdentifierGenerator(new DefaultIdentifierGenerator());
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        dbConfig.setIdType(IdType.ASSIGN_ID);
        globalConfig.setDbConfig(dbConfig);
        return globalConfig;
    }

    /**
     * MyBatis-Spring提供了一个MapperFactoryBean，可以将数据映射接口转为Spring Bean
     * 开发中有很多的接口需要转换为Bean，一个个配置就显得恶心啦，所有出现了这个MapperScannerConfigurer
     * <p>
     * MapperScannerConfigurer将扫描basePackage所指定的包下的所有接口类（包括子类），如果它们在SQL映射
     * 文件中定义过，则将它们动态定义为一个Spring Bean，这样，我们在Service中就可以直接注入映射接口的bean
     */
    public static MapperScannerConfigurer mapperScannerConfigurer(String key, String mappersPath) {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        log.info("DataSource-{} loading MappersPath:{}", key, mappersPath);
        configurer.setBasePackage(mappersPath);
        configurer.setSqlSessionTemplateBeanName(String.format(MybatisXConstant.sqlSessionTemplateBeanName, key));
        configurer.setNameGenerator(new MapperBeanNameGenerator(key));
        return configurer;
    }
}
