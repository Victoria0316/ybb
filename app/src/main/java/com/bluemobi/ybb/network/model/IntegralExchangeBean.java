package com.bluemobi.ybb.network.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gaoyn on 2015/8/12.
 */
public class IntegralExchangeBean  implements Serializable{

    private String currentnum;//每页条数
    private String currentpage;//当前页数
    private String pageTime;//分页时间
    private List<IntegralExchangeInfo> info;


    public String getCurrentnum() {
        return currentnum;
    }

    public void setCurrentnum(String currentnum) {
        this.currentnum = currentnum;
    }

    public String getCurrentpage() {
        return currentpage;
    }

    public void setCurrentpage(String currentpage) {
        this.currentpage = currentpage;
    }

    public String getPageTime() {
        return pageTime;
    }

    public void setPageTime(String pageTime) {
        this.pageTime = pageTime;
    }

    public List<IntegralExchangeInfo> getInfo() {
        return info;
    }

    public void setInfo(List<IntegralExchangeInfo> info) {
        this.info = info;
    }
}
