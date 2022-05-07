package wang.bannong.gk5.boot.starter.web.util;

/**
 * constant
 */
public interface Constant {

    /**
     * 缓存： token样式
     */
    String CK_TOKEN_PATTERN = "ia:token:%s";

    /**
     * 原生 token 生效时长 20天 单位秒
     */
    long NATIVE_TOKEN_EFFECTIVE_TIME = 3600 * 24 * 20;

    /**
     * ADMIN token 生效时长 15分钟 单位秒
     */
    long ADMIN_TOKEN_EFFECTIVE_TIME = 15 * 60;

    /**
     * 默认 token 生效时长 15分钟 单位秒
     */
    long TOKEN_EFFECTIVE_TIME = 15 * 60;


    String UNABLE_TO_ACCESS_RESOURCES = "请求访问：%s，认证失败，无法访问系统资源";


    String BLANK = "";
}
