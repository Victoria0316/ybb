package com.bluemobi.ybb.network.response;

import com.bluemobi.ybb.network.YbbHttpResponse;
import com.bluemobi.ybb.network.model.AddressListModel;
import com.bluemobi.ybb.network.model.CommodityListModel;
import com.bluemobi.ybb.network.model.CommodityModel;

import java.util.List;

/**
 * Created by wangzhijun on 2015/8/9.
 */
public class CommodtiesResponse extends YbbHttpResponse {

    private CommodityListModel data;

    public CommodityListModel getData() {
        return data;
    }

    public void setData(CommodityListModel data) {
        this.data = data;
    }
}
