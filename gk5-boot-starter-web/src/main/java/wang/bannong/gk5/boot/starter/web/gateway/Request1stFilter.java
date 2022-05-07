package wang.bannong.gk5.boot.starter.web.gateway;

import java.io.IOException;
import java.util.Enumeration;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import wang.bannong.gk5.boot.starter.web.filter.AOncePerRequestFilter;
import wang.bannong.gk5.boot.starter.web.gateway.enums.ResponseStatusCode;
import wang.bannong.gk5.boot.starter.web.gateway.exception.GatewayException;
import wang.bannong.gk5.boot.starter.web.gateway.model.GatewayRequest;
import wang.bannong.gk5.boot.starter.web.gateway.model.GatewayResponse;
import wang.bannong.gk5.boot.starter.web.gateway.model.HttpNativeRequest;
import wang.bannong.gk5.boot.starter.web.security.ResponseHandler;
import wang.bannong.gk5.boot.starter.web.security.TokenService;
import wang.bannong.gk5.boot.starter.web.util.JsonUtils;

/**
 * Request handling
 */
@Component
public class Request1stFilter extends AOncePerRequestFilter {

    private final static Logger LOGGER = LoggerFactory.getLogger(Request1stFilter.class);

    @Resource
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        try {
            String traceId = UUID.randomUUID().toString().replace("-", "");
            MDC.put("tid", traceId);
            StringBuilder builder = new StringBuilder(800);
            builder.append("\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n")
                   .append("┃Mint-Path: ")
                   .append(requestURI)
                   .append(" " + JsonUtils.toString(request.getParameterMap()))
                   .append("\n┃Method: ")
                   .append(request.getMethod())
                   .append("\n");
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                builder.append("┃").append(name).append(": ").append(request.getHeader(name))
                       .append("\n");
            }
            builder.append("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
            LOGGER.info(builder.toString());
            String token = request.getHeader(HttpNativeRequest.IA);
            if (!noNeedContainProfile(requestURI)) {
                HttpNativeRequest nativeRequest = HttpNativeRequest.of(request);
                GatewayRequest requestProfile = GatewayRequest
                        .builder()
                        .nativeRequest(nativeRequest)
                        .traceId(traceId)
                        .requestTime(System.currentTimeMillis())
                        .subject(tokenService.getSubject(token))
                        .build();
                RequestContextHolder.set(requestProfile);
                LOGGER.info("request profile:[{}]", requestProfile);
            }
            filterChain.doFilter(request, response);
        } catch (GatewayException e) {
            LOGGER.error("request handling error", e);
            GatewayResponse result = GatewayResponse.of(ResponseStatusCode.BAD_REQUEST, e.getMessage());
            ResponseHandler.write(response, result);
        } catch (Exception e) {
            LOGGER.error("request handling error", e);
            GatewayResponse result = GatewayResponse.of(ResponseStatusCode.BAD_REQUEST, e.getMessage());
            ResponseHandler.write(response, result);
        }
    }

    /**
     * 不需要包含
     */
    public boolean noNeedContainProfile(String requestURI) {
        return StringUtils.isNotBlank(requestURI) &&
                (requestURI.contains("swagger") || requestURI.contains("api-docs"));
    }
}
