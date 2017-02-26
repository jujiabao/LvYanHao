package com.lvyanhao.vo;

import java.io.Serializable;

/**
 * Created by Hello.Ju on 17/2/25.
 */
public class UserVerifyMailRspVo implements Serializable {
    private String vmail;
    private String vname;

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

    @Override
    public String toString() {
        return "UserVerifyMailRspVo{" +
                "vmail='" + vmail + '\'' +
                ", vname='" + vname + '\'' +
                '}';
    }
}
