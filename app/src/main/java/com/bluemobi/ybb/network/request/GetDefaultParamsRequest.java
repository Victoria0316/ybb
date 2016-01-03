package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.GetDefaultParamsResponse;

import java.util.HashMap;
import java.util.Map;

/**
 *gaozq
 *
 */
public class GetDefaultParamsRequest extends YbbHttpJsonRequest<GetDefaultParamsResponse>{

    private static final String APIPATH = "mobi/productinfo/getDefaultParams";

    public GetDefaultParamsRequest(Response.Listener<GetDefaultParamsResponse> listener,
                                   Response.ErrorListener errorListener)
    {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    public GetDefaultParamsRequest(int method, String partUrl,
                                   Response.Listener<GetDefaultParamsResponse> listener, Response.ErrorListener errorListener)
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
        return map;
    }

    @Override
    public Class<GetDefaultParamsResponse> getResponseClass() {
        return GetDefaultParamsResponse.class;
    }

}
