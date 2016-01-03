package com.bluemobi.ybb.app;

import android.os.AsyncTask;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.network.model.ParamModel;
import com.bluemobi.ybb.network.request.ParamRequest;
import com.bluemobi.ybb.network.response.ParamResponse;
import com.bluemobi.ybb.util.PreferencesService;
import com.bluemobi.ybb.util.StringUtils;
import com.bluemobi.ybb.util.WebUtils;

import java.util.HashMap;
import java.util.List;

/**
 * 获取公共参数
 * Created by wangzhijun on 2015/8/11.
 */
public class BackGroundTask extends AsyncTask<Void, Void, Void>{

    @Override
    protected Void doInBackground(Void... params) {
        ParamRequest request = new ParamRequest(new Response.Listener<ParamResponse>() {
            @Override
            public void onResponse(ParamResponse response) {
                if(response != null && response.getStatus() ==0){
                    YbbApplication.getInstance().setParamModel(response.getData());
                    Logger.e("wangzhijun", "param success");
                    String temp = response.getData().getCanteen_id();
                    YbbApplication.getInstance().setCanteenId(temp);
                    String tempCount = response.getData().getShopcart_shops_count();
                    if(tempCount ==null || "".equals(tempCount)){
                        tempCount = "0";
                    }
                    YbbApplication.getInstance().setCartCounts(Integer.parseInt(tempCount));
                    YbbApplication.getInstance().setCartAmount(
                            StringUtils.isEmpty(response.getData().getShopcart_price())?"0":
                                    response.getData().getShopcart_price());
                    prepareData(response.getData().getProductAttributeInfoList());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
        WebUtils.doPost(request);
        return null;
    }

    private void prepareData(List<ParamModel.ProductAttributeEntity> productAttributeInfoList) {
        if(productAttributeInfoList != null){
            HashMap<String, ParamModel.ProductAttributeEntity> hashMapo = YbbApplication.getInstance().getAttributeEntityHashMap();
            hashMapo.clear();
            for(ParamModel.ProductAttributeEntity item : productAttributeInfoList){
                hashMapo.put(item.getId(), item);
            }
        }
    }
}
