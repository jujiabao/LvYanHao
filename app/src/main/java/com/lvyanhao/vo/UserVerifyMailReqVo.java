package com.lvyanhao.vo;

import java.io.Serializable;

/**
 * Created by Hello.Ju on 17/2/25.
 */
public class UserVerifyMailReqVo implements Serializable {
    private String vmail;
    private String vname;
    private String vcode;

    public String getVmail() {
        return vmail;
    }

    public void setVmail(String vmail) {
        this.vmail = vmail;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    @Override
    public String toString() {
        return "UserVerifyMailReqVo{" +
                "vmail='" + vmail + '\'' +
                ", vname='" + vname + '\'' +
                ", vcode='" + vcode + '\'' +
                '}';
    }
}
