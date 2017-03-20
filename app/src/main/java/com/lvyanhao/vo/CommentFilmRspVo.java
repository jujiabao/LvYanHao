package com.lvyanhao.vo;

import java.io.Serializable;

/**
 * Created by Hello.Ju on 17/3/7.
 */
public class CommentFilmRspVo implements Serializable {
    private String ccid;
    private String cfid;
    private String cflag;

    public String getCcid() {
        return ccid;
    }

    public void setCcid(String ccid) {
        this.ccid = ccid;
    }

    public String getCfid() {
        return cfid;
    }

    public void setCfid(String cfid) {
        this.cfid = cfid;
    }

    public String getCflag() {
        return cflag;
    }

    public void setCflag(String cflag) {
        this.cflag = cflag;
    }

    @Override
    public String toString() {
        return "CommentFilmRspVo{" +
                "ccid='" + ccid + '\'' +
                ", cfid='" + cfid + '\'' +
                ", cflag='" + cflag + '\'' +
                '}';
    }
}
