package com.bluemobi.ybb.ps.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.ps.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.ps.network.response.SmsCodeValidateResponse;
import com.bluemobi.ybb.ps.network.util.ParameterUtil;
import com.bluemobi.ybb.ps.network.util.SmsType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhijun on 2015/8/9.
 */
public class SmsCodeValidateRequest extends YbbHttpJsonRequest<SmsCodeValidateResponse> {

    private static final String APIPATH = "mobi/cascode/checkIdentifyingCode";

    private SmsType type;
    private String cellphone;
    private String validationCode;

    public SmsCodeValidateRequest(Response.Listener<SmsCodeValidateResponse> listener,
                                  Response.ErrorListener errorListener) {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    public SmsCodeValidateRequest(int method, String partUrl,
                                  Response.Listener<SmsCodeValidateResponse> listener, Response.ErrorListener errorListener) {
        super(method, partUrl, listener, errorListener);
    }

    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("type", ParameterUtil.getSmsType(type));
        map.put("cellphone", cellphone);
        map.put("pass", "n");
        map.put("validationCode", validationCode);
        return map;
    }

    @Override
    public Class<SmsCodeValidateResponse> getResponseClass() {
        return SmsCodeValidateResponse.class;
    }

    public SmsType getType() {
        return type;
    }

    public void setType(SmsType type) {
        this.type = type;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(String validationCode) {
        this.validationCode = validationCode;
    }
}
