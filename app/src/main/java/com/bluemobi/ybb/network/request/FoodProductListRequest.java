package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.AccountBillResponse;
import com.bluemobi.ybb.network.response.CommodtiesResponse;
import com.bluemobi.ybb.network.response.FoodProductListResponse;
import com.bluemobi.ybb.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhijun on 2015/8/13.
 */
public class FoodProductListRequest extends YbbHttpJsonRequest<FoodProductListResponse>{

    private static final String APIPATH = "mobi/productinfo/findByPage";
    private String keyword;//查询关键字
    private String productId;//否	主食id
    private String queryTime = "";//否	查询时间（yyyy-mm-dd）
    private String attributeId;//否	餐次id
    private String canteenId;//是	食堂id
    private String categoryId;//是	套餐类型（1营养餐2零点餐3医护套餐4病患套餐）
    private String loginuserid;//是	当前登录用户id
    private String categoryIdList;//套餐类型列表（以','分隔，例如(1,2,3)）
    /**
     * 每页记录数
     */
    private String currentnum;
    /**
     * 当前页数
     */
    private String currentpage;
    /**
     * 分页时间
     */
    private String pageTime;

    public FoodProductListRequest(Response.Listener<FoodProductListResponse> listener,
                              Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    public FoodProductListRequest(int method, String partUrl,
                              Response.Listener<FoodProductListResponse> listener, Response.ErrorListener errorListener) {
        super(method, partUrl, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("keyword", keyword == null? "":keyword);
        map.put("productId", productId == null? "":productId);
        map.put("attributeId", StringUtils.isEmpty(attributeId)?null:attributeId);
        map.put("canteenId", canteenId == null? "":canteenId);
        map.put("loginuserid", loginuserid == null? "":loginuserid);
        map.put("categoryId", categoryId == null? null:categoryId);
        map.put("currentpage", currentpage == null? "":currentpage);
        map.put("pageTime", pageTime == null? "":pageTime);
        map.put("currentnum", currentnum == null ? "" : currentnum);
        map.put("categoryIdList", categoryIdList == null ? "" : categoryIdList);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        if(StringUtils.isNotEmpty(queryTime)){
            map.put("queryTime", queryTime);
        }else {
            map.put("queryTime",df.format(new Date()));
        }

        return map;
    }

    @Override
    public Class<FoodProductListResponse> getResponseClass() {
        return FoodProductListResponse.class;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(String queryTime) {
        this.queryTime = queryTime;
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getLoginuserid() {
        return loginuserid;
    }

    public void setLoginuserid(String loginuserid) {
        this.loginuserid = loginuserid;
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

    public String getPageTime() {
        return pageTime;
    }

    public void setPageTime(String pageTime) {
        this.pageTime = pageTime;
    }

    public String getCategoryIdList() {
        return categoryIdList;
    }

    public void setCategoryIdList(String categoryIdList) {
        this.categoryIdList = categoryIdList;
    }
}
