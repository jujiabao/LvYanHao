package com.lvyanhao.vo;

import java.io.Serializable;

/**
 * Created by Hello.Ju on 17/3/19.
 */
public class FilmWantReqVo implements Serializable {
    private String wfid;
    private String wstatus;//״̬:1:�ղ�,0:ȡ���ղ�

    public String getWfid() {
        return wfid;
    }

    public void setWfid(String wfid) {
        this.wfid = wfid;
    }

    public String getWstatus() {
        return wstatus;
    }

    public void setWstatus(String wstatus) {
        this.wstatus = wstatus;
    }

    @Override
    public String toString() {
        return "FilmWantReqVo{" +
                "wfid='" + wfid + '\'' +
                ", wstatus='" + wstatus + '\'' +
                '}';
    }
}
