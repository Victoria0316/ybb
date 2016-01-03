package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.OrderinfoResponse;

import java.util.HashMap;
import java.util.Map;

/**
 *gaozq
 *
 */
public class OrderinfoRequest extends YbbHttpJsonRequest<OrderinfoResponse>{

    private static final String APIPATH = "mobi/orderinfo/findByPage";

//    userId 	string 	是 	用户id
//    orderStatus 	string 	是 	需要查询的订单类型【1：未付款 2：已付款(待发货) 3：已付款（已发货，待收货） 4：已收货(待评价) 5：已完成 6：已退单】
//    currentnum 	string 	是 	每页条数
//    currentpage 	string 	是 	当前页数（起始0）

    private String userId;
    private String orderStatus;
    private String currentnum;
    private String currentpage;

    public OrderinfoRequest(Response.Listener<OrderinfoResponse> listener,
                            Response.ErrorListener errorListener)
    {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    public OrderinfoRequest(int method, String partUrl,
                            Response.Listener<OrderinfoResponse> listener, Response.ErrorListener errorListener)
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
        map.put("userId", userId);
        map.put("orderStatus", orderStatus);
        map.put("currentnum", currentnum);
        map.put("currentpage",currentpage);
        return map;
    }

    @Override
    public Class<OrderinfoResponse> getResponseClass() {
        return OrderinfoResponse.class;
    }


    public String getCurrentnum() {
        return currentnum;
    }

    public void setCurrentnum(String currentnum) {
        this.currentnum = currentnum;
    }

    public String getCurrentpage() {
        return currentpage;
    }

    public void setCurrentpage(String currentpage) {
        this.currentpage = currentpage;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
