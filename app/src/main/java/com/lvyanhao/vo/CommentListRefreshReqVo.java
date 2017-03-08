package com.lvyanhao.vo;

import java.io.Serializable;

/**
 * Created by Hello.Ju on 17/3/5.
 */
public class CommentListRefreshReqVo implements Serializable {
    private String cfid;
    private String climit;

    public String getCfid() {
        return cfid;
    }

    public void setCfid(String cfid) {
        this.cfid = cfid;
    }

    public String getClimit() {
        return climit;
    }

    public void setClimit(String climit) {
        this.climit = climit;
    }

    @Override
    public String toString() {
        return "CommentListRefreshReqVo{" +
                "cfid='" + cfid + '\'' +
                ", climit='" + climit + '\'' +
                '}';
    }
}
