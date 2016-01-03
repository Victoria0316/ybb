package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.AddressResponse;
import com.bluemobi.ybb.network.response.CommonResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liufy on 2015/8/13.
 */
public class AddToShopCartRequest extends YbbHttpJsonRequest<CommonResponse> {

    private static final String APIPATH = "mobi/shoppingcart/addToShopCart";

    private String userId	;//是	用户ID
    private String productId	;//	是	商品ID
    private String productNum	;//	是	数量
    private String reserveTime ;//预定日期
    private String categoryId ;//1营养餐2零点餐3医护套餐4病患套餐
    private String attributeId ;//餐次

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public AddToShopCartRequest(Response.Listener<CommonResponse> listener,
                                Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    public AddToShopCartRequest(int method, String partUrl,
                                Response.Listener<CommonResponse> listener, Response.ErrorListener errorListener) {
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
        map.put("productId",productId);
        map.put("productNum",productNum);
        map.put("reserveTime",reserveTime);
        map.put("categoryId",categoryId);
        map.put("attributeId",attributeId);
        return map;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public Class<CommonResponse> getResponseClass() {
        return CommonResponse.class;
    }


    public String getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(String reserveTime) {
        this.reserveTime = reserveTime;
    }
}
