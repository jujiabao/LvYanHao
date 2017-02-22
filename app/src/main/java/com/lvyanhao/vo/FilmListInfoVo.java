package com.lvyanhao.vo;

import java.io.Serializable;

/**
 * Created by Hello.Ju on 17/2/22.
 */
public class FilmListInfoVo extends PageVo implements Serializable {
    //电影Id
    private String fid;
    //电影名称
    private String filmName;
    //电影海报URL地址
    private String filmPosterUrl;
    //电影评分
    private String filmLevel;
    //电影介绍
    private String filmSimpleInfo;
    //电影公映时间
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
