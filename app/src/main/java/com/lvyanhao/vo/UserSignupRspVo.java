package com.lvyanhao.vo;

import java.io.Serializable;

/**
 * Created by Hello.Ju on 17/2/23.
 */
public class UserSignupRspVo implements Serializable {
    private String suname;
    private String snick;
    private String semail;
    private String smobile;

    public String getSuname() {
        return suname;
    }

    public void setSuname(String suname) {
        this.suname = suname;
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
        return "UserSignupRspVo{" +
                "suname='" + suname + '\'' +
                ", snick='" + snick + '\'' +
                ", semail='" + semail + '\'' +
                ", smobile='" + smobile + '\'' +
                '}';
    }
}
