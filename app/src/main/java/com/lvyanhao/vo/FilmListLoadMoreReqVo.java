package com.lvyanhao.vo;

import java.io.Serializable;

/**
 * Created by Hello.Ju on 17/3/2.
 * ����:fst=0,flimit=10:���ݷ������ǵ�1��~��10������,
 *      ��һ�δ�10~10,���ǵ�11��~20������
 */
public class FilmListLoadMoreReqVo implements Serializable {
    //��ʼֵfst
    private String fst;
    //������
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
