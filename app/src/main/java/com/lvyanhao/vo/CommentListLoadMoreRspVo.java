package com.lvyanhao.vo;

import java.io.Serializable;

/**
 * Created by Hello.Ju on 17/3/6.
 */
public class CommentListLoadMoreRspVo implements Serializable {
    private String ccid;
    private String cfid;
    private String cava;//ͷ���ַ
    private String cunick;//�ǳ�
    private String cgrade;//��������
    private String ccontent;//��������
    private String ccreatime;//����ʱ��
    private String cagreetime;//���޴���
    private String cstatus;//��ǰ�����۸��û��Ƿ�����,0����,1��
    private String cnwpg;//��ǰ��ҳ��,��1��ʼ
    private String climit;
    private String ced;//���һ����¼���ڵ�λ��,��1��ʼ

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
