package wang.bannong.gk5.boot.sample.apollo;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"wang.bannong"})
@EnableApolloConfig
public class SampleApolloApp {
    public static void main(String... args) {
        SpringApplication.run(SampleApolloApp.class, args);
    }
}
