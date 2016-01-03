package com.bluemobi.ybb.network.response;

import com.bluemobi.ybb.network.YbbHttpResponse;
import com.bluemobi.ybb.network.model.AddressListModel;

/**
 * Created by wangzhijun on 2015/8/9.
 */
public class FoodListResponse extends YbbHttpResponse{
    private AddressListModel data;

    public AddressListModel getData() {
        return data;
    }

    public void setData(AddressListModel data) {
        this.data = data;
    }
}
