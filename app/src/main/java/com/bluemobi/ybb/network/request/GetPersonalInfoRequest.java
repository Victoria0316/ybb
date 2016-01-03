package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.GetPersonalInfoResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * gaozq
 *  	P14-16获取基本信息
 */
public class GetPersonalInfoRequest extends YbbHttpJsonRequest<GetPersonalInfoResponse> {

    private static final String APIPATH = "mobi/personalcenter/getPersonalInfo";

//    userId 	string 	是 	用户ID
    private String userId;

    public GetPersonalInfoRequest(Response.Listener<GetPersonalInfoResponse> listener,
                                  Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    public GetPersonalInfoRequest(int method, String partUrl,
                                  Response.Listener<GetPersonalInfoResponse> listener, Response.ErrorListener errorListener) {
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
    public Class<GetPersonalInfoResponse> getResponseClass() {
        return GetPersonalInfoResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
