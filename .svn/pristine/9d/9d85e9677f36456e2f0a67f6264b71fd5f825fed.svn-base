package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.AccountBillResponse;
import com.bluemobi.ybb.network.response.WxOrderResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付订单接口
 */
public class WxOrderRequest extends YbbHttpJsonRequest<WxOrderResponse> {

    private static final String APIPATH = "mobi/orderinfo/wxpay";
    private String orderNo;//string	是	订单号
    private String totalPrice;//	string	是	支付总金额，单位：元
    private String spbillCreateIp ="8.8.8.8";//	string	是	终端IP

    public WxOrderRequest(Response.Listener<WxOrderResponse> listener,
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
        map.put("orderNo", orderNo);
        map.put("totalPrice", totalPrice);
        map.put("spbillCreateIp", spbillCreateIp);
        return map;
    }

    @Override
    public Class<WxOrderResponse> getResponseClass() {
        return WxOrderResponse.class;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }
}
