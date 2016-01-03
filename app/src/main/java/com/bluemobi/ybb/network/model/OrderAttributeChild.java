package com.bluemobi.ybb.network.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by wangzhijun on 2015/8/26.
 */
public class OrderAttributeChild implements Serializable {
    private String attributeId;//"8aba20e84ef857e1014ef85af5940003",
    private String attributeName;//"午餐",
    private String canteenId;//"8aba20b74efc0488014efc4ec83f0008",
    private String categoryId;//3,
    private String categoryName;//3,
    private String combogroup_categoryId;
    private String cellPhone;//"13501041010",
    private String collectCount;//0,
    private String comboName;//"碳烤琵琶腿饭",
    private String comboPrice;//15,
    private String commentCount;//0,
    private String commentStar;//0,
    private String createTime;//"2015-08-0808:53:27",
    private String createUserId;//"1",
    private String createUserName;//"1",
    private String customerPrice;//42,
    private String description;//42,
    private String freightTemplateId;
    private String endTime;//"2015-08-2113:00:00",
    private String id;//"25",
    private String imgPath;//"null",
    private ArrayList<String> imgList;
    private String isColl;// 0,
    private String imgStr;//
    private String isRecommend;//
    private String isShelves;//
    private String num;// 1,
    private String pinyin;// 1,
    private String optTime;// "2015-08-10T10:42:08+08:00",
    private String optUserId;// "1",
    private String optUserName;// "1",

    private String productBarcode;//"null",
    private String productBrandId;//"null",
    private String productBrandName;//"null",
    private String productBurdening;//"null",
    private String productName;//"糖醋肉段",
    private String productNo;//"2015090100000002",
    private String productPlace;//"null",
    private String productPurpose;//"null",
    private String productSize;//"null",
    private String productTechnique;//"null",
    private String productUnit;//"null",
    private String productionDate;//"null",
    private String recommendSerialNo;//"null",
    private String remark;//"null",
    private String saleCount;//2,
    private String sellPrice;//30,
    private String shelvesTime;//"null",
    private String shopName;//"协和医院员工食堂",
    private String shopsId;//"null",
    private String startTime;//"2015-09-01",
    private String status;//0,
    private String supplierId;//"null",
    private String userId;//"ff8080814f6e6a36014f721cdd540141",


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCombogroup_categoryId() {
        return combogroup_categoryId;
    }

    public void setCombogroup_categoryId(String combogroup_categoryId) {
        this.combogroup_categoryId = combogroup_categoryId;
    }

    public String getCustomerPrice() {
        return customerPrice;
    }

    public void setCustomerPrice(String customerPrice) {
        this.customerPrice = customerPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFreightTemplateId() {
        return freightTemplateId;
    }

    public void setFreightTemplateId(String freightTemplateId) {
        this.freightTemplateId = freightTemplateId;
    }

    public ArrayList<String> getImgList() {
        return imgList;
    }

    public void setImgList(ArrayList<String> imgList) {
        this.imgList = imgList;
    }

    public String getImgStr() {
        return imgStr;
    }

    public void setImgStr(String imgStr) {
        this.imgStr = imgStr;
    }

    public String getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(String isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getIsShelves() {
        return isShelves;
    }

    public void setIsShelves(String isShelves) {
        this.isShelves = isShelves;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getProductBarcode() {
        return productBarcode;
    }

    public void setProductBarcode(String productBarcode) {
        this.productBarcode = productBarcode;
    }

    public String getProductBrandId() {
        return productBrandId;
    }

    public void setProductBrandId(String productBrandId) {
        this.productBrandId = productBrandId;
    }

    public String getProductBrandName() {
        return productBrandName;
    }

    public void setProductBrandName(String productBrandName) {
        this.productBrandName = productBrandName;
    }

    public String getProductBurdening() {
        return productBurdening;
    }

    public void setProductBurdening(String productBurdening) {
        this.productBurdening = productBurdening;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductPlace() {
        return productPlace;
    }

    public void setProductPlace(String productPlace) {
        this.productPlace = productPlace;
    }

    public String getProductPurpose() {
        return productPurpose;
    }

    public void setProductPurpose(String productPurpose) {
        this.productPurpose = productPurpose;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public String getProductTechnique() {
        return productTechnique;
    }

    public void setProductTechnique(String productTechnique) {
        this.productTechnique = productTechnique;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public String getRecommendSerialNo() {
        return recommendSerialNo;
    }

    public void setRecommendSerialNo(String recommendSerialNo) {
        this.recommendSerialNo = recommendSerialNo;
    }

    public String getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(String saleCount) {
        this.saleCount = saleCount;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getShelvesTime() {
        return shelvesTime;
    }

    public void setShelvesTime(String shelvesTime) {
        this.shelvesTime = shelvesTime;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
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

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(String collectCount) {
        this.collectCount = collectCount;
    }

    public String getComboName() {
        return comboName;
    }

    public void setComboName(String comboName) {
        this.comboName = comboName;
    }

    public String getComboPrice() {
        return comboPrice;
    }

    public void setComboPrice(String comboPrice) {
        this.comboPrice = comboPrice;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getCommentStar() {
        return commentStar;
    }

    public void setCommentStar(String commentStar) {
        this.commentStar = commentStar;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getIsColl() {
        return isColl;
    }

    public void setIsColl(String isColl) {
        this.isColl = isColl;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getOptTime() {
        return optTime;
    }

    public void setOptTime(String optTime) {
        this.optTime = optTime;
    }

    public String getOptUserId() {
        return optUserId;
    }

    public void setOptUserId(String optUserId) {
        this.optUserId = optUserId;
    }

    public String getOptUserName() {
        return optUserName;
    }

    public void setOptUserName(String optUserName) {
        this.optUserName = optUserName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopsId() {
        return shopsId;
    }

    public void setShopsId(String shopsId) {
        this.shopsId = shopsId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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
}
