package com.lvyanhao.vo;

import java.io.Serializable;

/**
 * Created by Hello.Ju on 17/3/6.
 */
public class CommentAgreeRspVo implements Serializable {
    //电影ID
    private String cfid;
    //评论的id
    private String ccid;
    //1:点赞,0:取消点赞
    private String cstatus;
    //是否成功,0失败;1成功
    private String flag;

    public String getCstatus() {
        return cstatus;
    }

    public void setCstatus(String cstatus) {
        this.cstatus = cstatus;
    }

    public String getCfid() {
        return cfid;
    }

    public void setCfid(String cfid) {
        this.cfid = cfid;
    }

    public String getCcid() {
        return ccid;
    }

    public void setCcid(String ccid) {
        this.ccid = ccid;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "CommentAgreeRspVo{" +
                "cfid='" + cfid + '\'' +
                ", ccid='" + ccid + '\'' +
                ", cstatus='" + cstatus + '\'' +
                ", flag='" + flag + '\'' +
                '}';
    }
}
