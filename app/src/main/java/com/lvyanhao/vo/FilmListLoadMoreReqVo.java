package com.lvyanhao.vo;

import java.io.Serializable;

/**
 * Created by Hello.Ju on 17/3/2.
 * 介绍:fst=0,flimit=10:数据发给你是第1条~第10条数据,
 *      下一次传10~10,就是第11条~20条数据
 */
public class FilmListLoadMoreReqVo implements Serializable {
    //开始值fst
    private String fst;
    //最大个数
    private String flimit;

    public String getFst() {
        return fst;
    }

    public void setFst(String fst) {
        this.fst = fst;
    }

    public String getFlimit() {
        return flimit;
    }

    public void setFlimit(String flimit) {
        this.flimit = flimit;
    }

    @Override
    public String toString() {
        return "FilmListLoadMoreReqVo{" +
                "fst='" + fst + '\'' +
                ", flimit='" + flimit + '\'' +
                '}';
    }
}
