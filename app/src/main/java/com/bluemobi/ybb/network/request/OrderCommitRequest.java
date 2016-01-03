package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.activity.ModificationAddressMedicalActivity;
import com.bluemobi.ybb.activity.ModificationAddressPatientActivity;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.model.OrderMakeModel;
import com.bluemobi.ybb.network.model.OrderMakeRequestModel;
import com.bluemobi.ybb.network.response.AddressResponse;
import com.bluemobi.ybb.network.response.OrderCommitResponse;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangzhijun on 2015/8/9.
 */
public class OrderCommitRequest extends YbbHttpJsonRequest<OrderCommitResponse> {

    private static final String APIPATH = "mobi/orderinfo/addOrder";
    private String orderSource;//	string	是	终端类型（0--未知，1--IOS，2--android，3--网站，4--微信）
    private String totalAmount;//	string	是	订单总金额
    private List<OrderMakeModel> dataBody;//	string	是	订单内容（json数组）
    private String orderType;//	string	是	订单类型 订单类型（1--患者，2--医护）
    private String userId;
    private String reserveTime;

    public OrderCommitRequest(Response.Listener<OrderCommitResponse> listener,
                              Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    public OrderCommitRequest(int method, String partUrl,
                              Response.Listener<OrderCommitResponse> listener, Response.ErrorListener errorListener) {
        super(method, partUrl, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("orderSource", String.valueOf("2"));//android
        map.put("reserveTime", String.valueOf(reserveTime));
        map.put("totalAmount", String.valueOf(totalAmount));
        String orderType = "1";
        if (YbbApplication.getInstance().role_bh.equals(YbbApplication.getInstance()
        .getMyUserInfo().getTypeId())) {//病患
            orderType = "1";
            //病患
        }
        if (YbbApplication.getInstance().role_yh.equals(YbbApplication.getInstance()
                .getMyUserInfo().getTypeId())) {
            orderType = "2";
            //医护
        }
        map.put("orderType", orderType);

        map.put("userId",  String.valueOf(YbbApplication.getInstance()
                .getMyUserInfo().getUserId()));
//        Gson gson = new Gson();
////        String json = gson.toJson(dataBody);
//        map.put("body", dataBody);
        return map;
    }

    @Override
    protected Map<String, Object> getExtParams() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("body", dataBody);
        return map;
    }

    @Override
    public Class<OrderCommitResponse> getResponseClass() {
        return OrderCommitResponse.class;
    }



    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }



    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<OrderMakeModel> getDataBody() {
        return dataBody;
    }

    public void setDataBody(List<OrderMakeModel> dataBody) {
        this.dataBody = dataBody;
    }

    public String getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(String reserveTime) {
        this.reserveTime = reserveTime;
    }
}
