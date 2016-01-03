package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.PayPwdModifyResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/12.
 */
public class PayPwdModifyRequest extends YbbHttpJsonRequest<PayPwdModifyResponse>{

    private static final String APIPATH = "mobi/personalcenter/updateAccoutPassword";

    private String userId; //用户ID
    private String password ;	//	支付密码


    public PayPwdModifyRequest(Response.Listener<PayPwdModifyResponse> listener, Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userId);
        map.put("password", password);

        return map;
    }

    @Override
    public Class<PayPwdModifyResponse> getResponseClass() {
        return PayPwdModifyResponse.class;
    }

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
