package wang.bannong.gk5.boot.sample.dubbo;

import java.util.Date;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import wang.bannong.gk5.boot.sample.dubbo.facade.StudentServiceFacade;

@Component
public class Task implements CommandLineRunner {
    @DubboReference
    private StudentServiceFacade studentServiceFacade;

    @Override
    public void run(String... args) throws Exception {
        //String result = studentServiceFacade.hello("Heart Beat");
        //System.out.println("Receive result ======> " + result);
        //
        //new Thread(()-> {
        //    while (true) {
        //        try {
        //            Thread.sleep(1000);
        //            System.out.println(new Date() + " Receive result ======> " + studentServiceFacade.hello("world"));
        //        } catch (InterruptedException e) {
        //            e.printStackTrace();
        //            Thread.currentThread().interrupt();
        //        }
        //    }
        //}).start();
    }
}