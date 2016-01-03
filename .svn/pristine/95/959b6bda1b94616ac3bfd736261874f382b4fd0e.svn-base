package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.CollectionResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/11.
 */
public class CollectionRequest extends YbbHttpJsonRequest<CollectionResponse>{

    private static final String APIPATH = "mobi/collectioninfo/save";

    private String collectionId;//收藏信息主键id
    private String collectionType;//收藏信息类型 1:店铺，2:商品，3：餐品，4：资讯
    private String userId; //用户id

    public CollectionRequest(Response.Listener<CollectionResponse> listener,
                                  Response.ErrorListener errorListener)
    {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("collectionId", collectionId);
        map.put("collectionType", collectionType);
        map.put("userId", userId);
        return map;
    }

    @Override
    public Class<CollectionResponse> getResponseClass() {
        return CollectionResponse.class;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
