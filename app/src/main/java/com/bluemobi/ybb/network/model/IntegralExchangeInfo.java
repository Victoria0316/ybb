package com.bluemobi.ybb.network.model;

import java.io.Serializable;

/**
 * Created by gaoyn on 2015/8/12.
 */
public class IntegralExchangeInfo implements Serializable{

    private String id;//主键
    private String productBrandId;//商品品牌属性id
    private String categoryId;//商品类别主键ID
    private String productId;//商品ID
    private String productName;//商品名称
    private String integralQuantity;//使用积分值
    private String stockNum;//商品数量
    private String exchangeStatus;//兑换状态（0，关闭；1，开启）
    private String promotionalRange;//促销范围（商品分类一级）
    private String createTime;//创建时间
    private String optTime;//操作时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductBrandId() {
        return productBrandId;
    }

    public void setProductBrandId(String productBrandId) {
        this.productBrandId = productBrandId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getIntegralQuantity() {
        return integralQuantity;
    }

    public void setIntegralQuantity(String integralQuantity) {
        this.integralQuantity = integralQuantity;
    }

    public String getStockNum() {
        return stockNum;
    }

    public void setStockNum(String stockNum) {
        this.stockNum = stockNum;
    }

    public String getExchangeStatus() {
        return exchangeStatus;
    }

    public void setExchangeStatus(String exchangeStatus) {
        this.exchangeStatus = exchangeStatus;
    }

    public String getPromotionalRange() {
        return promotionalRange;
    }

    public void setPromotionalRange(String promotionalRange) {
        this.promotionalRange = promotionalRange;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOptTime() {
        return optTime;
    }

    public void setOptTime(String optTime) {
        this.optTime = optTime;
    }
}
