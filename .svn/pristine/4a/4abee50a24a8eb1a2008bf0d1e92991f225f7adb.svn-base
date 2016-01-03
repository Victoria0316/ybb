package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.MyEvaluationResponse;
import com.bluemobi.ybb.network.response.MyInfoResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liufy
 * 添加评论--P11-3评论
 */
public class MyEvaluationRequest extends YbbHttpJsonRequest<MyEvaluationResponse>{

    private static final String APIPATH = "mobi/articlecommentinfo/save";

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getCommentInfo() {
        return commentInfo;
    }

    public void setCommentInfo(String commentInfo) {
        this.commentInfo = commentInfo;
    }

    public String getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(String commentUserId) {
        this.commentUserId = commentUserId;
    }

    public String getCommentNickName() {
        return commentNickName;
    }

    public void setCommentNickName(String commentNickName) {
        this.commentNickName = commentNickName;
    }

    private String articleId;//			文章ID
    private String commentInfo;//		评论内容
    private String commentUserId;//			评论人ID
    private String commentNickName;//		评论人昵称

    public MyEvaluationRequest(Response.Listener<MyEvaluationResponse> listener,
                               Response.ErrorListener errorListener)
    {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    public MyEvaluationRequest(int method, String partUrl,
                               Response.Listener<MyEvaluationResponse> listener, Response.ErrorListener errorListener)
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
        map.put("articleId", articleId);
        map.put("commentInfo", commentInfo);
        map.put("commentUserId", commentUserId);
        map.put("commentNickName", commentNickName);

        return map;
    }

    @Override
    public Class<MyEvaluationResponse> getResponseClass() {
        return MyEvaluationResponse.class;
    }


}
