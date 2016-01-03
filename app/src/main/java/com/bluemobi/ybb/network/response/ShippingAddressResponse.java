package com.bluemobi.ybb.network.response;

import com.bluemobi.ybb.network.YbbHttpResponse;
import com.bluemobi.ybb.network.model.ShippingAddressBean;


/**
 * Created by gaoyn on 2015/8/11.
 */
public class ShippingAddressResponse extends YbbHttpResponse{

    private ShippingAddressBean data;

    public ShippingAddressBean getData() {
        return data;
    }

    public void setData(ShippingAddressBean data) {
        this.data = data;
    }
}
