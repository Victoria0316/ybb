package com.bluemobi.ybb.ps.db.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by gaoxy on 2015/8/20.
 */
@Table(name = "PSMessageFoods")
public class PSMessageFoods {
    @Id
    @Column(column = "id")
    private int id;
    @Column(column = "shopAndName")
    private String shopAndName;
    @Column(column = "imgPath")
    private String imgPath;
    @Column(column = "price")
    private String price;
    @Column(column = "count")
    private String count;
    @Column(column = "orderid")
    private String orderid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getShopAndName() {
        return shopAndName;
    }

    public void setShopAndName(String shopAndName) {
        this.shopAndName = shopAndName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
