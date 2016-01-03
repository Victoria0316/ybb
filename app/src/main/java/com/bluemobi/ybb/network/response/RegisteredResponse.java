package com.bluemobi.ybb.network.response;

import com.bluemobi.ybb.network.YbbHttpResponse;
import com.bluemobi.ybb.network.model.RegisteredBean;

/**
 * Created by gaoyn on 2015/8/9.
 */
public class RegisteredResponse extends YbbHttpResponse {

    private RegisteredBean data;

    public RegisteredBean getData() {
        return data;
    }

    public void setData(RegisteredBean data) {
        this.data = data;
    }


}
