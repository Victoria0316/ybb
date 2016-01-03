package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.CommentsListResponse;
import com.bluemobi.ybb.network.response.CommentsListResponse;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class CommentsListRequest extends YbbHttpJsonRequest<CommentsListResponse>{

    private static final String APIPATH = "mobi/commentinfo/findByPage";

    private String productId;//用户ID
    private String currentnum;//每页条数
    private String currentpage;//当前页数
    private String pageTime;//分页时间

    public CommentsListRequest(Response.Listener<CommentsListResponse> listener,
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
        map.put("productId",productId);
        map.put("pageTime",pageTime);

        return map;
    }

    @Override
    public Class<CommentsListResponse> getResponseClass() {
        return CommentsListResponse.class;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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
