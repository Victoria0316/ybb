package com.bluemobi.ybb.network.model;

import java.io.Serializable;

/**
 * Created by gaoyn on 2015/8/12.
 */
public class IntegralBillInfo implements Serializable{

    private String id; //主键id
    private String userId; //用户id
    private String nickName; //用户昵称
    private String cellphone; //用户手机号
    private String integralType; //记录类型(0:增加；1：减少)
    private String reason; //积分增加/使用原因
    private String availableValue;//积分值
    private String createTime; //创建时间
    private String optTime; //操作时间

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getIntegralType() {
        return integralType;
    }

    public void setIntegralType(String integralType) {
        this.integralType = integralType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAvailableValue() {
        return availableValue;
    }

    public void setAvailableValue(String availableValue) {
        this.availableValue = availableValue;
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
