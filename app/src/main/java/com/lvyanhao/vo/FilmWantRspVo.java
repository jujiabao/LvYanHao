package com.lvyanhao.vo;

import java.io.Serializable;

/**
 * Created by Hello.Ju on 17/3/19.
 */
public class FilmWantRspVo implements Serializable {
    private String wfid;
    private String wstatus;//�����״̬:1:�ղ�,0:ȡ���ղ�
    private String wflag;

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

    public String getWflag() {
        return wflag;
    }

    public void setWflag(String wflag) {
        this.wflag = wflag;
    }

    @Override
    public String toString() {
        return "FilmWantRspVo{" +
                "wfid='" + wfid + '\'' +
                ", wstatus='" + wstatus + '\'' +
                ", wflag='" + wflag + '\'' +
                '}';
    }
}
