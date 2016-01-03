package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.model.EditShopCarDto;
import com.bluemobi.ybb.network.model.EditShopCarModel;
import com.bluemobi.ybb.network.model.OrderMakeModel;
import com.bluemobi.ybb.network.response.CommonResponse;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liufy on 2015/8/13.
 * P6-4编辑购物车
 */
public class EditShopCartRequest extends YbbHttpJsonRequest<CommonResponse> {

    private static final String APIPATH = "mobi/shoppingcart/editShopCart4Android";

    public EditShopCarModel getJsonBody() {
        return jsonBody;
    }

    public void setJsonBody(EditShopCarModel jsonBody) {
        this.jsonBody = jsonBody;
    }
    private String userId;

    private String type;


    private EditShopCarModel jsonBody;

    private List<EditShopCarDto> lists;

    public EditShopCartRequest(Response.Listener<CommonResponse> listener,
                               Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }
    public EditShopCartRequest(int method, String partUrl,
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
//        Gson gson = new Gson();
//        map.put("jsonBody", gson.toJson(jsonBody));
//        map.put("type",type);
        map.put("userId",userId);
//        Logger.e("wangzhijun", gson.toJson(jsonBody));
//        Logger.e("wangzhijun", gson.toJson(map));

        return map;
    }

    @Override
    protected Map<String, Object> getExtParams() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("info", lists);
        return map;
    }


    @Override
    public Class<CommonResponse> getResponseClass() {
        return CommonResponse.class;
    }

    public List<EditShopCarDto> getLists() {
        return lists;
    }

    public void setLists(List<EditShopCarDto> lists) {
        this.lists = lists;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
