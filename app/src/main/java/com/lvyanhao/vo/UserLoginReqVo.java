package com.lvyanhao.vo;

import java.io.Serializable;

/**
 * 用户登录请求信息,附:
 * {"uname":"lvyanhao","upwd":"202cb962ac59075b964b07152d234b70","imei":"3210000000000000000"}
 */
public class UserLoginReqVo implements Serializable {
    private String uname;
    private String upwd;
    private String imei;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpwd() {
        return upwd;
    }

    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    @Override
    public String toString() {
        return "UserLoginReqVo{" +
                "uname='" + uname + '\'' +
                ", upwd='" + upwd + '\'' +
                ", imei='" + imei + '\'' +
                '}';
    }
}
