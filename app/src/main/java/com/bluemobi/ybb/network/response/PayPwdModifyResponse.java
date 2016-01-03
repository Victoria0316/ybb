package com.bluemobi.ybb.network.response;

import com.bluemobi.ybb.network.YbbHttpResponse;

/**
 * Created by gaoyn on 2015/8/12.
 */
public class PayPwdModifyResponse extends YbbHttpResponse{
    private String userId; //用户ID
    private String password; //支付密码

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
