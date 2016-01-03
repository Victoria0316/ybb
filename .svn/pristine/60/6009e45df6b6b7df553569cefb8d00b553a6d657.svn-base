package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.AddressResponse;
import com.bluemobi.ybb.network.response.BalancePayResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhijun on 2015/8/9.
 */
public class BalancePayRequest extends YbbHttpJsonRequest<BalancePayResponse> {

    private static final String APIPATH = "mobi/orderinfo/balancePay";
    /**
     * 用户id
     */
    private String userId;
    /**
     * 支付金额（单位：元）
     */
    private String totalPrice;
    /**
     * 支付密码
     */
    private String paypassword;
    /**
     * 订单编号
     */
    private String orderNo;


    public BalancePayRequest(Response.Listener<BalancePayResponse> listener,
                             Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    public BalancePayRequest(int method, String partUrl,
                             Response.Listener<BalancePayResponse> listener, Response.ErrorListener errorListener) {
        super(method, partUrl, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", String.valueOf(userId));
        map.put("totalPrice", String.valueOf(totalPrice));
        map.put("paypassword", String.valueOf(paypassword));
        map.put("orderNo", String.valueOf(orderNo));
        return map;
    }

    @Override
    public Class<BalancePayResponse> getResponseClass() {
        return BalancePayResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaypassword() {
        return paypassword;
    }

    public void setPaypassword(String paypassword) {
        this.paypassword = paypassword;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
