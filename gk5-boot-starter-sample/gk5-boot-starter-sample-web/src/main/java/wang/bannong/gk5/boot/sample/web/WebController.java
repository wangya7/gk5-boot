package wang.bannong.gk5.boot.sample.web;

import java.util.Collections;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import wang.bannong.gk5.boot.starter.web.gateway.model.GatewayResponse;
import wang.bannong.gk5.boot.starter.web.security.annotation.Anonymous;
import wang.bannong.gk5.boot.starter.web.security.annotation.PermitAll;

/**
 * @author bn
 * @date 2022/5/7
 */
@RestController
@RequestMapping("/dprs")
public class WebController {

    @Anonymous
    @RequestMapping(path = "/jk1", method = RequestMethod.GET)
    public GatewayResponse show1() {
        return GatewayResponse.success(Collections.singletonMap("name", "Nathan"));
    }

    @Anonymous
    @PermitAll
    @RequestMapping(path = "/jk2", method = RequestMethod.GET)
    public GatewayResponse show2() {
        return GatewayResponse.success(Collections.singletonMap("name", "Nathan"));
    }

    @PermitAll
    @RequestMapping(path = "/jk3", method = RequestMethod.GET)
    public GatewayResponse show3() {
        return GatewayResponse.success(Collections.singletonMap("name", "Nathan"));
    }

    @RequestMapping(path = "/jk4", method = RequestMethod.GET)
    public GatewayResponse show4() {
        return GatewayResponse.success(Collections.singletonMap("name", "Nathan"));
    }
}
