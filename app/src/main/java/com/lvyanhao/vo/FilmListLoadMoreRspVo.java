package com.lvyanhao.vo;

import java.io.Serializable;

/**
 * Created by Hello.Ju on 17/3/2.
 */
public class FilmListLoadMoreRspVo implements Serializable {
    private String fid;
    private String fna;
    private String fgrade;
    private String fseio;//简介
    private String fontm;//上映时间
    private String fpicurl;
    private String fnwpg;//当前的页码,从1开始
    private String flimit;
    private String fed;//最后一条记录所在的位置,从1开始

    public String getFgrade() {
        return fgrade;
    }

    public void setFgrade(String fgrade) {
        this.fgrade = fgrade;
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

    public String getFseio() {
        return fseio;
    }

    public void setFseio(String fseio) {
        this.fseio = fseio;
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

    public String getFnwpg() {
        return fnwpg;
    }

    public void setFnwpg(String fnwpg) {
        this.fnwpg = fnwpg;
    }

    public String getFlimit() {
        return flimit;
    }

    public void setFlimit(String flimit) {
        this.flimit = flimit;
    }

    public String getFed() {
        return fed;
    }

    public void setFed(String fed) {
        this.fed = fed;
    }

    @Override
    public String toString() {
        return "FilmListLoadMoreRspVo{" +
                "fid='" + fid + '\'' +
                ", fna='" + fna + '\'' +
                ", fgrade='" + fgrade + '\'' +
                ", fseio='" + fseio + '\'' +
                ", fontm='" + fontm + '\'' +
                ", fpicurl='" + fpicurl + '\'' +
                ", fnwpg='" + fnwpg + '\'' +
                ", flimit='" + flimit + '\'' +
                ", fed='" + fed + '\'' +
                '}';
    }
}
