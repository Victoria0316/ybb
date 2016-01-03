package com.bluemobi.ybb.ps.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.ps.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.ps.network.response.MineResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/18.
 */
public class MineRequest extends YbbHttpJsonRequest<MineResponse> {

    private static final String APIPATH = "mobi/personalcenter/getExpressPersonalInfo";

    private String userId;//用户ID

    public MineRequest(Response.Listener<MineResponse> listener, Response.ErrorListener errorListener) {
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
    public Class<MineResponse> getResponseClass() {
        return MineResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
