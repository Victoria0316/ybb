package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.MyAccountResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/12.
 */
public class MyAccountRequest extends YbbHttpJsonRequest<MyAccountResponse>{

    private static final String APIPATH = "mobi/personalcenter/getPersonalAccount";

    private String userId; //用户ID


    public MyAccountRequest(Response.Listener<MyAccountResponse> listener, Response.ErrorListener errorListener) {
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

        return map;
    }

    @Override
    public Class<MyAccountResponse> getResponseClass() {
        return MyAccountResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
