package com.lvyanhao.vo;

import java.io.Serializable;

/**
 * Created by Hello.Ju on 17/3/2.
 */
public class FilmListLoadMoreRspVo implements Serializable {
    private String fid;
    private String fna;
    private String fgrade;
    private String fseio;//���
    private String fontm;//��ӳʱ��
    private String fpicurl;
    private String fnwpg;//��ǰ��ҳ��,��1��ʼ
    private String flimit;
    private String fed;//���һ����¼���ڵ�λ��,��1��ʼ

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
