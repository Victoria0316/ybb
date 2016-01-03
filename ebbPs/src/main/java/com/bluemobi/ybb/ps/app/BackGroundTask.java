package com.bluemobi.ybb.ps.app;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.base.utils.WebUtils;
import com.bluemobi.ybb.ps.network.request.ParamRequest;
import com.bluemobi.ybb.ps.network.response.ParamResponse;
import com.bluemobi.ybb.ps.util.Constants;


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
                    YbbPsApplication.getInstance().setParamModel(response.getData());
                    Logger.e("wangzhijun", "param success");
                    String temp = response.getData().getCanteen_id();
                    YbbPsApplication.getInstance().setCanteenId(temp);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request.setUserId(YbbPsApplication.getInstance().getMyUserInfo().getUserId());
        WebUtils.doPost(request);
        return null;
    }
}
