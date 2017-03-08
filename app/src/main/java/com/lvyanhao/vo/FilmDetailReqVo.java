package com.lvyanhao.vo;

import java.io.Serializable;

/**
 * Created by Hello.Ju on 17/3/3.
 */
public class FilmDetailReqVo implements Serializable {
    private String fid;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    @Override
    public String toString() {
        return "FilmDetailReqVo{" +
                "fid='" + fid + '\'' +
                '}';
    }
}
