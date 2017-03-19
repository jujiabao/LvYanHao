package com.lvyanhao.vo;

import java.io.Serializable;

/**
 * Created by Hello.Ju on 17/3/3.
 */
public class FilmDetailRspVo implements Serializable {
    private String fid;
    private String fna;
    private String fena;
    private String fgrade;
    private String farea;
    private String fdura;
    private String ftp;
    private String fintro;//��ϸ����
    private String fontm;//��ӳʱ��
    private String fpicurl;
    private String fcount;//��������
    private String fwant;//�Ƿ��Ѿ��ղ�,1:�ղ�,2:δ�ղ�

    public String getFwant() {
        return fwant;
    }

    public void setFwant(String fwant) {
        this.fwant = fwant;
    }

    public String getFdura() {
        return fdura;
    }

    public void setFdura(String fdura) {
        this.fdura = fdura;
    }

    public String getFcount() {
        return fcount;
    }

    public void setFcount(String fcount) {
        this.fcount = fcount;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFna() {
        return fna;
    }

    public void setFna(String fna) {
        this.fna = fna;
    }

    public String getFena() {
        return fena;
    }

    public void setFena(String fena) {
        this.fena = fena;
    }

    public String getFgrade() {
        return fgrade;
    }

    public void setFgrade(String fgrade) {
        this.fgrade = fgrade;
    }

    public String getFarea() {
        return farea;
    }

    public void setFarea(String farea) {
        this.farea = farea;
    }

    public String getFtp() {
        return ftp;
    }

    public void setFtp(String ftp) {
        this.ftp = ftp;
    }

    public String getFintro() {
        return fintro;
    }

    public void setFintro(String fintro) {
        this.fintro = fintro;
    }

    public String getFontm() {
        return fontm;
    }

    public void setFontm(String fontm) {
        this.fontm = fontm;
    }

    public String getFpicurl() {
        return fpicurl;
    }

    public void setFpicurl(String fpicurl) {
        this.fpicurl = fpicurl;
    }

    @Override
    public String toString() {
        return "FilmDetailRspVo{" +
                "fid='" + fid + '\'' +
                ", fna='" + fna + '\'' +
                ", fena='" + fena + '\'' +
                ", fgrade='" + fgrade + '\'' +
                ", farea='" + farea + '\'' +
                ", fdura='" + fdura + '\'' +
                ", ftp='" + ftp + '\'' +
                ", fintro='" + fintro + '\'' +
                ", fontm='" + fontm + '\'' +
                ", fpicurl='" + fpicurl + '\'' +
                ", fcount='" + fcount + '\'' +
                ", fwant='" + fwant + '\'' +
                '}';
    }
}
