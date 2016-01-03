package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.ModificationAddressResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/10.
 */
public class ModificationAddressRequest extends YbbHttpJsonRequest<ModificationAddressResponse>{

    private static final String APIPATH = "mobi/adminuser/updateRegisterInfo";

    private String userId; //用户ID
    private String username ;	//	姓名
    private String hospitalId ;	//医院ID
    private String hospitalName; //医院名称
    private String departmentId; //	科室ID
    private String departmentName; //科室名称
    private String bedId; //	床位ID
    private String bedName; //床位名称
    private String address; //	地址
    private String cellPhone; //电话

    public ModificationAddressRequest(Response.Listener<ModificationAddressResponse> listener,
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
        map.put("userId", userId);
        map.put("username", username);
        map.put("hospitalId", hospitalId);
        map.put("hospitalName", hospitalName);
        map.put("departmentId", departmentId);
        map.put("departmentName",departmentName);
        map.put("bedId", bedId);
        map.put("bedName", bedName);
        map.put("address",address);
        map.put("cellphone",cellPhone);
        return map;
    }

    @Override
    public Class<ModificationAddressResponse> getResponseClass() {
        return ModificationAddressResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getBedId() {
        return bedId;
    }

    public void setBedId(String bedId) {
        this.bedId = bedId;
    }

    public String getBedName() {
        return bedName;
    }

    public void setBedName(String bedName) {
        this.bedName = bedName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }
}
