package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.UploadMyPicResponse;
import com.bluemobi.ybb.network.response.UploadMyPicResponse;

import java.util.HashMap;
import java.util.Map;

/**
 *gxy
 */
public class UploadMyPicRequest extends YbbHttpJsonRequest<UploadMyPicResponse> {

    private static final String APIPATH = "mobi/personalcenter/updateHeadPic";
    private String userId;//用户ID
    private String picArray;//base64图片字节流
    private String picType;//图片类型


    public UploadMyPicRequest(Response.Listener<UploadMyPicResponse> listener,
                              Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    public UploadMyPicRequest(int method, String partUrl,
                              Response.Listener<UploadMyPicResponse> listener, Response.ErrorListener errorListener) {
        super(method, partUrl, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", userId);
        map.put("picArray", picArray);
        map.put("picType", picType);
        return map;
    }

    @Override
    public Class<UploadMyPicResponse> getResponseClass() {
        return UploadMyPicResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPicArray() {
        return picArray;
    }

    public void setPicArray(String picArray) {
        this.picArray = picArray;
    }

    public String getPicType() {
        return picType;
    }

    public void setPicType(String picType) {
        this.picType = picType;
    }
}
