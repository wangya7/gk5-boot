package wang.bannong.gk5.boot.mybatis1ms;

import com.github.pagehelper.PageInterceptor;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

import javax.sql.DataSource;

import wang.bannong.gk5.boot.mybatis1ms.config.DataSourceDB;

/**
 * SlaveDB
 *
 * @author wang.bannong(inc11003307@gmail.com)
 */
@Configuration
@EnableConfigurationProperties(DataSourceDB.class)
public class SlaveDB {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private DataSourceDB dataSourceDB;

    @Bean
    public SqlSessionTemplate slaveSqlSessionTemplate() {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(slaveDataSource());
        Properties props = new Properties();
        props.setProperty("reasonable", "true");
        props.setProperty("supportMethodsArguments", "true");
        props.setProperty("returnPageInfo", "check");
        props.setProperty("params", "count=countSql");
        PageInterceptor pageInterceptor = new PageInterceptor();
        pageInterceptor.setProperties(props);
        sqlSessionFactory.setPlugins(new Interceptor[]{pageInterceptor});
        try {
            return new SqlSessionTemplate(sqlSessionFactory.getObject());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Bean
    public DataSource slaveDataSource() {
        return DBSupporter.buildPoolProperties(dataSourceDB.getSlave());
    }
}
