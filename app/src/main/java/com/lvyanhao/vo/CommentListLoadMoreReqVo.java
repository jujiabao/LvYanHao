package com.lvyanhao.vo;

import java.io.Serializable;

/**
 * Created by Hello.Ju on 17/3/6.
 */
public class CommentListLoadMoreReqVo implements Serializable {
    //��ӰID
    private String cfid;
    //��ʼֵfst
    private String cst;
    //������
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
