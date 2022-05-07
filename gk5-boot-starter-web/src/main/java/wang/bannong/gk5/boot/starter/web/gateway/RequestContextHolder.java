package wang.bannong.gk5.boot.starter.web.gateway;

import wang.bannong.gk5.boot.starter.web.gateway.model.GatewayRequest;
import wang.bannong.gk5.boot.starter.web.security.model.Subject;
import wang.bannong.gk5.boot.starter.web.util.Constant;

public final class RequestContextHolder {

    private static ThreadLocal<GatewayRequest> profileThreadLocal = new InheritableThreadLocal<>();

    public static GatewayRequest get() {
        return profileThreadLocal.get();
    }

    public static void set(GatewayRequest profile) {
        profileThreadLocal.set(profile);
    }

    public static String getTraceId(){
        GatewayRequest profile;
        String traceId;
        if ((profile = get()) != null && (traceId = profile.getTraceId()) != null) {
            return traceId;
        }
        return Constant.BLANK;
    }

    public static long getSubjectId(){
        GatewayRequest profile;
        Subject subject;
        if ((profile = get()) != null && (subject = profile.getSubject()) != null) {
            return subject.getSubjectId();
        }
        return 0;
    }

    public static String getChannel(){
        GatewayRequest profile;
        String channel;
        if ((profile = get()) != null && (channel = profile.getChannel()) != null) {
            return channel;
        }
        return Constant.BLANK;
    }

    public static String getAppid(){
        GatewayRequest profile;
        String appid;
        if ((profile = get()) != null && (appid = profile.getAppid()) != null) {
            return appid;
        }
        return Constant.BLANK;
    }
}