package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.FoodProductDetailResponse;
import com.bluemobi.ybb.network.response.NutritiousFoodDetailsResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * wangzhijun
 */
public class FoodProductDetailRequest extends YbbHttpJsonRequest<FoodProductDetailResponse> {

    private static final String APIPATH = "mobi/productinfo/findByID";

    private String loginuserid;
    private String id;
    /**
     * 餐次id
     */
    private String attributeId;
    /**
     * 套餐类型（1营养餐2零点餐3医护套餐4医患套餐）
     */
    private String categoryId;

    public FoodProductDetailRequest(Response.Listener<FoodProductDetailResponse> listener,
                                    Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    public FoodProductDetailRequest(int method, String partUrl,
                                    Response.Listener<FoodProductDetailResponse> listener, Response.ErrorListener errorListener) {
        super(method, partUrl, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("loginuserid", loginuserid);
        map.put("id", id);
        map.put("attributeId", attributeId);
        map.put("categoryId", categoryId);
        return map;
    }

    @Override
    public Class<FoodProductDetailResponse> getResponseClass() {
        return FoodProductDetailResponse.class;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginuserid() {
        return loginuserid;
    }

    public void setLoginuserid(String loginuserid) {
        this.loginuserid = loginuserid;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
