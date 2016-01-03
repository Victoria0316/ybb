package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.ConfirmGoodsResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/16.
 *
 * 订单确认收货
 */
public class ConfirmGoodsRequest extends YbbHttpJsonRequest<ConfirmGoodsResponse>{

    private static final String APIPATH = "mobi/orderinfo/confirmOrder";

    private String orderId;//订单ID


    public ConfirmGoodsRequest(Response.Listener<ConfirmGoodsResponse> listener, Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("orderId", orderId);
        return map;
    }

    @Override
    public Class<ConfirmGoodsResponse> getResponseClass() {
        return ConfirmGoodsResponse.class;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
