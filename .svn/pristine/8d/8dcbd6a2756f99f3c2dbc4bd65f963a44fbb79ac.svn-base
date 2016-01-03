package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.MyInfoResponse;
import com.bluemobi.ybb.network.response.MyInfoResponse;

import java.util.HashMap;
import java.util.Map;

/**
 *gxy
 * p14 我的信息
 *
 */
public class MyInfoRequest extends YbbHttpJsonRequest<MyInfoResponse>{

    private static final String APIPATH = "mobi/personalcenter/getPersonalInfo";
    private String userId;

    public MyInfoRequest(Response.Listener<MyInfoResponse> listener,
                         Response.ErrorListener errorListener)
    {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    public MyInfoRequest(int method, String partUrl,
                         Response.Listener<MyInfoResponse> listener, Response.ErrorListener errorListener)
    {
        super(method, partUrl, listener, errorListener);
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
    public Class<MyInfoResponse> getResponseClass() {
        return MyInfoResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
