package com.bluemobi.ybb.network.response;

import com.bluemobi.ybb.db.entity.OrderInfoModel;
import com.bluemobi.ybb.network.YbbHttpResponse;
import com.bluemobi.ybb.network.model.Orderinfo;
import com.bluemobi.ybb.network.model.OrderinfoResponseModel;
import com.bluemobi.ybb.network.model.UserInfo;

/**

 */
public class OrderinfoResponse extends YbbHttpResponse {
    private OrderinfoResponseModel data;

    public OrderinfoResponseModel getData() {
        return data;
    }

    public void setData(OrderinfoResponseModel data) {
        this.data = data;
    }
}
