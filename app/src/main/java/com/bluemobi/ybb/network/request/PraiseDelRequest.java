package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.PraiseDelResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/16.
 */
public class PraiseDelRequest extends YbbHttpJsonRequest<PraiseDelResponse>{

    private static final String APIPATH = "mobi/praiseinfo/update";

    private String praiseId;//点赞信息主键id
    private String praiseType;//点赞信息类型 1:店铺，2:商品，3:资讯
    private String userId; //用户id


    public PraiseDelRequest(Response.Listener<PraiseDelResponse> listener, Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("praiseId", praiseId);
        map.put("praiseType", praiseType);
        map.put("userId", userId);
        return map;
    }

    @Override
    public Class<PraiseDelResponse> getResponseClass() {
        return PraiseDelResponse.class;
    }

    public String getPraiseId() {
        return praiseId;
    }

    public void setPraiseId(String praiseId) {
        this.praiseId = praiseId;
    }

    public String getPraiseType() {
        return praiseType;
    }

    public void setPraiseType(String praiseType) {
        this.praiseType = praiseType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
