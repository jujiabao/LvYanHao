package com.lvyanhao.vo;

import java.io.Serializable;

/**
 * Created by Hello.Ju on 17/2/28.
 */
public class FilmListRefreshReqVo implements Serializable {
    private String flimit;

    public String getFlimit() {
        return flimit;
    }

    public void setFlimit(String flimit) {
        this.flimit = flimit;
    }

    @Override
    public String toString() {
        return "FilmListRefreshReqVo{" +
                "flimit='" + flimit + '\'' +
                '}';
    }
}
