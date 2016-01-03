package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.UpdatePasswordResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * gaozq
 */
public class UpdatePasswordRequest extends YbbHttpJsonRequest<UpdatePasswordResponse> {

    private static final String APIPATH = "mobi/adminuser/updatePassword";

//    username 	string 	是 	用户名（手机号）
//    password 	string 	是 	密码
    private String username;
    private String password;

    public UpdatePasswordRequest(Response.Listener<UpdatePasswordResponse> listener,
                                 Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    public UpdatePasswordRequest(int method, String partUrl,
                                 Response.Listener<UpdatePasswordResponse> listener, Response.ErrorListener errorListener) {
        super(method, partUrl, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("username", username);
        map.put("password", password);
        return map;
    }

    @Override
    public Class<UpdatePasswordResponse> getResponseClass() {
        return UpdatePasswordResponse.class;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
