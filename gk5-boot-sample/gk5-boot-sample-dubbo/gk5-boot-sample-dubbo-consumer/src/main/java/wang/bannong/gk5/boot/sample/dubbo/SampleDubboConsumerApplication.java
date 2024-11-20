package wang.bannong.gk5.boot.sample.dubbo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDubbo
@MapperScan(basePackages = "wang.bannong.gk5.boot.sample.dubbo.mapper")
public class SampleDubboConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SampleDubboConsumerApplication.class);
    }
}
