package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.BasicInformationNicknameResponse;
import com.bluemobi.ybb.network.response.RegisteredResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/9.
 */
public class BasicInformationNicknameRequest extends YbbHttpJsonRequest<BasicInformationNicknameResponse>{

    private static final String APIPATH = "mobi/personalcenter/updateNickName";

    private String userId; //用户ID
    private String nickName; //昵称

    public BasicInformationNicknameRequest(Response.Listener<BasicInformationNicknameResponse> listener,
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
        map.put("nickName", nickName);
        map.put("pass", "n");
        return map;
    }

    @Override
    public Class<BasicInformationNicknameResponse> getResponseClass() {
        return BasicInformationNicknameResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
