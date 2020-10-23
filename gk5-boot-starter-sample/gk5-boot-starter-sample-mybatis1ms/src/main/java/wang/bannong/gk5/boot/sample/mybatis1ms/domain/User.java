package wang.bannong.gk5.boot.sample.mybatis1ms.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 小程序用户信息表
 */
public class User implements Serializable {
    private static final long serialVersionUID = 8267242611433593283L;

    private Long   id;
    private String nick;
    private String icon;
    private String mobile;
    private Byte   sex;
    private Byte   type;
    private Byte   status;
    private String openid;
    private String signature;
    private Date   createTime;
    private Date   modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nick='" + nick + '\'' +
                ", icon='" + icon + '\'' +
                ", mobile='" + mobile + '\'' +
                ", sex=" + sex +
                ", type=" + type +
                ", status=" + status +
                ", openid='" + openid + '\'' +
                ", signature='" + signature + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}