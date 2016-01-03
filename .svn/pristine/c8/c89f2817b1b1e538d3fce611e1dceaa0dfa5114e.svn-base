package com.bluemobi.ybb.ps.network.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.bluemobi.ybb.ps.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.ps.network.response.CheckVersionResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhijun on 2015/8/9.
 */
public class CheckVersionRequest extends YbbHttpJsonRequest<CheckVersionResponse> {

    private static final String APIPATH = "mobi/trendversion/getVersion";
    /**
     * 产品名称（医患端，商户端，配送端）
     */
    private String type;//	string	是
    /**
     * web,iphone,aphone,ipad,ios,android,apad）
     */
    private String platform;//	string;
    /**
     * 版本类型（1，测试版，2正式版）
     */
    private String vType;

    public CheckVersionRequest(Response.Listener<CheckVersionResponse> listener,
                               Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    public CheckVersionRequest(int method, String partUrl,
                               Response.Listener<CheckVersionResponse> listener, Response.ErrorListener errorListener) {
        super(method, partUrl, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("type", String.valueOf("配送端"));
        map.put("vType", String.valueOf(2));
        map.put("platform", String.valueOf("android"));
        return map;
    }

    @Override
    public Class<CheckVersionResponse> getResponseClass() {
        return CheckVersionResponse.class;
    }

}
