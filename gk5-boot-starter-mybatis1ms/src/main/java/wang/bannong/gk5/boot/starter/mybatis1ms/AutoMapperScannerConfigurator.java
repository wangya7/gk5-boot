package wang.bannong.gk5.boot.starter.mybatis1ms;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import wang.bannong.gk5.boot.starter.mybatis1ms.config.DataSourceDB;

/**
 * @author wang.bannong(inc11003307@gmail.com)
 */
@Configuration
public class AutoMapperScannerConfigurator implements EnvironmentAware {

    private final static Logger log = LoggerFactory.getLogger(AutoMapperScannerConfigurator.class);

    private static String master = "master";
    private static String slave  = "slave";
    private static String masterSqlSessionTemplate   = "masterSqlSessionTemplate";
    private static String slaveSqlSessionTemplate    = "slaveSqlSessionTemplate";

    private Environment environment;

    /**
     * MyBatis-Spring提供了一个MapperFactoryBean，可以将数据映射接口转为Spring Bean
     * 开发中有很多的接口需要转换为Bean，一个个配置就显得恶心啦，所有出现了这个MapperScannerConfigurer
     *
     * MapperScannerConfigurer将扫描basePackage所指定的包下的所有接口类（包括子类），如果它们在SQL映射
     * 文件中定义过，则将它们动态定义为一个Spring Bean，这样，我们在Service中就可以直接注入映射接口的bean
     *
     * @return MapperScannerConfigurer
     * @throws Exception 异常
     */
    @Bean
    public MapperScannerConfigurer masterMapperScannerConfigurer() throws Exception {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        String mapperPath = environment.getProperty(DataSourceDB.PLACEHOLDER_NAME_MAPPERS_PATH, "");
        log.info("Mybatis加载Mapper路径:{}", mapperPath);
        configurer.setBasePackage(mapperPath);
        configurer.setSqlSessionTemplateBeanName(masterSqlSessionTemplate);
        configurer.setNameGenerator(new MapperBeanNameGenerator(master));
        return configurer;
    }

    @Bean
    public MapperScannerConfigurer slaveMapperScannerConfigurer() throws Exception {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage(environment.getProperty(DataSourceDB.PLACEHOLDER_NAME_MAPPERS_PATH, ""));
        configurer.setSqlSessionTemplateBeanName(slaveSqlSessionTemplate);
        configurer.setNameGenerator(new MapperBeanNameGenerator(slave));
        return configurer;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
