package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.AccountBillResponse;
import com.bluemobi.ybb.network.response.AlipayTransferOutResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyn on 2015/8/13.
 */
public class AlipayTransferOutRequest extends YbbHttpJsonRequest<AlipayTransferOutResponse>{

    private static final String APIPATH = "mobi/personalcenter/extraction";

    private String userId;//	string	是	用户ID
    private String  rechargeChannel;//	string	是	充值渠道（1现金，2银行卡转账，3支付宝，4微信，5银联）
    private String rechargeAmount;//	float	是	金额
    private String rechargeType;//	int	是	充值类型（1注册充值，2充值，3补欠，4转出）
    private String  alipayId;//	string	是	转出至支付宝账号
    private String alipayName;//	string	是	转出至支付宝姓名

    public AlipayTransferOutRequest(Response.Listener<AlipayTransferOutResponse> listener,
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
        map.put("userId", userId);
        map.put("rechargeChannel", rechargeChannel);
        map.put("rechargeAmount",rechargeAmount);
        map.put("rechargeType",rechargeType);
        map.put("alipayId", alipayId);
        map.put("alipayName", alipayName);

        return map;
    }

    @Override
    public Class<AlipayTransferOutResponse> getResponseClass() {
        return AlipayTransferOutResponse.class;
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

    public String getAlipayId() {
        return alipayId;
    }

    public void setAlipayId(String alipayId) {
        this.alipayId = alipayId;
    }

    public String getAlipayName() {
        return alipayName;
    }

    public void setAlipayName(String alipayName) {
        this.alipayName = alipayName;
    }
}
