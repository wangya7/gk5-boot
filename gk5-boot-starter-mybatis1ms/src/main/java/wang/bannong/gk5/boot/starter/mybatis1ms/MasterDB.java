package wang.bannong.gk5.boot.starter.mybatis1ms;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

import wang.bannong.gk5.boot.starter.mybatis1ms.config.DataSourceDB;

/**
 * MasterDB
 * 参考：
 * com.zaxxer.hikari.HikariDataSource
 *
 * @author wang.bannong(inc11003307@gmail.com)
 */
@Configuration
@EnableConfigurationProperties(DataSourceDB.class)
@EnableTransactionManagement
public class MasterDB implements TransactionManagementConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(MasterDB.class);

    @Autowired
    private DataSourceDB dataSourceDB;

    @Bean
    // @Scope("prototype") // 参考：https://www.cnblogs.com/daxin/p/3544188.html
    public SqlSessionTemplate masterSqlSessionTemplate() {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(masterDataSource());
        try {
            return new SqlSessionTemplate(sqlSessionFactoryBean.getObject());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Bean
    public DataSource masterDataSource() {
        DataSource dataSource = DBSupporter.buildPoolProperties(dataSourceDB.getMaster());
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(masterDataSource());
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return txManager();
    }
}
