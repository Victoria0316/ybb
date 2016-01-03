package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.ShippingAddressResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/11.
 */
public class ShippingAddressRequest extends YbbHttpJsonRequest<ShippingAddressResponse>{

    private static final String APIPATH = "mobi/personalcenter/getPersonalAddress";

    private String userId; //	用户ID

    public ShippingAddressRequest(Response.Listener<ShippingAddressResponse> listener,
                             Response.ErrorListener errorListener)
    {
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
    public Class<ShippingAddressResponse> getResponseClass() {
        return ShippingAddressResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
