package wang.bannong.gk5.boot.sample.web;

import java.util.Collections;
import javax.annotation.Resource;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.bannong.gk5.boot.sample.web.dto.LoginDto;
import wang.bannong.gk5.boot.starter.web.gateway.RequestContextHolder;
import wang.bannong.gk5.boot.starter.web.gateway.SubjectClient;
import wang.bannong.gk5.boot.starter.web.gateway.model.GatewayResponse;
import wang.bannong.gk5.boot.starter.web.security.AuthenticationType;
import wang.bannong.gk5.boot.starter.web.security.ConvergenceAuthenticationToken;
import wang.bannong.gk5.boot.starter.web.security.TokenService;
import wang.bannong.gk5.boot.starter.web.security.annotation.Anonymous;
import wang.bannong.gk5.boot.starter.web.security.model.Subject;

@RestController
@RequestMapping("subject")
public class SubjectController {

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private TokenService tokenService;

    @Anonymous
    @PostMapping("login")
    public GatewayResponse login(@RequestBody LoginDto loginDto) {
        ConvergenceAuthenticationToken authenticationToken = new ConvergenceAuthenticationToken(
                loginDto.getMobile(),
                loginDto.getPassword(),
                AuthenticationType.PASSWORD,
                SubjectClient.ADMIN
        );
        // 用户验证
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {

        }
        Subject subject = (Subject) authentication.getPrincipal();
        String token = tokenService.createSubject(subject);
        return GatewayResponse.success(token);
    }


    @GetMapping("info")
    public GatewayResponse info() {
        long subjectId = RequestContextHolder.getSubjectId();
        return GatewayResponse.success(Collections.singletonMap("subjectId", subjectId));
    }
}
