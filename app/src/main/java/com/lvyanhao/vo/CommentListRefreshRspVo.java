package com.lvyanhao.vo;

import java.io.Serializable;

/**
 * Created by Hello.Ju on 17/3/5.
 */
public class CommentListRefreshRspVo implements Serializable {
    private String ccid;
    private String cfid;
    private String cava;//头像地址
    private String cunick;//昵称
    private String cgrade;//个人评分
    private String ccontent;//评论内容
    private String ccreatime;//发表时间
    private String cagreetime;//被赞次数
    private String cstatus;//当前的评论该用户是否能赞,0不能,1能
    private String climit;

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

    public String getCava() {
        return cava;
    }

    public void setCava(String cava) {
        this.cava = cava;
    }

    public String getCunick() {
        return cunick;
    }

    public void setCunick(String cunick) {
        this.cunick = cunick;
    }

    public String getCgrade() {
        return cgrade;
    }

    public void setCgrade(String cgrade) {
        this.cgrade = cgrade;
    }

    public String getCcontent() {
        return ccontent;
    }

    public void setCcontent(String ccontent) {
        this.ccontent = ccontent;
    }

    public String getCcreatime() {
        return ccreatime;
    }

    public void setCcreatime(String ccreatime) {
        this.ccreatime = ccreatime;
    }

    public String getCagreetime() {
        return cagreetime;
    }

    public void setCagreetime(String cagreetime) {
        this.cagreetime = cagreetime;
    }

    public String getCstatus() {
        return cstatus;
    }

    public void setCstatus(String cstatus) {
        this.cstatus = cstatus;
    }

    public String getClimit() {
        return climit;
    }

    public void setClimit(String climit) {
        this.climit = climit;
    }

    @Override
    public String toString() {
        return "CommentListRefreshRspVo{" +
                "ccid='" + ccid + '\'' +
                ", cfid='" + cfid + '\'' +
                ", cava='" + cava + '\'' +
                ", cunick='" + cunick + '\'' +
                ", cgrade='" + cgrade + '\'' +
                ", ccontent='" + ccontent + '\'' +
                ", ccreatime='" + ccreatime + '\'' +
                ", cagreetime='" + cagreetime + '\'' +
                ", cstatus='" + cstatus + '\'' +
                ", climit='" + climit + '\'' +
                '}';
    }
}
