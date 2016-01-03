package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.TrunInorOutResponse;
import com.bluemobi.ybb.network.response.TrunInorOutResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * p14-14转入转出  
 */
public class TrunInorOutRequest extends YbbHttpJsonRequest<TrunInorOutResponse>{

    private static final String APIPATH = "mobi/personalcenter/recharge";

    private String userId;//用户ID
    private String rechargeChannel;//充值渠道（1现金，2银行卡转账，3支付宝，4微信，5银联）
    private String rechargeAmount;//充值金额
    private String rechargeType;//充值类型（1注册充值，2充值，3补欠，4转出）

    public TrunInorOutRequest(Response.Listener<TrunInorOutResponse> listener,
                              Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("rechargeChannel", rechargeChannel);
        map.put("rechargeAmount", rechargeAmount);
        map.put("userId",userId);
        map.put("rechargeType",rechargeType);

        return map;
    }

    @Override
    public Class<TrunInorOutResponse> getResponseClass() {
        return TrunInorOutResponse.class;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRechargeChannel() {
        return rechargeChannel;
    }

    public void setRechargeChannel(String rechargeChannel) {
        this.rechargeChannel = rechargeChannel;
    }

    public String getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(String rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public String getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(String rechargeType) {
        this.rechargeType = rechargeType;
    }
}
