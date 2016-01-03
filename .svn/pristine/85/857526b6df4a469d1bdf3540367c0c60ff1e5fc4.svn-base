package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.AddressResponse;
import com.bluemobi.ybb.network.response.SureReceiveResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhijun on 2015/8/9.
 */
public class SureReceiveRequest extends YbbHttpJsonRequest<SureReceiveResponse> {

    private static final String APIPATH = "mobi/logisticsdistributioninfo/confirmOrderLogistic";
    /**
     * 配送单id
     */
    private String id;




    public SureReceiveRequest(Response.Listener<SureReceiveResponse> listener,
                              Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    public SureReceiveRequest(int method, String partUrl,
                              Response.Listener<SureReceiveResponse> listener, Response.ErrorListener errorListener) {
        super(method, partUrl, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", String.valueOf(id));
        return map;
    }

    @Override
    public Class<SureReceiveResponse> getResponseClass() {
        return SureReceiveResponse.class;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
