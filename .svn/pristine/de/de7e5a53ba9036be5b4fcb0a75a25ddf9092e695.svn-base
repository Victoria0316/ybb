package com.bluemobi.ybb.ps.network.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.bluemobi.ybb.ps.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.ps.network.response.LoginResponse;


import java.util.HashMap;
import java.util.Map;

/**
 *gxy
 *
 */
public class LoginRequest extends YbbHttpJsonRequest<LoginResponse> {

    private static final String APIPATH = "mobi/adminuser/expressLogin";
    private String username;
    private String password;

    public LoginRequest(Response.Listener<LoginResponse> listener,
                        Response.ErrorListener errorListener)
    {
        super(Request.Method.POST, APIPATH, listener, errorListener);
    }

    public LoginRequest(int method, String partUrl,
                        Response.Listener<LoginResponse> listener, Response.ErrorListener errorListener)
    {
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
        map.put("pass", "n");
        return map;
    }

    @Override
    public Class<LoginResponse> getResponseClass() {
        return LoginResponse.class;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
