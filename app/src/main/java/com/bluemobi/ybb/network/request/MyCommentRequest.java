package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.MyCommentResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/17.
 */
public class MyCommentRequest extends YbbHttpJsonRequest<MyCommentResponse>{

    private static final String APIPATH = "mobi/commentinfo/save";

    private String userId ;//评价人id
    private String userName ;//	评价人姓名
    private String sourceType ;//评价类型(1餐品 2商品，3物流/配餐员，4客服人员)
    private String starScore ;//星级评分
    private String content;//评价内容

    private String userIp ;//否 	评价人所在ip
    private String productId ;//否 	评价商品id
    private String productName ;//否 	评价商品名称
    private String logistics_id ;//否 	被评价物流公司/配餐员ID
    private String customer_service_id ;//否 	被评价客服人员ID
    private String order_id ;//	否 	评价订单号

    public MyCommentRequest(Response.Listener<MyCommentResponse> listener, Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userId);
        map.put("userName", userName);
        map.put("sourceType", sourceType);
        map.put("starScore", starScore);
        map.put("content", content);
        map.put("userIp", userIp == null? "":userIp);
        map.put("productId", productId == null? "":productId);
        map.put("productName", productName == null? "":productName);
        map.put("logistics_id", logistics_id == null? "":logistics_id);
        map.put("customer_service_id", customer_service_id == null? "":customer_service_id);
        map.put("order_id", order_id == null? "":order_id);
        return map;
    }

    @Override
    public Class<MyCommentResponse> getResponseClass() {
        return MyCommentResponse.class;
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

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
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

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getLogistics_id() {
        return logistics_id;
    }

    public void setLogistics_id(String logistics_id) {
        this.logistics_id = logistics_id;
    }

    public String getCustomer_service_id() {
        return customer_service_id;
    }

    public void setCustomer_service_id(String customer_service_id) {
        this.customer_service_id = customer_service_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
