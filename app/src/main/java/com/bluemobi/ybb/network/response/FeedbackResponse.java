package com.bluemobi.ybb.network.response;

import com.bluemobi.ybb.network.YbbHttpResponse;

/**
 * Created by gaoyn on 2015/8/11.
 */
public class FeedbackResponse extends YbbHttpResponse {

    private String userId; //用户ID
    private String content; //反馈内容

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }
}
