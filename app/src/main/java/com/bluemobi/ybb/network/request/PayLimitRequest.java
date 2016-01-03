package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.PayLimitResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/13.
 */
public class PayLimitRequest extends YbbHttpJsonRequest<PayLimitResponse> {

    private static final String APIPATH = "mobi/personalcenter/updateAccoutLimit";

    private String userId; //用户ID
    private String accountLimit;//支付限额

    public PayLimitRequest(Response.Listener<PayLimitResponse> listener, Response.ErrorListener errorListener) {
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
        map.put("accountLimit", accountLimit);
        return map;
    }

    @Override
    public Class<PayLimitResponse> getResponseClass() {
        return PayLimitResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccountLimit() {
        return accountLimit;
    }

    public void setAccountLimit(String accountLimit) {
        this.accountLimit = accountLimit;
    }
}
