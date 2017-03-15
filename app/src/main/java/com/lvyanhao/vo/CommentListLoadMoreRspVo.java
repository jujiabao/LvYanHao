package com.lvyanhao.vo;

import java.io.Serializable;

/**
 * Created by Hello.Ju on 17/3/6.
 */
public class CommentListLoadMoreRspVo implements Serializable {
    private String ccid;
    private String cfid;
    private String cava;//头像地址
    private String cunick;//昵称
    private String cgrade;//个人评分
    private String ccontent;//评论内容
    private String ccreatime;//发表时间
    private String cagreetime;//被赞次数
    private String cstatus;//当前的评论该用户是否能赞,0不能,1能
    private String cnwpg;//当前的页码,从1开始
    private String climit;
    private String ced;//最后一条记录所在的位置,从1开始

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

    public String getCnwpg() {
        return cnwpg;
    }

    public void setCnwpg(String cnwpg) {
        this.cnwpg = cnwpg;
    }

    public String getClimit() {
        return climit;
    }

    public void setClimit(String climit) {
        this.climit = climit;
    }

    public String getCed() {
        return ced;
    }

    public void setCed(String ced) {
        this.ced = ced;
    }

    @Override
    public String toString() {
        return "CommentListLoadMoreRspVo{" +
                "ccid='" + ccid + '\'' +
                ", cfid='" + cfid + '\'' +
                ", cava='" + cava + '\'' +
                ", cunick='" + cunick + '\'' +
                ", cgrade='" + cgrade + '\'' +
                ", ccontent='" + ccontent + '\'' +
                ", ccreatime='" + ccreatime + '\'' +
                ", cagreetime='" + cagreetime + '\'' +
                ", cstatus='" + cstatus + '\'' +
                ", cnwpg='" + cnwpg + '\'' +
                ", climit='" + climit + '\'' +
                ", ced='" + ced + '\'' +
                '}';
    }
}
