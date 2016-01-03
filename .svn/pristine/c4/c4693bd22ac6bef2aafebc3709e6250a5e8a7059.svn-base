package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.model.CommentBatchChild;
import com.bluemobi.ybb.network.model.CommentBatchRequestModel;
import com.bluemobi.ybb.network.response.AddressResponse;
import com.bluemobi.ybb.network.response.CommentBatchResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhijun on 2015/8/9.
 */
public class CommentBatchRequest extends YbbHttpJsonRequest<CommentBatchResponse> {

    private static final String APIPATH = "mobi/commentinfo/addCommentList";

    private CommentBatchRequestModel model;
    private ArrayList<CommentBatchChild> commentInfoList;
    private String userId;//private String 评价用户id",
    private String userName;//private String 评价用户名称",
    private String starScore;//private String 评价星级",
    private String content;//private String 评价内容",
    private String orderId;//private String 订单id",
    private String userIp;//private String 评价用户ip",
    private String logisticsId;//private String 被评价物流公司/配餐员ID",
    private String customerServiceId;//private String 被评价客服人员ID",

    public CommentBatchRequest(Response.Listener<CommentBatchResponse> listener,
                               Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    public CommentBatchRequest(int method, String partUrl,
                               Response.Listener<CommentBatchResponse> listener, Response.ErrorListener errorListener) {
        super(method, partUrl, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", String.valueOf(userId));
        map.put("userName", String.valueOf(userName));
        map.put("starScore", String.valueOf(starScore));
        map.put("content", String.valueOf(content));
        map.put("orderId", String.valueOf(orderId));
        map.put("userIp", String.valueOf(userIp));
        map.put("logisticsId", String.valueOf(logisticsId));
        return map;
    }

    @Override
    protected Map<String, Object> getExtParams() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("commentInfoList", commentInfoList);
        return map;
    }

    @Override
    public Class<CommentBatchResponse> getResponseClass() {
        return CommentBatchResponse.class;
    }

    public CommentBatchRequestModel getModel() {
        return model;
    }

    public void setModel(CommentBatchRequestModel model) {
        this.model = model;
    }

    public ArrayList<CommentBatchChild> getCommentInfoList() {
        return commentInfoList;
    }

    public void setCommentInfoList(ArrayList<CommentBatchChild> commentInfoList) {
        this.commentInfoList = commentInfoList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStarScore() {
        return starScore;
    }

    public void setStarScore(String starScore) {
        this.starScore = starScore;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    public String getCustomerServiceId() {
        return customerServiceId;
    }

    public void setCustomerServiceId(String customerServiceId) {
        this.customerServiceId = customerServiceId;
    }
}
