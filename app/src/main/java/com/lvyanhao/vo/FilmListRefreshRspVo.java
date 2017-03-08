package com.lvyanhao.vo;

import java.io.Serializable;

/**
 * Created by Hello.Ju on 17/2/28.
 */
public class FilmListRefreshRspVo implements Serializable {
    private String fid;
    private String fna;
    private String fgrade;
    private String fseio;//ºÚΩÈ
    private String fontm;//…œ”≥ ±º‰
    private String fpicurl;

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

    public String getFgrade() {
        return fgrade;
    }

    public void setFgrade(String fgrade) {
        this.fgrade = fgrade;
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

    @Override
    public String toString() {
        return "FilmListRefreshRspVo{" +
                "fid='" + fid + '\'' +
                ", fna='" + fna + '\'' +
                ", fgrade='" + fgrade + '\'' +
                ", fseio='" + fseio + '\'' +
                ", fontm='" + fontm + '\'' +
                ", fpicurl='" + fpicurl + '\'' +
                '}';
    }
}
