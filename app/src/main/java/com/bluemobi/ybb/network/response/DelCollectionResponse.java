package com.bluemobi.ybb.network.response;

import com.bluemobi.ybb.network.YbbHttpResponse;

/**
 * Created by gaoyn on 2015/8/11.
 */
public class DelCollectionResponse extends YbbHttpResponse{
    private String collectionId;//收藏信息主键id
    private String collectionType;//收藏信息类型 1:店铺，2:商品，3：餐品，4：资讯
    private String userid; //用户id

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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

}
