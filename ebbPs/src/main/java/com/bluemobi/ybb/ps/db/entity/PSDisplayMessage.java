package com.bluemobi.ybb.ps.db.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gaoxy on 2015/8/21.
 */
public class PSDisplayMessage implements Serializable {
    private String id;
    private String isread;
    private String createTime;
    private String orderNo;
    private String orderStatus;
    private String orderId;
    private List<PSMessageFoods> childBean;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsread() {
        return isread;
    }

    public void setIsread(String isread) {
        this.isread = isread;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<PSMessageFoods> getChildBean() {
        return childBean;
    }

    public void setChildBean(List<PSMessageFoods> childBean) {
        this.childBean = childBean;
    }
}
