package wang.bannong.gk5.boot.starter.web.gateway.enums;

import javax.servlet.http.HttpServletResponse;

/**
 * 状态码
 */
public enum ResponseStatusCode {
    /**
     * 200 成功
     */
    SUCCESS(HttpServletResponse.SC_OK, "成功"),

    /**
     * 400 业务异常
     */
    BAD_REQUEST(HttpServletResponse.SC_BAD_REQUEST, "异常请求"),

    /**
     * 401 请求未授权
     */
    UN_AUTHORIZED(HttpServletResponse.SC_UNAUTHORIZED, "请求未授权"),

    /**
     * 404 没找到请求
     */
    NOT_FOUND(HttpServletResponse.SC_NOT_FOUND, "没找到请求"),

    /**
     * 405 不支持当前请求方法
     */
    METHOD_NOT_SUPPORTED(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "不支持当前请求方法"),

    /**
     * 415 不支持当前媒体类型
     */
    MEDIA_TYPE_NOT_SUPPORTED(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "不支持当前媒体类型"),

    /**
     * 403 请求被拒绝
     */
    REQ_REJECT(HttpServletResponse.SC_FORBIDDEN, "请求被拒绝"),

    /**
     * 500 服务器异常
     */
    FAILURE(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "服务器异常"),

    ;
    private int    code;
    private String msg;

    ResponseStatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "ResponseStatusCode{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                "} " + super.toString();
    }
}
