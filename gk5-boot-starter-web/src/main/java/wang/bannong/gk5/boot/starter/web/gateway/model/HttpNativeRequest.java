package wang.bannong.gk5.boot.starter.web.gateway.model;

import javax.servlet.http.HttpServletRequest;

import static wang.bannong.gk5.boot.starter.web.util.Constant.BLANK;

import org.apache.commons.lang3.StringUtils;
import wang.bannong.gk5.boot.starter.web.gateway.exception.GatewayException;
import wang.bannong.gk5.boot.starter.web.util.html.EscapeUtil;

public class HttpNativeRequest {
    public static final String UNKNOWN       = "unknown";
    public static final String HEADER_PREFIX = "whl-";

    public static final String RT      = HEADER_PREFIX + "rt";
    public static final String CHANNEL = HEADER_PREFIX + "channel";
    public static final String APPID   = HEADER_PREFIX + "appid";
    public static final String HSI     = HEADER_PREFIX + "hsi";
    public static final String IA      = HEADER_PREFIX + "ia";
    public static final String GL      = HEADER_PREFIX + "gl";
    public static final String SIGN    = HEADER_PREFIX + "sign";

    private String rt;
    private String channel;
    private String appid;
    private String hsi;
    private String ia;
    private String gl;
    private String sign;
    private String ip;
    private String method;
    private String path;

    public static String blank(String src) {
        if (src != null && src.length() > 0) {
            return src;
        }
        return BLANK;
    }

    public static HttpNativeRequest of(HttpServletRequest request) {
        HttpNativeRequest nativeRequest = new HttpNativeRequest();
        nativeRequest.setRt(blank(request.getHeader(RT)));
        nativeRequest.setChannel(blank(request.getHeader(CHANNEL)));
        nativeRequest.setAppid(blank(request.getHeader(APPID)));
        nativeRequest.setHsi(blank(request.getHeader(HSI)));
        nativeRequest.setIa(blank(request.getHeader(IA)));
        nativeRequest.setGl(blank(request.getHeader(GL)));
        nativeRequest.setSign(blank(request.getHeader(SIGN)));
        nativeRequest.setIp(getIpAddr(request));
        nativeRequest.setMethod(request.getMethod());
        nativeRequest.setPath(request.getRequestURI());
        return nativeRequest;
    }

    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return UNKNOWN;
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : EscapeUtil.clean(ip);
    }

    private void setRt(String rt) {
        if (BLANK.equals(rt)) {
            throw new GatewayException("\'" + RT + "\' in header cannot be blank");
        }
        this.rt = rt;
    }

    private void setChannel(String channel) {
        if (BLANK.equals(channel)) {
            throw new GatewayException("\'" + CHANNEL + "\' in header cannot be blank");
        }
        this.channel = channel;
    }

    private void setAppid(String appid) {
        if (BLANK.equals(appid)) {
            throw new GatewayException("\'" + APPID + "\' in header cannot be blank");
        }
        this.appid = appid;
    }

    private void setHsi(String hsi) {
        if (BLANK.equals(hsi)) {
            throw new GatewayException("\'" + HSI + "\' in header cannot be blank");
        }
        this.hsi = hsi;
    }

    private void setIa(String ia) {
        this.ia = ia;
    }

    private void setGl(String gl) {
        this.gl = gl;
    }

    private void setSign(String sign) {
        this.sign = sign;
    }

    private void setIp(String ip) {
        if (StringUtils.isBlank(ip)) {
            this.ip = UNKNOWN;
        }
        this.ip = ip;
    }

    private void setMethod(String method) {
        if (StringUtils.isBlank(method)) {
            throw new GatewayException("no assign request method");
        }
        this.method = method;
    }

    private void setPath(String path) {
        this.path = path;
    }


    public String getRt() {
        return rt;
    }

    public String getChannel() {
        return channel;
    }

    public String getAppid() {
        return appid;
    }

    public String getHsi() {
        return hsi;
    }

    public String getIa() {
        return ia;
    }

    public String getGl() {
        return gl;
    }

    public String getSign() {
        return sign;
    }

    public String getIp() {
        return ip;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "HttpNativeRequest{" +
                "rt='" + rt + '\'' +
                ", channel='" + channel + '\'' +
                ", appid='" + appid + '\'' +
                ", hsi='" + hsi + '\'' +
                ", ia='" + ia + '\'' +
                ", gl='" + gl + '\'' +
                ", sign='" + sign + '\'' +
                ", ip='" + ip + '\'' +
                ", method='" + method + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}