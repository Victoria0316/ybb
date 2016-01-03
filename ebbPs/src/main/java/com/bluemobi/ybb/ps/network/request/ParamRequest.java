package com.bluemobi.ybb.ps.network.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.bluemobi.ybb.ps.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.ps.network.response.ParamResponse;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhijun on 2015/8/9.
 */
public class ParamRequest extends YbbHttpJsonRequest<ParamResponse> {

    private static final String APIPATH = "mobi/productinfo/getAllParams";

    private String userId;

    public ParamRequest(Response.Listener<ParamResponse> listener,
                        Response.ErrorListener errorListener) {
        super(Request.Method.POST, APIPATH, listener, errorListener);
    }

    public ParamRequest(int method, String partUrl,
                        Response.Listener<ParamResponse> listener, Response.ErrorListener errorListener) {
        super(method, partUrl, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", String.valueOf(userId));
        return map;
    }

    @Override
    public Class<ParamResponse> getResponseClass() {
        return ParamResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
