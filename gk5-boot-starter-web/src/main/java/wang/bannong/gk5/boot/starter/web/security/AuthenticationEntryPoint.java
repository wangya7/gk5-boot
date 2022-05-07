package wang.bannong.gk5.boot.starter.web.security;

import java.io.IOException;
import java.io.Serializable;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import wang.bannong.gk5.boot.starter.web.gateway.enums.ResponseStatusCode;
import wang.bannong.gk5.boot.starter.web.gateway.model.GatewayResponse;
import wang.bannong.gk5.boot.starter.web.util.Constant;

@Component
public class AuthenticationEntryPoint implements
        org.springframework.security.web.AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -7288174536781102449L;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException e) throws IOException {
        GatewayResponse result = GatewayResponse.of(
                ResponseStatusCode.UN_AUTHORIZED,
                String.format(Constant.UNABLE_TO_ACCESS_RESOURCES, request.getRequestURI()));
        LOGGER.warn("请求失败：{}", result);
        ResponseHandler.write(response, result);
    }
}
