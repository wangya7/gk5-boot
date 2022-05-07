package wang.bannong.gk5.boot.starter.web.gateway.model;

import java.io.Serializable;
import java.util.Map;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import wang.bannong.gk5.boot.starter.web.gateway.enums.ResponseStatusCode;

/**
 * 网关返回值类型
 */
public class GatewayResponse implements Serializable {
    private static final long serialVersionUID = 5796270837020587009L;

    private String  msg;
    private int     code = 0;
    private Object  data = null;

    public final static GatewayResponse SUCCESS = success();

    public static GatewayResponse of(ResponseStatusCode resultCode) {
        GatewayResponse result = new GatewayResponse();
        result.setCode(resultCode.getCode());
        result.setMsg(resultCode.getMsg());
        return result;
    }

    public static GatewayResponse of(ResponseStatusCode resultCode, String msg) {
        GatewayResponse result = new GatewayResponse();
        result.setCode(resultCode.getCode());
        result.setMsg(StringUtils.isNotBlank(msg) ? msg : resultCode.getMsg());
        return result;
    }

    //**************************************************** success

    private static GatewayResponse success() {
        return of(ResponseStatusCode.SUCCESS);
    }

    public static GatewayResponse success(Map<String, Object> map) {
        GatewayResponse result = success();
        result.setData(map);
        return result;
    }

    public static GatewayResponse success(Object data) {
        GatewayResponse result = success();
        result.setData(data);
        return result;
    }

    public static GatewayResponse success(String msg) {
        GatewayResponse result = success();
        result.setMsg(msg);
        return result;
    }

    //**************************************************** fail

    public static GatewayResponse fail(String error) {
        GatewayResponse result = of(ResponseStatusCode.FAILURE);
        result.setMsg(error);
        return result;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return ResponseStatusCode.SUCCESS.getCode() == this.code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @SuppressWarnings("unchecked")
    public <T> T  getData() {
        return (T)data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GatewayResponse{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}
