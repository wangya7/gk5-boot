package wang.bannong.gk5.boot.starter.web.gateway.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import wang.bannong.gk5.boot.starter.web.security.model.Subject;

/**
 * 请求入参
 */
public class GatewayRequest implements Serializable {
    private static final long serialVersionUID = -1592730885698408741L;
    /**
     * 原生请求
     */
    private final HttpNativeRequest nativeRequest;

    /**
     * 链路标识
     */
    private final String traceId;

    /**
     * 客户端请求时间戳
     */
    private final long clientRequestTime;

    /**
     * 服务端接收请求的时间戳
     */
    private final long requestTime;

    /**
     * 渠道
     */
    private final String channel;

    /**
     * 应用标识
     */
    private final String appid;

    /**
     * 软硬件信息
     */
    private final HardAndSoftInfo hardAndSoftInfo;

    /**
     * 登录主体信息
     */
    private Subject subject;

    /**
     * 客户端地理位置
     */
    private GeographicalLocation geographicalLocation;

    /**
     * 客户端的验签
     */
    private String sign;

    /**
     * 客户端的IP
     */
    private final String ip;

    /**
     * 客户端的请求方式
     */
    private final String method;

    /**
     * 客户端的请求路径
     */
    private final String path;

    public GatewayRequest(
            HttpNativeRequest nativeRequest, String traceId, long clientRequestTime,
            long requestTime, String channel, String appid, HardAndSoftInfo hardAndSoftInfo,
            Subject subject, GeographicalLocation geographicalLocation,
            String sign, String ip, String method, String path) {
        this.nativeRequest = nativeRequest;
        this.traceId = traceId;
        this.clientRequestTime = clientRequestTime;
        this.requestTime = requestTime;
        this.channel = channel;
        this.appid = appid;
        this.hardAndSoftInfo = hardAndSoftInfo;
        this.subject = subject;
        this.geographicalLocation = geographicalLocation;
        this.sign = sign;
        this.ip = ip;
        this.method = method;
        this.path = path;
    }

    public static GatewayRequest.HttpRequestProfileBuilder builder() {
        return new GatewayRequest.HttpRequestProfileBuilder();
    }

    public static class HttpRequestProfileBuilder {
        private HttpNativeRequest nativeRequest;
        private String traceId;
        private long clientRequestTime;
        private long requestTime;
        private String channel;
        private String appid;
        private String ip;
        private String method;
        private HardAndSoftInfo      hardAndSoftInfo;
        private Subject              subject;
        private GeographicalLocation geographicalLocation;
        private String sign;
        private String path;

        public GatewayRequest.HttpRequestProfileBuilder nativeRequest(HttpNativeRequest nativeRequest) {
            this.nativeRequest = nativeRequest;
            this.clientRequestTime = Long.parseLong(nativeRequest.getRt());
            this.channel = nativeRequest.getChannel();
            this.appid = nativeRequest.getAppid();
            this.ip = nativeRequest.getIp();
            this.method = nativeRequest.getMethod();
            this.sign = nativeRequest.getSign();
            this.hardAndSoftInfo = HardAndSoftInfo.of(nativeRequest.getHsi());
            if (StringUtils.isNotBlank(nativeRequest.getGl())) {
                this.geographicalLocation = GeographicalLocation.of(nativeRequest.getGl());
            }
            this.path = nativeRequest.getPath();
            return this;
        }

        public GatewayRequest.HttpRequestProfileBuilder traceId(String traceId) {
            this.traceId = traceId;
            return this;
        }

        public GatewayRequest.HttpRequestProfileBuilder requestTime(long requestTime) {
            this.requestTime = requestTime;
            return this;
        }

        public GatewayRequest.HttpRequestProfileBuilder subject(Subject subject) {
            this.subject = subject;
            return this;
        }

        public GatewayRequest build() {
            return new GatewayRequest(this.nativeRequest, this.traceId, this.clientRequestTime,
                    this.requestTime, this.channel, this.appid, this.hardAndSoftInfo,
                    this.subject, this.geographicalLocation,
                    this.sign, this.ip, this.method, this.path);
        }
    }


    public HttpNativeRequest getNativeRequest() {
        return nativeRequest;
    }

    public String getTraceId() {
        return traceId;
    }

    public long getClientRequestTime() {
        return clientRequestTime;
    }

    public long getRequestTime() {
        return requestTime;
    }

    public String getChannel() {
        return channel;
    }

    public String getAppid() {
        return appid;
    }

    public HardAndSoftInfo getHardAndSoftInfo() {
        return hardAndSoftInfo;
    }

    public Subject getSubject() {
        return subject;
    }

    public GeographicalLocation getGeographicalLocation() {
        return geographicalLocation;
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
        return "GatewayRequest{" +
                "traceId='" + traceId + '\'' +
                ", clientRequestTime=" + clientRequestTime +
                ", requestTime=" + requestTime +
                ", channel='" + channel + '\'' +
                ", appid='" + appid + '\'' +
                ", hardAndSoftInfo=" + hardAndSoftInfo +
                ", subject=" + subject +
                ", geographicalLocation=" + geographicalLocation +
                ", sign='" + sign + '\'' +
                ", ip='" + ip + '\'' +
                ", method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", nativeRequest=" + nativeRequest +
                '}';
    }
}
