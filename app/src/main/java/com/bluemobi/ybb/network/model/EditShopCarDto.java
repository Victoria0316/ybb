package com.bluemobi.ybb.network.model;

/**
 * Created by wangzhijun on 2015/8/25.
 */
public class EditShopCarDto
{
    public EditShopCarDto() {

    }

    private String id;//商品ID;
    private String count;//数量;
    private String reserveTime;//预约时间
    private String price;//数量;
    private String categoryId;//数量;
    private String attributeId;//餐次;

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(String reserveTime) {
        this.reserveTime = reserveTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
