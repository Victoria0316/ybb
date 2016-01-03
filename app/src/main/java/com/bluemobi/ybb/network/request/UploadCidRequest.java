package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.UploadCidResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * gaoxy  
 */
public class UploadCidRequest extends YbbHttpJsonRequest<UploadCidResponse>{

    private static final String APIPATH = "mobi/customerinfo/updateClient";

    private String clientId;//
    private String userId;//

    public UploadCidRequest(Response.Listener<UploadCidResponse> listener,
                            Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
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
