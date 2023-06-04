package wang.bannong.gk5.boot.starter.mybatis.datasource;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import wang.bannong.gk5.boot.starter.mybatis.SpringBeanUtils;

/**
 * druid 配置多数据源
 */
@Configuration
public class DruidConfig {
    private final static Logger log = LoggerFactory.getLogger(DruidConfig.class);

    static {
        System.setProperty("druid.mysql.usePingMethod", "false");
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource masterDataSource() {
        log.info("init masterDataSource...");
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.slave")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave", name = "enabled", havingValue = "true")
    public DataSource slaveDataSource() {
        log.info("init slaveDataSource...");
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "dynamicDataSource")
    public DynamicDataSource dataSource() {
        Map<Object, Object> dataSourceMap = new HashMap<>(8);
        DataSource masterDataSource = masterDataSource();
        if (masterDataSource == null) {
            throw new RuntimeException("masterDataSource cannot be null");
        }
        dataSourceMap.put(DataSourceType.MASTER, masterDataSource);
        try {
            DataSource dataSource = (DataSource) SpringBeanUtils.getBean("slaveDataSource");
            dataSourceMap.put(DataSourceType.SLAVE, dataSource);
        } catch (Exception e) {
            log.error("slave DataSource fail.");
        }
        return new DynamicDataSource(masterDataSource, dataSourceMap);
    }
}
