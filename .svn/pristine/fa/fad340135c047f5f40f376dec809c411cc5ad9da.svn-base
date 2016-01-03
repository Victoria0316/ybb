package com.bluemobi.ybb.ps.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.ps.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.ps.network.response.RefundMealReasonResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/16.
 */
public class RefundMealReasonRequest extends YbbHttpJsonRequest<RefundMealReasonResponse>{

    private static final String APIPATH = "mobi/logisticsdistributioninfo/backOrderLogistics";

    private String id;// 配送单id
    private String backReason;// 退餐原因
//    private String imgList;//上传图片列表，base64数组
    private String mobiType;
    private ArrayList<String> imgList;//上传图片列表，base64数组


    public RefundMealReasonRequest(Response.Listener<RefundMealReasonResponse> listener, Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        map.put("backReason", backReason);
//        map.put("imgList", imgList);
        map.put("mobiType", "1");
        return map;
    }

    @Override
    protected Map<String, Object> getExtParams() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("imgList", imgList);
        return map;
    }

    @Override
    public Class<RefundMealReasonResponse> getResponseClass() {
        return RefundMealReasonResponse.class;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBackReason() {
        return backReason;
    }

    public void setBackReason(String backReason) {
        this.backReason = backReason;
    }

//    public String getImgList() {
//        return imgList;
//    }
//
//    public void setImgList(String imgList) {
//        this.imgList = imgList;
//    }

    public ArrayList<String> getImgList() {
        return imgList;
    }

    public void setImgList(ArrayList<String> imgList) {
        this.imgList = imgList;
    }
}
