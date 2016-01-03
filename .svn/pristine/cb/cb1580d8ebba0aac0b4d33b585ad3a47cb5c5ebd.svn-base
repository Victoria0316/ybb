package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.DeleteOrderResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/16.
 *
 * 我的订单列表删除订单
 */
public class DeleteOrderRequest extends YbbHttpJsonRequest<DeleteOrderResponse>{

    private static final String APIPATH = "mobi/orderinfo/del";

    private String orderId;//订单ID


    public DeleteOrderRequest(Response.Listener<DeleteOrderResponse> listener, Response.ErrorListener errorListener) {
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
    public Class<DeleteOrderResponse> getResponseClass() {
        return DeleteOrderResponse.class;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
