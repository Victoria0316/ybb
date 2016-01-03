package com.bluemobi.ybb.ps.network.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.bluemobi.ybb.ps.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.ps.network.response.FeedbackResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/11.
 */
public class FeedbackRequest extends YbbHttpJsonRequest<FeedbackResponse> {

    private static final String APIPATH = "mobi/feedback/save";

    private String userId; //用户ID
    private String content; //反馈内容

    public FeedbackRequest(Response.Listener<FeedbackResponse> listener,
                           Response.ErrorListener errorListener)
    {
        super(Request.Method.POST, APIPATH, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userId);
        map.put("content", content);
        return map;
    }

    @Override
    public Class<FeedbackResponse> getResponseClass() {
        return FeedbackResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
