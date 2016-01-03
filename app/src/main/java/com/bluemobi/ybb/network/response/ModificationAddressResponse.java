package com.bluemobi.ybb.network.response;

import com.bluemobi.ybb.network.YbbHttpResponse;
import com.bluemobi.ybb.network.model.ModificationAddressBean;

/**
 * Created by gaoyn on 2015/8/10.
 */
public class ModificationAddressResponse extends YbbHttpResponse{

    private ModificationAddressBean data;

    public ModificationAddressBean getData() {
        return data;
    }

    public void setData(ModificationAddressBean data) {
        this.data = data;
    }
}
