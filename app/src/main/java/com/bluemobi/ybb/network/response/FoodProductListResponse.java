package com.bluemobi.ybb.network.response;

import com.bluemobi.ybb.network.YbbHttpResponse;
import com.bluemobi.ybb.network.model.AccountBillBean;
import com.bluemobi.ybb.network.model.CommodityListModel;
import com.bluemobi.ybb.network.model.FoodProductListModel;

/**
 * Created by gaoyn on 2015/8/13.
 */
public class FoodProductListResponse extends YbbHttpResponse{

    private FoodProductListModel data;

    public FoodProductListModel getData() {
        return data;
    }

    public void setData(FoodProductListModel data) {
        this.data = data;
    }
}
