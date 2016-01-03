package com.bluemobi.ybb.network.model;

import java.util.List;

/**
 * Created by wangzhijun on 2015/8/26.
 */
public class OrderinfoResponseModel {
    private String currentpage;

    private List<OrderItem> info;

    private String totalnum;

    private String totalpage;

    private String pageTime;

    public String getPageTime() {
        return pageTime;
    }

    public void setPageTime(String pageTime) {
        this.pageTime = pageTime;
    }

    public String getCurrentpage() {
        return currentpage;
    }

    public void setCurrentpage(String currentpage) {
        this.currentpage = currentpage;
    }

    public List<OrderItem> getInfo() {
        return info;
    }

    public void setInfo(List<OrderItem> info) {
        this.info = info;
    }

    public String getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(String totalnum) {
        this.totalnum = totalnum;
    }

    public String getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(String totalpage) {
        this.totalpage = totalpage;
    }
}
