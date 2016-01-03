package com.bluemobi.ybb.network.model;

import java.io.Serializable;

/**
 * Created by gaozq on 2015/8/10.
 * 个人信息
 */
public class PersonalInfo implements Serializable {

//    "data": {
//        "type": "personalInfoDTO",
//                "availableAmount": 0.0,
//                "collConsultingCount": 0,
//                "collGoodCount": 0,
//                "collMealCount": 0,
//                "nickName": "gzq",
//                "userId": "ff8080814f1553be014f15a19a2a0016"
//    headPicUrl
//    },
    private String type;
    private String availableAmount;
    private String collConsultingCount;
    private String collGoodCount;
    private String collMealCount;
    private String nickName;
    private String userId;
    private String headPicUrl;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCollMealCount() {
        return collMealCount;
    }

    public void setCollMealCount(String collMealCount) {
        this.collMealCount = collMealCount;
    }

    public String getCollGoodCount() {
        return collGoodCount;
    }

    public void setCollGoodCount(String collGoodCount) {
        this.collGoodCount = collGoodCount;
    }

    public String getCollConsultingCount() {
        return collConsultingCount;
    }

    public void setCollConsultingCount(String collConsultingCount) {
        this.collConsultingCount = collConsultingCount;
    }

    public String getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(String availableAmount) {
        this.availableAmount = availableAmount;
    }

    public String getHeadPicUrl() {
        return headPicUrl;
    }

    public void setHeadPicUrl(String headPicUrl) {
        this.headPicUrl = headPicUrl;
    }
}
