package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.YbbHttpResponse;
import com.bluemobi.ybb.network.response.LoginResponse;

import java.util.HashMap;
import java.util.Map;

/**
 *gxy
 *
 */
public class ExtLoginRequest extends YbbHttpJsonRequest<LoginResponse>{

    private static final String APIPATH = "mobi/adminuser/InsteadLogin";
    private String userId;
    private String ssid;
    private String checkUserId;

    public ExtLoginRequest(Response.Listener<LoginResponse> listener,
                        Response.ErrorListener errorListener)
    {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    public ExtLoginRequest(int method, String partUrl,
                        Response.Listener<LoginResponse> listener, Response.ErrorListener errorListener)
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
//        map.put("userId", "ff8080814f3b97ed014f3ecf84470079");
//        map.put("ssid", "5a2e6b356e5c49ae9e003fdda015b82d");
//        map.put("checkUserId", "ff8080814f1553be014f156a58a6000b");
        map.put("userId", userId);
        map.put("ssid", ssid);
        map.put("checkUserId", checkUserId);
        return map;
    }

    @Override
    public Class<LoginResponse> getResponseClass() {
        return LoginResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(String checkUserId) {
        this.checkUserId = checkUserId;
    }
}
