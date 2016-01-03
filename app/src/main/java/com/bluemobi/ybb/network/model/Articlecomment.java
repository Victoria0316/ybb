package com.bluemobi.ybb.network.model;

import java.util.List;

/**
 * Created by gaozq on 2015/8/12.
 */
public class Articlecomment {

    private String currentpage;
    private List<Articlecommentinfo> info;
    private String totalnum;
    private String totalpage;

    public String getCurrentpage() {
        return currentpage;
    }

    public void setCurrentpage(String currentpage) {
        this.currentpage = currentpage;
    }

    public List<Articlecommentinfo> getInfo() {
        return info;
    }

    public void setInfo(List<Articlecommentinfo> info) {
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
