package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.CommonResponse;
import com.bluemobi.ybb.network.response.ShopCartResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liufy on 2015/8/13.
 * P6-4查看购物车
 */
public class ShopCartRequest extends YbbHttpJsonRequest<ShopCartResponse> {

    private static final String APIPATH = "mobi/shoppingcart/getShopCart";

    private String userId	;//string	是	用户ID

    public String getCurrentnum() {
        return currentnum;
    }

    public void setCurrentnum(String currentnum) {
        this.currentnum = currentnum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCurrentpage() {
        return currentpage;
    }

    public void setCurrentpage(String currentpage) {
        this.currentpage = currentpage;
    }

    private String  currentnum	;//	是	每页条数
    private String  currentpage	;//	是	当前页数
    private String  pageTime	;//	否	分页时间



    public ShopCartRequest(Response.Listener<ShopCartResponse> listener,
                           Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    public ShopCartRequest(int method, String partUrl,
                           Response.Listener<ShopCartResponse> listener, Response.ErrorListener errorListener) {
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
        map.put("currentnum",currentnum);
        map.put("currentpage",currentpage);
        return map;
    }

    @Override
    public Class<ShopCartResponse> getResponseClass() {
        return ShopCartResponse.class;
    }


}
