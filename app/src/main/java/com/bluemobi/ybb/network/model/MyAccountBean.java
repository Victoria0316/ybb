package com.bluemobi.ybb.network.model;

import java.io.Serializable;

/**
 * Created by gaoyn on 2015/8/12.
 */
public class MyAccountBean implements Serializable{

    private String userId; //用户ID
    private String balanceAmount;//充值余额
    private String accountLimit;//充值限额

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(String balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public String getAccountLimit() {
        return accountLimit;
    }

    public void setAccountLimit(String accountLimit) {
        this.accountLimit = accountLimit;
    }
}
