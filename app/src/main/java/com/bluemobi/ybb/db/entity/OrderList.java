package com.bluemobi.ybb.db.entity;//

import java.io.Serializable;//

/**
 * Created by gaoxy on 2015/8/20.
 */
public class OrderList implements Serializable {
    private String orderNo;//": "201508080007",
    private  String commentStatus;// 0,
    private  String         createTime;// 2015-08-0818;//51;//08,
    private  String         id;// 8aba20d94f0cef3e014f0cefa5b50001,
    private  String         itemType;// 1,
    private  String        optTime;// 2015-08-08T18;//51;//08+08;//00,
    private  String         orderId;// 201508080007,
    private  String     productId;//1,
    private  String            productName;//拉面,
    private  String            productNum;//2,
    private  String            productPrice;//15.5,
    private  String            productTotalAmount;//31,
    private  String           shopId;//8aba20b74ed76fa3014ed771094b0002,
    private  String           status;//0,
    private  String           userId;//8aba20c94f00c82f014f00efcbbf0006,
    private  OrderBean         productComboGroupDTO;//

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getOptTime() {
        return optTime;
    }

    public void setOptTime(String optTime) {
        this.optTime = optTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductTotalAmount() {
        return productTotalAmount;
    }

    public void setProductTotalAmount(String productTotalAmount) {
        this.productTotalAmount = productTotalAmount;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public OrderBean getProductComboGroupDTO() {
        return productComboGroupDTO;
    }

    public void setProductComboGroupDTO(OrderBean productComboGroupDTO) {
        this.productComboGroupDTO = productComboGroupDTO;
    }
}
