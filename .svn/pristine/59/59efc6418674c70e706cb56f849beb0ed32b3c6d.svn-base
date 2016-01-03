package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.AccountBillResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/13.
 */
public class AccountBillRequest extends YbbHttpJsonRequest<AccountBillResponse>{

    private static final String APIPATH = "mobi/customerrechargelog/findByPage";

    private String userId;//用户ID
    private String currentnum;//每页条数
    private String currentpage;//当前页数
    private String pageTime;//分页时间

    public AccountBillRequest(Response.Listener<AccountBillResponse> listener,
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
        map.put("userId",userId);
        map.put("pageTime",pageTime);

        return map;
    }

    @Override
    public Class<AccountBillResponse> getResponseClass() {
        return AccountBillResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
