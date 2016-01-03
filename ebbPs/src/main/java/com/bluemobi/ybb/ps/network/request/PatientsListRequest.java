package com.bluemobi.ybb.ps.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.ps.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.ps.network.response.ConfirmSendedResponse;
import com.bluemobi.ybb.ps.network.response.PatientsListResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * 患者列表
 */
public class PatientsListRequest extends YbbHttpJsonRequest<PatientsListResponse> {

    private static final String APIPATH = "mobi/adminuser/getCustomerList";
    private String adminTypeId;//	string	是	要查询的用户类型id（例如：医患id，8aba20b64edda41a014edda5ee0d0003）
    private String loginuserid;//	string	是	当前登录用户id

    public PatientsListRequest(Response.Listener<PatientsListResponse> listener,
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
        map.put("adminTypeId", adminTypeId);
        map.put("loginuserid", loginuserid);
        return map;
    }

    @Override
    public Class<PatientsListResponse> getResponseClass() {
        return PatientsListResponse.class;
    }

    public String getAdminTypeId() {
        return adminTypeId;
    }

    public void setAdminTypeId(String adminTypeId) {
        this.adminTypeId = adminTypeId;
    }

    public String getLoginuserid() {
        return loginuserid;
    }

    public void setLoginuserid(String loginuserid) {
        this.loginuserid = loginuserid;
    }
}
