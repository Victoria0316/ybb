package com.bluemobi.ybb.network.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liufy on 2015/8/16.
 * 购物车类型编辑内容
 */
public class EditShopCarModel {

    private String userId;

    private String type;

    private List<EditShopCarDto> info = new ArrayList<EditShopCarDto>();


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<EditShopCarDto> getInfo() {
        return info;
    }

    public void setInfo(List<EditShopCarDto> info) {
        this.info = info;
    }
}
