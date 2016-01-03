package com.bluemobi.ybb.network.response;

import com.bluemobi.ybb.network.YbbHttpResponse;
import com.bluemobi.ybb.network.model.MyAccountBean;

/**
 * Created by gaoyn on 2015/8/12.
 */
public class MyAccountResponse extends YbbHttpResponse{

    private MyAccountBean data;

    public MyAccountBean getData() {
        return data;
    }

    public void setData(MyAccountBean data) {
        this.data = data;
    }
}
