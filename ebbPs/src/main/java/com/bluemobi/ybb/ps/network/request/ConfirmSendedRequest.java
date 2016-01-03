package com.bluemobi.ybb.ps.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.ps.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.ps.network.response.ConfirmSendedResponse;
import com.bluemobi.ybb.ps.network.response.ConfirmSendedResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * p23  确定配送
 */
public class ConfirmSendedRequest extends YbbHttpJsonRequest<ConfirmSendedResponse> {

    private static final String APIPATH = "mobi/logisticsdistributioninfo/receiveOrderLogistics";

    private String id; //配送单id
    private String  mobiType; //客户端类型（1：Android，2：IOS）

    public ConfirmSendedRequest(Response.Listener<ConfirmSendedResponse> listener,
                                Response.ErrorListener errorListener)
    {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        map.put("mobiType", "1");
        return map;
    }

    @Override
    public Class<ConfirmSendedResponse> getResponseClass() {
        return ConfirmSendedResponse.class;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobiType() {
        return mobiType;
    }

    public void setMobiType(String mobiType) {
        this.mobiType = mobiType;
    }
}
