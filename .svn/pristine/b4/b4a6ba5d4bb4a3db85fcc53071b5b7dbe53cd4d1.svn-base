package com.bluemobi.ybb.ps.network.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.bluemobi.ybb.ps.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.ps.network.response.UploadCidResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * gaoxy  
 */
public class PsUploadCidRequest extends YbbHttpJsonRequest<UploadCidResponse> {

    private static final String APIPATH = "mobi/userinfo/updateClient";

    private String clientId;//
    private String userId;//

    public PsUploadCidRequest(Response.Listener<UploadCidResponse> listener,
                              Response.ErrorListener errorListener) {
        super(Request.Method.POST, APIPATH, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("clientId", clientId);
        map.put("userId", userId);

        return map;
    }

    @Override
    public Class<UploadCidResponse> getResponseClass() {
        return UploadCidResponse.class;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
