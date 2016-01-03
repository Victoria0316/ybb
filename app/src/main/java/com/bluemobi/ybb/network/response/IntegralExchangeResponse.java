package com.bluemobi.ybb.network.response;

import com.bluemobi.ybb.network.YbbHttpResponse;
import com.bluemobi.ybb.network.model.IntegralExchangeBean;

/**
 * Created by gaoyn on 2015/8/12.
 */
public class IntegralExchangeResponse extends YbbHttpResponse {

    private IntegralExchangeBean data;

    public IntegralExchangeBean getData() {
        return data;
    }

    public void setData(IntegralExchangeBean data) {
        this.data = data;
    }
}
