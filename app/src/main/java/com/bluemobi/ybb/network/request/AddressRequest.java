package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.YbbHttpResponse;
import com.bluemobi.ybb.network.response.AddressResponse;
import com.bluemobi.ybb.network.util.ParameterUtil;
import com.bluemobi.ybb.network.util.SmsType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhijun on 2015/8/9.
 */
public class AddressRequest extends YbbHttpJsonRequest<AddressResponse> {

    private static final String APIPATH = "mobi/adminuser/getAddress";
    /**
     * 查询类型，1医院、2科室、3床位
     */
    private String category;
    /**
     * 医院ID，获取科室及床位需要
     */
    private String hospitalId;
    /**
     * 部门ID，获取床位需要
     */
    private String departmentId;

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




    public AddressRequest(Response.Listener<AddressResponse> listener,
                          Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    public AddressRequest(int method, String partUrl,
                          Response.Listener<AddressResponse> listener, Response.ErrorListener errorListener) {
        super(method, partUrl, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("category", String.valueOf(category));
        map.put("hospitalId", String.valueOf(hospitalId));
        map.put("departmentId", String.valueOf(departmentId));
        map.put("currentnum", String.valueOf(currentnum));
        map.put("currentpage", String.valueOf(currentpage));
        map.put("pageTime", pageTime);
        return map;
    }

    @Override
    public Class<AddressResponse> getResponseClass() {
        return AddressResponse.class;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
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
}
