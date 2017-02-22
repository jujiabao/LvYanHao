package com.lvyanhao.vo;

/**
 * Created by Hello.Ju on 17/2/22.
 */
public class PageVo {
    //当前页码
    private Integer pageNow;
    //每页显示数量
    private Integer limit;
    //起始页码
    private Integer startPgNum;
    //终止页码
    private Integer endPgNum;

    public PageVo(Integer pageNow) {
        this.pageNow = pageNow;
        this.limit = 10;
        this.startPgNum = this.pageNow * this.limit;
        this.endPgNum = this.startPgNum + this.limit -1;
    }

    public Integer getPageNow() {
        return pageNow;
    }

    public void setPageNow(Integer pageNow) {
        this.pageNow = pageNow;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getStartPgNum() {
        return this.startPgNum;
    }

    public void setStartPgNum(Integer startPgNum) {
        this.startPgNum = startPgNum;
    }

    public Integer getEndPgNum() {
        return this.endPgNum;
    }

    public void setEndPgNum(Integer endPgNum) {
        this.endPgNum = endPgNum;
    }

}
