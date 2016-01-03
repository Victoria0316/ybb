package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.HotRecommendResponse;
import com.bluemobi.ybb.util.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class HotRecommendRequest extends YbbHttpJsonRequest<HotRecommendResponse> {

    private static final String APIPATH = "mobi/productinfo/getTypeParams";
    private String loginuserid;
    private String advertisingPositionId;




    public HotRecommendRequest(Response.Listener<HotRecommendResponse> listener,
                               Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    public HotRecommendRequest(int method, String partUrl,
                               Response.Listener<HotRecommendResponse> listener, Response.ErrorListener errorListener) {
        super(method, partUrl, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("loginuserid", loginuserid);
        map.put("advertisingPositionId", Constants.AD_CATEGORY_ID);
        return map;
    }

    @Override
    public Class<HotRecommendResponse> getResponseClass() {
        return HotRecommendResponse.class;
    }

    public String getLoginuserid() {
        return loginuserid;
    }

    public void setLoginuserid(String loginuserid) {
        this.loginuserid = loginuserid;
    }
}
