package com.lvyanhao.vo;

import java.io.Serializable;

/**
 * Created by Hello.Ju on 17/3/6.
 */
public class CommentListLoadMoreReqVo implements Serializable {
    //电影ID
    private String cfid;
    //开始值fst
    private String cst;
    //最大个数
    private String climit;

    public String getCfid() {
        return cfid;
    }

    public void setCfid(String cfid) {
        this.cfid = cfid;
    }

    public String getCst() {
        return cst;
    }

    public void setCst(String cst) {
        this.cst = cst;
    }

    public String getClimit() {
        return climit;
    }

    public void setClimit(String climit) {
        this.climit = climit;
    }

    @Override
    public String toString() {
        return "CommentListLoadMoreReqVo{" +
                "cfid='" + cfid + '\'' +
                ", cst='" + cst + '\'' +
                ", climit='" + climit + '\'' +
                '}';
    }
}
