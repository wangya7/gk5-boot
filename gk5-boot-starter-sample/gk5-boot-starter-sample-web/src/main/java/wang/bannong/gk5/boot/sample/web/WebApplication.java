package wang.bannong.gk5.boot.sample.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"wang.bannong"})
public class WebApplication {
    public static void main(String... args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
