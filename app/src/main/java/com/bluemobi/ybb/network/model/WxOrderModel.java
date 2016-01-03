package com.bluemobi.ybb.network.model;

/**
 * Created by wangzhijun on 2015/8/20.
 */
public class WxOrderModel {
    private String type;//weChatResultDTO",
    private String appid;//wx21c9bfb5a8d80ba8",
    private String partnerid;//1262575801",
    private String prepayid;//wx201508121235237f6ca152e20685541610",

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }
}
