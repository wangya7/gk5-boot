package wang.bannong.gk5.boot.starter.web.security;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;


import wang.bannong.gk5.boot.starter.web.gateway.model.GatewayResponse;
import wang.bannong.gk5.boot.starter.web.util.JsonUtils;

public final class ResponseHandler {
    public static void write(HttpServletResponse httpServletResponse, GatewayResponse response)
            throws IOException {
        httpServletResponse.setHeader("Content-Type","text/html;charset=utf-8");
        httpServletResponse.getWriter().write(JsonUtils.toString(response));
    }
}
