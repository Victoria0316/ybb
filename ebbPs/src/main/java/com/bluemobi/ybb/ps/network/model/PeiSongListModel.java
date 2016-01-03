package com.bluemobi.ybb.ps.network.model;

import java.util.List;

/**
 * Created by gaoxy on 2015/8/15.
 * peisong shiti
 */
public class PeiSongListModel {
    private String currentpage;
    private String totalnum;
    private String totalpage;
    private List<PeiSongModel> info;

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

    public List<PeiSongModel> getInfo() {
        return info;
    }

    public void setInfo(List<PeiSongModel> info) {
        this.info = info;
    }
}
