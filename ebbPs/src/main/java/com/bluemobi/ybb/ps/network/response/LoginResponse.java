package com.bluemobi.ybb.ps.network.response;


import com.bluemobi.ybb.ps.network.YbbHttpResponse;
import com.bluemobi.ybb.ps.network.model.UserInfo;

/**

 */
public class LoginResponse extends YbbHttpResponse {
    private UserInfo data;

    public UserInfo getData() {
        return data;
    }

    public void setData(UserInfo data) {
        this.data = data;
    }

}
