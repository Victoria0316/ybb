package com.bluemobi.ybb.network.util;

/**
 * Created by wangzhijun on 2015/8/9.
 */
public class ParameterUtil {

    public static String getSmsType(SmsType smsType){
        String temp = "";
        if(SmsType.REGISTER == smsType){
            temp = "register";
        }else if(SmsType.LOGIN == smsType){
            temp = "login";
        }else if(SmsType.BIND == smsType){
            temp = "bind";
        }else if(SmsType.FORGOTPASSWORD == smsType){
            temp = "forgotpassword";
        }
        return temp;
    }
}
