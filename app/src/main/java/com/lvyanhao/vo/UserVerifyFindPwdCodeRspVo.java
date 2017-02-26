package com.lvyanhao.vo;

import java.io.Serializable;

/**
 * Created by Hello.Ju on 17/2/26.
 */
public class UserVerifyFindPwdCodeRspVo implements Serializable {
    private String fname;
    private String fmail;
    private String fnewpwd;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFmail() {
        return fmail;
    }

    public void setFmail(String fmail) {
        this.fmail = fmail;
    }

    public String getFnewpwd() {
        return fnewpwd;
    }

    public void setFnewpwd(String fnewpwd) {
        this.fnewpwd = fnewpwd;
    }

    @Override
    public String toString() {
        return "UserVerifyFindPwdCodeRspVo{" +
                "fname='" + fname + '\'' +
                ", fmail='" + fmail + '\'' +
                ", fnewpwd='" + fnewpwd + '\'' +
                '}';
    }
}
