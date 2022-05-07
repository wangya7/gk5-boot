package wang.bannong.gk5.boot.sample.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.bannong.gk5.boot.starter.web.gateway.model.GatewayResponse;
import wang.bannong.gk5.boot.starter.web.security.annotation.Anonymous;

@RestController
@RequestMapping("student")
public class StudentController {

    @Anonymous
    @RequestMapping("all")
    public GatewayResponse all() {
        return GatewayResponse.SUCCESS;
    }
}
