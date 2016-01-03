package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.NutritiousFoodDetailsResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * gaozq
 */
public class NutritiousFoodDetailsRequest extends YbbHttpJsonRequest<NutritiousFoodDetailsResponse> {

    private static final String APIPATH = "mobi/productcombogroup/findByID";

    private String loginuserid;
    private String id;

    public NutritiousFoodDetailsRequest(Response.Listener<NutritiousFoodDetailsResponse> listener,
                                        Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    public NutritiousFoodDetailsRequest(int method, String partUrl,
                                        Response.Listener<NutritiousFoodDetailsResponse> listener, Response.ErrorListener errorListener) {
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
        return map;
    }

    @Override
    public Class<NutritiousFoodDetailsResponse> getResponseClass() {
        return NutritiousFoodDetailsResponse.class;
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
}
