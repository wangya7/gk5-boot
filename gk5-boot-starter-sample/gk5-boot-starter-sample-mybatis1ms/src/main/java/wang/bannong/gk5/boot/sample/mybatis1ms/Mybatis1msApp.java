package wang.bannong.gk5.boot.sample.mybatis1ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}, scanBasePackages = {"wang.bannong"})
//@ImportResource("classpath:applicationContext.xml")
public class Mybatis1msApp {
    public static void main(String... args) {
        SpringApplication.run(Mybatis1msApp.class, args);
    }
}
