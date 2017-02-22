package com.lvyanhao.vo;

import java.io.Serializable;

/**
 * Created by Hello.Ju on 17/2/22.
 */
public class FilmListInfoVo extends PageVo implements Serializable {
    //��ӰId
    private String fid;
    //��Ӱ����
    private String filmName;
    //��Ӱ����URL��ַ
    private String filmPosterUrl;
    //��Ӱ����
    private String filmLevel;
    //��Ӱ����
    private String filmSimpleInfo;
    //��Ӱ��ӳʱ��
    private String filmOpenTime;

    public FilmListInfoVo(Integer pageNow) {
        super(pageNow);
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getFilmPosterUrl() {
        return filmPosterUrl;
    }

    public void setFilmPosterUrl(String filmPosterUrl) {
        this.filmPosterUrl = filmPosterUrl;
    }

    public String getFilmLevel() {
        return filmLevel;
    }

    public void setFilmLevel(String filmLevel) {
        this.filmLevel = filmLevel;
    }

    public String getFilmSimpleInfo() {
        return filmSimpleInfo;
    }

    public void setFilmSimpleInfo(String filmSimpleInfo) {
        this.filmSimpleInfo = filmSimpleInfo;
    }

    public String getFilmOpenTime() {
        return filmOpenTime;
    }

    public void setFilmOpenTime(String filmOpenTime) {
        this.filmOpenTime = filmOpenTime;
    }

    @Override
    public String toString() {
        return "FilmListInfoVo{" +
                "fid='" + fid + '\'' +
                ", filmName='" + filmName + '\'' +
                ", filmPosterUrl='" + filmPosterUrl + '\'' +
                ", filmLevel='" + filmLevel + '\'' +
                ", filmSimpleInfo='" + filmSimpleInfo + '\'' +
                ", filmOpenTime='" + filmOpenTime + '\'' +
                '}';
    }
}
