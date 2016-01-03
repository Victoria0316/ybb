package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.RegisteredResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/9.
 */
public class RegisteredRequest extends YbbHttpJsonRequest<RegisteredResponse>{

    private static final String APIPATH = "mobi/adminuser/register";

    private String userName; //手机号
    private String password ;	//	密码
    private String adminTypeId ;	//用户类型ID
    private String adminTypeName; //用户类型名称
    private String nickName; //	昵称

    public RegisteredRequest(Response.Listener<RegisteredResponse> listener,
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
        map.put("userName", userName);
        map.put("password", password);
        map.put("adminTypeId", adminTypeId);
        map.put("adminTypeName", adminTypeName);
        map.put("nickName", nickName);
        map.put("pass", "n");
        return map;
    }

    @Override
    public Class<RegisteredResponse> getResponseClass() {
        return RegisteredResponse.class;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdminTypeId() {
        return adminTypeId;
    }

    public void setAdminTypeId(String adminTypeId) {
        this.adminTypeId = adminTypeId;
    }

    public String getAdminTypeName() {
        return adminTypeName;
    }

    public void setAdminTypeName(String adminTypeName) {
        this.adminTypeName = adminTypeName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
