package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.ArticleinfoResponse;

import java.util.HashMap;
import java.util.Map;

/**
 *gaozq
 *
 */
public class ArticleinfoRequest extends YbbHttpJsonRequest<ArticleinfoResponse>{

    private static final String APIPATH = "mobi/articleinfo/findByID";

//    id 	string 	是 	资讯id
//    loginuserid 	string 	是 	当前登录用户id

    private String id;
    private String loginuserid;

    public ArticleinfoRequest(Response.Listener<ArticleinfoResponse> listener,
                              Response.ErrorListener errorListener)
    {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    public ArticleinfoRequest(int method, String partUrl,
                              Response.Listener<ArticleinfoResponse> listener, Response.ErrorListener errorListener)
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
        map.put("id", id);
        map.put("loginuserid", loginuserid);
        return map;
    }

    @Override
    public Class<ArticleinfoResponse> getResponseClass() {
        return ArticleinfoResponse.class;
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
