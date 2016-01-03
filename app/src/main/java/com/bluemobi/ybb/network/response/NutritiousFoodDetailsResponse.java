package com.bluemobi.ybb.network.response;

import com.bluemobi.ybb.network.YbbHttpResponse;
import com.bluemobi.ybb.network.model.NutritiousFoodDetails;
import com.bluemobi.ybb.network.model.UserInfo;

/**

 */
public class NutritiousFoodDetailsResponse extends YbbHttpResponse{
    private NutritiousFoodDetails data;

    public NutritiousFoodDetails getData() {
        return data;
    }

    public void setData(NutritiousFoodDetails data) {
        this.data = data;
    }
}
