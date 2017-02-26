package com.lvyanhao.vo;

import java.io.Serializable;

/**
 * Created by Hello.Ju on 17/2/22.
 * 用户登录验证返回信息,附:
 * {"status":"0","msg":"登录成功,但您当前不在常用手机上登录,请您保存好密码!","data":{"uname":"jujiabao","nick":"Hello.Ju","email":"694455391@qq.com","mobile":"18651444434","imei":"3210000000000","token":"f369c093-213c-484c-a053-bfbf7df529df"}}
 */
public class UserLoginRspVo implements Serializable {
    private String uname;
    private String nick;
    private String email;
    private String mobile;
    private String imei;
    private String token;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserLoginRspVo{" +
                "uname='" + uname + '\'' +
                ", nick='" + nick + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", imei='" + imei + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
