package com.bluemobi.ybb.ps.network.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.bluemobi.ybb.ps.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.ps.network.response.PeiSongDanResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by gaoyn on 2015/8/13.
 */
public class PeiSongDanRequest extends YbbHttpJsonRequest<PeiSongDanResponse>{

    private static final String APIPATH = "mobi/logisticsdistributioninfo/findByPage";

    private String currentnum;//每页条数
    private String currentpage;//当前页数
    private String attributeId;//餐次id
    private int categoryId;//套餐类型（1营养餐2零点餐3医护套餐4医患套餐）
    private String canteenId;//食堂id
    private String adminTypeId;//用户类型id
    private String deliverymanId;//送货员id
    private int isAgent;//是否是代点餐（1是代点餐,2是非代点餐）
    private int distributionType;//配送状态（0未接单，1接单，2送货中，3已送到，4已退餐）
    private String  queryTime;
    private String distributionTypeList;

    public PeiSongDanRequest(Response.Listener<PeiSongDanResponse> listener,
                             Response.ErrorListener errorListener) {
        super(Request.Method.POST, APIPATH, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {

        Map<String, String> map = new HashMap<String, String>();
//        map.put("currentnum", currentnum == null? "":currentnum);
//        map.put("currentpage", currentpage == null? "":currentpage);
//        map.put("attributeId", attributeId == null? "":attributeId);
//        map.put("categoryId", categoryId == null? "":categoryId);
//        map.put("canteenId", canteenId == null? "":canteenId);
//        map.put("adminTypeId", adminTypeId == null? "":adminTypeId);
//        map.put("deliverymanId", deliverymanId == null? null:deliverymanId);
//        map.put("isAgent", isAgent == null? null:isAgent);
//        map.put("distributionType", distributionType == null? "":distributionType);
//        map.put("queryTime",  queryTime == null? "":queryTime);



        return map;
    }

    @Override
    protected Map<String, Object> getExtParams() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("currentnum", currentnum == null? "":currentnum);
        map.put("currentpage", currentpage == null? "":currentpage);
        map.put("attributeId", attributeId == null? "":attributeId);
        map.put("categoryId", categoryId == 0? 0:new Integer(categoryId));
        map.put("canteenId", canteenId == null? "":canteenId);
        map.put("adminTypeId", adminTypeId == null? "":adminTypeId);
        map.put("deliverymanId", deliverymanId == null? null:deliverymanId);
        map.put("isAgent", isAgent == 0? 0:new Integer(isAgent));
        if (distributionType!=-1){
            map.put("distributionType", distributionType == 0? 0:new Integer(distributionType));
        }
        map.put("queryTime",  queryTime == null? "":queryTime);
        map.put("distributionTypeList",  distributionTypeList == null? "":distributionTypeList);



        return map;
    }

    @Override
    public Class<PeiSongDanResponse> getResponseClass() {
        return PeiSongDanResponse.class;
    }

    public String getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(String queryTime) {
        this.queryTime = queryTime;
    }

    public String getCurrentnum() {
        return currentnum;
    }

    public void setCurrentnum(String currentnum) {
        this.currentnum = currentnum;
    }

    public String getCurrentpage() {
        return currentpage;
    }

    public void setCurrentpage(String currentpage) {
        this.currentpage = currentpage;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }



    public String getCanteenId() {
        return canteenId;
    }

    public void setCanteenId(String canteenId) {
        this.canteenId = canteenId;
    }

    public String getAdminTypeId() {
        return adminTypeId;
    }

    public void setAdminTypeId(String adminTypeId) {
        this.adminTypeId = adminTypeId;
    }

    public String getDeliverymanId() {
        return deliverymanId;
    }

    public void setDeliverymanId(String deliverymanId) {
        this.deliverymanId = deliverymanId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getIsAgent() {
        return isAgent;
    }

    public void setIsAgent(int isAgent) {
        this.isAgent = isAgent;
    }

    public int getDistributionType() {
        return distributionType;
    }

    public void setDistributionType(int distributionType) {
        this.distributionType = distributionType;
    }

    public String getDistributionTypeList() {
        return distributionTypeList;
    }

    public void setDistributionTypeList(String distributionTypeList) {
        this.distributionTypeList = distributionTypeList;
    }
}
