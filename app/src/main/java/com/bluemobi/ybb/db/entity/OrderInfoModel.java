package com.bluemobi.ybb.db.entity;

import java.io.Serializable;

/**
 * Created by gaoxy on 2015/8/20.
 */
public class OrderInfoModel implements Serializable {
    private String status;//"0",
    private String msg;//"您下的商品订单201508160047已被确认，正在配送中。",
    private OrderData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public OrderData getData() {
        return data;
    }

    public void setData(OrderData data) {
        this.data = data;
    }
}
