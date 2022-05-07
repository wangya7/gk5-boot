package wang.bannong.gk5.boot.starter.web.security.filter;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import wang.bannong.gk5.boot.starter.web.gateway.model.GatewayResponse;
import wang.bannong.gk5.boot.starter.web.gateway.model.HttpNativeRequest;
import wang.bannong.gk5.boot.starter.web.security.ResponseHandler;
import wang.bannong.gk5.boot.starter.web.security.TokenService;

/**
 * @author bn
 * @date 2022/5/7
 */
@Component
public class LogoutSuccessHandler implements
        org.springframework.security.web.authentication.logout.LogoutSuccessHandler {
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication)
            throws IOException, ServletException {
        String ia = request.getHeader(HttpNativeRequest.IA);
        if (StringUtils.isNotBlank(ia)) {
            redisTemplate.delete(TokenService.getToken(ia));
        }
        ResponseHandler.write(response, GatewayResponse.success("退出成功"));
    }
}
