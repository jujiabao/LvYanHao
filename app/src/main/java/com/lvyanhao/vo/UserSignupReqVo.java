package com.lvyanhao.vo;

import java.io.Serializable;

/**
 * Created by Hello.Ju on 17/2/23.
 */
public class UserSignupReqVo implements Serializable {
    private String suname;
    private String supwd;
    private String simei;
    private String snick;
    private String semail;//需要验证邮箱
    private String smobile;

    public String getSimei() {
        return simei;
    }

    public void setSimei(String simei) {
        this.simei = simei;
    }

    public String getSuname() {
        return suname;
    }

    public void setSuname(String suname) {
        this.suname = suname;
    }

    public String getSupwd() {
        return supwd;
    }

    public void setSupwd(String supwd) {
        this.supwd = supwd;
    }

    public String getSnick() {
        return snick;
    }

    public void setSnick(String snick) {
        this.snick = snick;
    }

    public String getSemail() {
        return semail;
    }

    public void setSemail(String semail) {
        this.semail = semail;
    }

    public String getSmobile() {
        return smobile;
    }

    public void setSmobile(String smobile) {
        this.smobile = smobile;
    }

    @Override
    public String toString() {
        return "UserSignupReqVo{" +
                "suname='" + suname + '\'' +
                ", supwd='" + supwd + '\'' +
                ", simei='" + simei + '\'' +
                ", snick='" + snick + '\'' +
                ", semail='" + semail + '\'' +
                ", smobile='" + smobile + '\'' +
                '}';
    }
}
