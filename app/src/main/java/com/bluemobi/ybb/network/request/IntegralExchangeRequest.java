package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.IntegralExchangeResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/12.
 */
public class IntegralExchangeRequest extends YbbHttpJsonRequest<IntegralExchangeResponse>{

    private static final String APIPATH = "mobi/integralcommodityinfo/findByPage";

    private String currentnum;//每页条数
    private String currentpage;//当前页数
    private String pageTime;//分页时间


    public IntegralExchangeRequest(Response.Listener<IntegralExchangeResponse> listener,
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
        map.put("currentnum", currentnum);
        map.put("currentpage", currentpage);
        map.put("pageTime",pageTime);

        return map;
    }

    @Override
    public Class<IntegralExchangeResponse> getResponseClass() {
        return IntegralExchangeResponse.class;
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

    public String getPageTime() {
        return pageTime;
    }

    public void setPageTime(String pageTime) {
        this.pageTime = pageTime;
    }
}
