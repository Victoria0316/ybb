package com.bluemobi.ybb.network.model;

/**
 * Created by wangzhijun on 2015/9/14.
 */
public class ProductCategory {
    private String categoryName;// "菜谱类别名称:回民灶等",
    private String canteenId;// "ff8080814f6e6a36014f72009a0300f4",
    private String categoryId;// "ff8080814f79a18c014f79b6914b0007",
    private String createTime;// "2015-09-1409:47:32",
    private String id;// "ff8080814fb6a6b1014fc98925b401ab",
    private String optTime;// "2015-09-14T09:47:33+08:00",
    private String productId;// "ff8080814fb6a6b1014fc98924f701a7",
    private String status;// 0,

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCanteenId() {
        return canteenId;
    }

    public void setCanteenId(String canteenId) {
        this.canteenId = canteenId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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

    public String getOptTime() {
        return optTime;
    }

    public void setOptTime(String optTime) {
        this.optTime = optTime;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
