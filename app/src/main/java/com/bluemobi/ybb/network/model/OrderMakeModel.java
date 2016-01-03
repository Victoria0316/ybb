package com.bluemobi.ybb.network.model;

import java.io.Serializable;

/**
 * Created by wangzhijun on 2015/8/14.
 */
public class OrderMakeModel implements Serializable{
    private String id;
    private String img;
    private String count;
    private String price;
    private String title;
    private String category;
    private String categoryId;
    private String type;
    private String name;
    private String reserveTime;
    private String attributeId;
    private String cartId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        if(price == null || "".equals(price)){
            this.price = "0";
        }else{
            this.price = price;
        }

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.name = title;
    }

    public String getCategory() {
        /**
         * 1营养餐2零点餐3医护套餐4医患套餐
         */
        if("1".equals(categoryId)){
            category = "营养餐";
        }if("2".equals(categoryId)){
            category = "零点餐";
        }if("3".equals(categoryId)){
            category = "医护套餐";
        }if("4".equals(categoryId)){
            category = "医患套餐";
        }
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
        this.type = categoryId;
    }

    public String getType() {
        return title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(String reserveTime) {
        this.reserveTime = reserveTime;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }
}
