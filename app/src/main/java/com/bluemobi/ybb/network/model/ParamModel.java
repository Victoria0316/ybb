package com.bluemobi.ybb.network.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzhijun on 2015/8/11.
 */
 public class ParamModel {
    private String type;
    private String canteen_id;
    private String orderprice_count;//未付款订单总金额",
    private String shopcart_count; //已加入购物车商品数量
    private String shopcart_shops_count; //已加入购物车商品数量
    private String shopcart_price; //已加入购物车商品数量
    private List<UserType> adminUserTypeList;
    private GoodType combo_category;
    private List<MainFoodEntity> main_foods;
    private ArrayList<ProductAttributeEntity> productAttributeInfoList;
    private StartAndEnd startAndEndTime;
    private StartAndEnd startAndEndTime_Logistics;//配送端日期
    private UserInfoDTO userInfoDTO;//配送员信息
    private String canteen_phone;//食堂电话

    public String getCateen_phone() {
        return canteen_phone;
    }

    public void setCateen_phone(String cateen_phone) {
        this.canteen_phone = cateen_phone;
    }

    public StartAndEnd getStartAndEndTime_Logistics() {
        return startAndEndTime_Logistics;
    }

    public void setStartAndEndTime_Logistics(StartAndEnd startAndEndTime_Logistics) {
        this.startAndEndTime_Logistics = startAndEndTime_Logistics;
    }

    public String getShopcart_price() {
        return shopcart_price;
    }

    public void setShopcart_price(String shopcart_price) {
        this.shopcart_price = shopcart_price;
    }

    public String getShopcart_shops_count() {
        return shopcart_shops_count;
    }

    public void setShopcart_shops_count(String shopcart_shops_count) {
        this.shopcart_shops_count = shopcart_shops_count;
    }

    public UserInfoDTO getUserInfoDTO() {
        return userInfoDTO;
    }

    public void setUserInfoDTO(UserInfoDTO userInfoDTO) {
        this.userInfoDTO = userInfoDTO;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCanteen_id() {
        return canteen_id;
    }

    public void setCanteen_id(String canteen_id) {
        this.canteen_id = canteen_id;
    }

    public String getOrderprice_count() {
        return orderprice_count;
    }

    public void setOrderprice_count(String orderprice_count) {
        this.orderprice_count = orderprice_count;
    }

    public String getShopcart_count() {
        return shopcart_count;
    }

    public void setShopcart_count(String shopcart_count) {
        this.shopcart_count = shopcart_count;
    }

    public List<UserType> getAdminUserTypeList() {
        return adminUserTypeList;
    }

    public void setAdminUserTypeList(List<UserType> adminUserTypeList) {
        this.adminUserTypeList = adminUserTypeList;
    }

    public GoodType getCombo_category() {
        return combo_category;
    }

    public void setCombo_category(GoodType combo_category) {
        this.combo_category = combo_category;
    }

    public List<MainFoodEntity> getMain_foods() {
        return main_foods;
    }

    public void setMain_foods(List<MainFoodEntity> main_foods) {
        this.main_foods = main_foods;
    }

    public ArrayList<ProductAttributeEntity> getProductAttributeInfoList() {
        return productAttributeInfoList;
    }

    public void setProductAttributeInfoList(ArrayList<ProductAttributeEntity> productAttributeInfoList) {
        this.productAttributeInfoList = productAttributeInfoList;
    }

    public StartAndEnd getStartAndEndTime() {
        return startAndEndTime;
    }

    public void setStartAndEndTime(StartAndEnd startAndEndTime) {
        this.startAndEndTime = startAndEndTime;
    }

    public static class UserType {
        private String id;//"主键id",
        private String optTime;//"操作时间",
        private String parentId;//"父用户类型ID",
        private String remark;//"后台用户",
        private String shopsId;//"所属商铺ID",
        private String status;//"逻辑删除字段(0:正常数据，1:已删除数据)",
        private String userTypeName;//"用户类型名称",

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

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getShopsId() {
            return shopsId;
        }

        public void setShopsId(String shopsId) {
            this.shopsId = shopsId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUserTypeName() {
            return userTypeName;
        }

        public void setUserTypeName(String userTypeName) {
            this.userTypeName = userTypeName;
        }
    }

    public static class GoodType {
        private List<GoodTypeEntity> entry;

        public static class GoodTypeEntity {
            private String key;
            private String value;

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public List<GoodTypeEntity> getEntry() {
            return entry;
        }

        public void setEntry(List<GoodTypeEntity> entry) {
            this.entry = entry;
        }
    }


    public static class MainFoodEntity {
        private String canteenId;//食堂id",
        private String categoryName;//商品类别名称",
        private String createTime;//创建时间",
        private String createUserId;//创建时间",
        private String createUserName;//创建时间",
        private String description;//创建时间",
        private String id;//主键id",
        private String ifShow;//主键id",
        private String optTime;//操作时间",
        private String optUserId;//操作时间",
        private String optUserName;//操作时间",
        private String parentId;//操作时间",
        private String status;//逻辑删除字段(0:有效，1：删除)",
        private String userId;//所属用户",

        public String getCanteenId() {
            return canteenId;
        }

        public void setCanteenId(String canteenId) {
            this.canteenId = canteenId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIfShow() {
            return ifShow;
        }

        public void setIfShow(String ifShow) {
            this.ifShow = ifShow;
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

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
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


    public static class ProductAttributeEntity {
        private String attributeId;//private String  商品属性id",
        private String attributeName;//private String  商品属性名称（等值信息）",
        private String backMealTime;//退餐
        private String backMealTimeType;//订餐时间类型（1，小时，2，天）",

        private String canteenId;//private String  所属食堂",
        private String createTime;//private String  创建时间",
        private String createUserId;//private String  创建用户",
        private String createUserName;//private String  创建用户名称",
        private String description;//private String  属性描述",
        private String id;//private String  主键id",
        private String maxValue;//private String  最大值（区间值信息）",
        private String minValue;//private String  最小值（区间值信息）",
        private String optTime;//private String  操作时间",
        private String optUserId;//private String  修改人",
        private String optUserName;//private String  修改人名称（username)",
        private String shopId;//private String  店铺id",
        private String showInFrontend;//private String  是否在前端显示（0不显示，1显示）",
        private String status;//private String  逻辑删除字段(0:正常数据，1:已删除数据)",
        private String orderingTime;//private String  逻辑删除字段(0:正常数据，1:已删除数据)",
        private String orderingTimeType;//private String  逻辑删除字段(0:正常数据，1:已删除数据)",

        public String getBackMealTime() {
            return backMealTime;
        }

        public void setBackMealTime(String backMealTime) {
            this.backMealTime = backMealTime;
        }

        public String getBackMealTimeType() {
            return backMealTimeType;
        }

        public void setBackMealTimeType(String backMealTimeType) {
            this.backMealTimeType = backMealTimeType;
        }

        public String getOrderingTime() {
            return orderingTime;
        }

        public void setOrderingTime(String orderingTime) {
            this.orderingTime = orderingTime;
        }

        public String getOrderingTimeType() {
            return orderingTimeType;
        }

        public void setOrderingTimeType(String orderingTimeType) {
            this.orderingTimeType = orderingTimeType;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMaxValue() {
            return maxValue;
        }

        public void setMaxValue(String maxValue) {
            this.maxValue = maxValue;
        }

        public String getMinValue() {
            return minValue;
        }

        public void setMinValue(String minValue) {
            this.minValue = minValue;
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

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getShowInFrontend() {
            return showInFrontend;
        }

        public void setShowInFrontend(String showInFrontend) {
            this.showInFrontend = showInFrontend;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class StartAndEnd {
        List<StartAndEndEntity> entry;

        public List<StartAndEndEntity> getEntry() {
            return entry;
        }

        public void setEntry(List<StartAndEndEntity> entry) {
            this.entry = entry;
        }

        public static class StartAndEndEntity {
            private String key;
            private Value value;

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public Value getValue() {
                return value;
            }

            public void setValue(Value value) {
                this.value = value;
            }

            public static class Value {
                private String type;
                private String value;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }
        }
    }

    public static class UserInfoDTO{
        private java.lang.String id;
        private java.lang.String userId;
        private java.lang.String shopsId;
        private java.lang.String deptId;
        private java.lang.String userCode;
        private java.lang.String realName;
        private java.lang.String nickName;
        private java.lang.String gender;
        private java.lang.String idcardType;
        private java.lang.String idcardNo;
        private java.lang.String idcardFacePicUrl;
        private java.lang.String idcardBackPicUrl;
        private java.lang.String cellphone;
        private java.lang.String telephone;
        private java.lang.String headPicUrl;
        private java.lang.String userLocation;
        private java.lang.String userLocationCode;
        private java.lang.String userAddress;
        private java.lang.String memberState;
        private java.lang.String experienceAmount;
        private java.lang.String growthAmount;
        private java.lang.String createTime;
        private java.lang.String userSsid;
        private java.lang.String userSalt;
        private java.lang.String email;
        private java.lang.String systemStyle;
        private java.lang.String infoState;
        private java.lang.String logTime;
        private java.lang.String vipStartTime;
        private java.lang.String vipEndTime;
        private String blackListState;
        private java.lang.String blackReason;
        private java.lang.String blackTime;
        private java.lang.String blackEndTime;
        private String auditingState;
        private String accountState;
        private String personalInfoPerfectState;
        private java.lang.String pushCode;
        private java.lang.String rechargePassword;
        private java.lang.String createUserId;
        private java.lang.String createUserName;
        private java.lang.String optUserId;
        private java.lang.String optUserName;
        private java.lang.String optTime;
        private String status;
        private java.lang.String remark;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getShopsId() {
            return shopsId;
        }

        public void setShopsId(String shopsId) {
            this.shopsId = shopsId;
        }

        public String getDeptId() {
            return deptId;
        }

        public void setDeptId(String deptId) {
            this.deptId = deptId;
        }

        public String getUserCode() {
            return userCode;
        }

        public void setUserCode(String userCode) {
            this.userCode = userCode;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getIdcardType() {
            return idcardType;
        }

        public void setIdcardType(String idcardType) {
            this.idcardType = idcardType;
        }

        public String getIdcardNo() {
            return idcardNo;
        }

        public void setIdcardNo(String idcardNo) {
            this.idcardNo = idcardNo;
        }

        public String getIdcardFacePicUrl() {
            return idcardFacePicUrl;
        }

        public void setIdcardFacePicUrl(String idcardFacePicUrl) {
            this.idcardFacePicUrl = idcardFacePicUrl;
        }

        public String getIdcardBackPicUrl() {
            return idcardBackPicUrl;
        }

        public void setIdcardBackPicUrl(String idcardBackPicUrl) {
            this.idcardBackPicUrl = idcardBackPicUrl;
        }

        public String getCellphone() {
            return cellphone;
        }

        public void setCellphone(String cellphone) {
            this.cellphone = cellphone;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getHeadPicUrl() {
            return headPicUrl;
        }

        public void setHeadPicUrl(String headPicUrl) {
            this.headPicUrl = headPicUrl;
        }

        public String getUserLocation() {
            return userLocation;
        }

        public void setUserLocation(String userLocation) {
            this.userLocation = userLocation;
        }

        public String getUserLocationCode() {
            return userLocationCode;
        }

        public void setUserLocationCode(String userLocationCode) {
            this.userLocationCode = userLocationCode;
        }

        public String getUserAddress() {
            return userAddress;
        }

        public void setUserAddress(String userAddress) {
            this.userAddress = userAddress;
        }

        public String getMemberState() {
            return memberState;
        }

        public void setMemberState(String memberState) {
            this.memberState = memberState;
        }

        public String getExperienceAmount() {
            return experienceAmount;
        }

        public void setExperienceAmount(String experienceAmount) {
            this.experienceAmount = experienceAmount;
        }

        public String getGrowthAmount() {
            return growthAmount;
        }

        public void setGrowthAmount(String growthAmount) {
            this.growthAmount = growthAmount;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUserSsid() {
            return userSsid;
        }

        public void setUserSsid(String userSsid) {
            this.userSsid = userSsid;
        }

        public String getUserSalt() {
            return userSalt;
        }

        public void setUserSalt(String userSalt) {
            this.userSalt = userSalt;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSystemStyle() {
            return systemStyle;
        }

        public void setSystemStyle(String systemStyle) {
            this.systemStyle = systemStyle;
        }

        public String getInfoState() {
            return infoState;
        }

        public void setInfoState(String infoState) {
            this.infoState = infoState;
        }

        public String getLogTime() {
            return logTime;
        }

        public void setLogTime(String logTime) {
            this.logTime = logTime;
        }

        public String getVipStartTime() {
            return vipStartTime;
        }

        public void setVipStartTime(String vipStartTime) {
            this.vipStartTime = vipStartTime;
        }

        public String getVipEndTime() {
            return vipEndTime;
        }

        public void setVipEndTime(String vipEndTime) {
            this.vipEndTime = vipEndTime;
        }

        public String getBlackListState() {
            return blackListState;
        }

        public void setBlackListState(String blackListState) {
            this.blackListState = blackListState;
        }

        public String getBlackReason() {
            return blackReason;
        }

        public void setBlackReason(String blackReason) {
            this.blackReason = blackReason;
        }

        public String getBlackTime() {
            return blackTime;
        }

        public void setBlackTime(String blackTime) {
            this.blackTime = blackTime;
        }

        public String getBlackEndTime() {
            return blackEndTime;
        }

        public void setBlackEndTime(String blackEndTime) {
            this.blackEndTime = blackEndTime;
        }

        public String getAuditingState() {
            return auditingState;
        }

        public void setAuditingState(String auditingState) {
            this.auditingState = auditingState;
        }

        public String getAccountState() {
            return accountState;
        }

        public void setAccountState(String accountState) {
            this.accountState = accountState;
        }

        public String getPersonalInfoPerfectState() {
            return personalInfoPerfectState;
        }

        public void setPersonalInfoPerfectState(String personalInfoPerfectState) {
            this.personalInfoPerfectState = personalInfoPerfectState;
        }

        public String getPushCode() {
            return pushCode;
        }

        public void setPushCode(String pushCode) {
            this.pushCode = pushCode;
        }

        public String getRechargePassword() {
            return rechargePassword;
        }

        public void setRechargePassword(String rechargePassword) {
            this.rechargePassword = rechargePassword;
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

        public String getOptTime() {
            return optTime;
        }

        public void setOptTime(String optTime) {
            this.optTime = optTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

}
