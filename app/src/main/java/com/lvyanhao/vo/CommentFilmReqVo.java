package com.lvyanhao.vo;

import java.io.Serializable;

/**
 * Created by Hello.Ju on 17/3/7.
 */
public class CommentFilmReqVo implements Serializable {
    private String cfid;
    private String ccontent;
    private String cgrade;

    public String getCfid() {
        return cfid;
    }

    public void setCfid(String cfid) {
        this.cfid = cfid;
    }

    public String getCcontent() {
        return ccontent;
    }

    public void setCcontent(String ccontent) {
        this.ccontent = ccontent;
    }

    public String getCgrade() {
        return cgrade;
    }

    public void setCgrade(String cgrade) {
        this.cgrade = cgrade;
    }

    @Override
    public String toString() {
        return "CommentFilmReqVo{" +
                "cfid='" + cfid + '\'' +
                ", ccontent='" + ccontent + '\'' +
                ", cgrade='" + cgrade + '\'' +
                '}';
    }
}
