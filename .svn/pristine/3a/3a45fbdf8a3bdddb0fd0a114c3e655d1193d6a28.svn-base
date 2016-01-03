package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.YbbHttpResponse;
import com.bluemobi.ybb.network.response.InformationListResponse;
import com.bluemobi.ybb.network.util.ParameterUtil;
import com.bluemobi.ybb.network.util.SmsType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liufy on 2015/8/9.
 */
public class InformationLisRequest extends YbbHttpJsonRequest<InformationListResponse> {

    private static final String APIPATH = "mobi/articleinfo/findByPage";

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

    private String currentnum;
    private String currentpage;
    private String pageTime;

    private String columnId	;//	是	1:广告,2:资讯

    private String advertisingPositionId;//广告位ID

    public String getLoginuserid() {
        return loginuserid;
    }

    public void setLoginuserid(String loginuserid) {
        this.loginuserid = loginuserid;
    }

    private String loginuserid;

    public InformationLisRequest(Response.Listener<InformationListResponse> listener,
                                 Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    public InformationLisRequest(int method, String partUrl,
                                 Response.Listener<InformationListResponse> listener, Response.ErrorListener errorListener) {
        super(method, partUrl, listener, errorListener);
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
        map.put("loginuserid",loginuserid);
        map.put("columnId",columnId);
        map.put("pageTime",pageTime);
        map.put("advertisingPositionId",advertisingPositionId);
        return map;
    }

    @Override
    public Class<InformationListResponse> getResponseClass() {
        return InformationListResponse.class;
    }


    public String getPageTime() {
        return pageTime;
    }

    public void setPageTime(String pageTime) {
        this.pageTime = pageTime;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getAdvertisingPositionId() {
        return advertisingPositionId;
    }

    public void setAdvertisingPositionId(String advertisingPositionId) {
        this.advertisingPositionId = advertisingPositionId;
    }
}
