package com.bluemobi.ybb.network.response;

import com.bluemobi.ybb.network.YbbHttpResponse;
import com.bluemobi.ybb.network.model.FoodProductDetailModel;
import com.bluemobi.ybb.network.model.NutritiousFoodDetails;

/**

 */
public class FoodProductDetailResponse extends YbbHttpResponse{
    private FoodProductDetailModel data;

    public FoodProductDetailModel getData() {
        return data;
    }

    public void setData(FoodProductDetailModel data) {
        this.data = data;
    }
}
