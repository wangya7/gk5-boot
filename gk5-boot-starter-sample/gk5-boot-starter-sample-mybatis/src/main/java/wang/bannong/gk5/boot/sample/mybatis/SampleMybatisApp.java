package wang.bannong.gk5.boot.sample.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(
        exclude = {DataSourceAutoConfiguration.class},
        scanBasePackages = {"wang.bannong.gk5"}
)
@MapperScan("wang.bannong.gk5.boot.sample.mybatis.mapper")
public class SampleMybatisApp {
    public static void main(String[] args) {
        SpringApplication.run(SampleMybatisApp.class, args);
    }
}