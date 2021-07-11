package wang.bannong.gk5.boot.sample.apollo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apollo")
public class ApolloController {

    private final static Logger log = LoggerFactory.getLogger(ApolloController.class);

    @Value("${name: NANA}")
    private String name;

    @GetMapping("/test")
    public String test() {
        log.info("I Love You,{}", name);
        return "NA";
    }
}
