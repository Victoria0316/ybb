package com.bluemobi.ybb.network.response;

import com.bluemobi.ybb.network.YbbHttpResponse;
import com.bluemobi.ybb.network.model.AddressListModel;
import com.bluemobi.ybb.network.model.OrderCommitModel;

/**
 * Created by wangzhijun on 2015/8/9.
 */
public class OrderCommitResponse extends YbbHttpResponse {
    private OrderCommitModel data;

    public OrderCommitModel getData() {
        return data;
    }

    public void setData(OrderCommitModel data) {
        this.data = data;
    }
}
