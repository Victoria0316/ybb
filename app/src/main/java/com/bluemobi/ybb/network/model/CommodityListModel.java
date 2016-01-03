package com.bluemobi.ybb.network.model;

import java.util.List;

/**
 * Created by wangzhijun on 2015/8/11.
 */
public class CommodityListModel {
    private String currentpage;
    private String totalnum;
    private String totalpage;
    private List<CommodityModel> info;

    public String getCurrentpage() {
        return currentpage;
    }

    public void setCurrentpage(String currentpage) {
        this.currentpage = currentpage;
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

    public List<CommodityModel> getInfo() {
        return info;
    }

    public void setInfo(List<CommodityModel> info) {
        this.info = info;
    }
}
