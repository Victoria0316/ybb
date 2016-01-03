package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.AccountBillResponse;
import com.bluemobi.ybb.network.response.OrderDetailResponse;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 订单详情
 */
public class OrderDetailRequest extends YbbHttpJsonRequest<OrderDetailResponse>{

    private static final String APIPATH = "mobi/orderinfo/findByID";

    private String orderId;//订单ID

    public OrderDetailRequest(Response.Listener<OrderDetailResponse> listener,
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
        map.put("orderId", orderId);
        return map;
    }

    @Override
    public Class<OrderDetailResponse> getResponseClass() {
        return OrderDetailResponse.class;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
