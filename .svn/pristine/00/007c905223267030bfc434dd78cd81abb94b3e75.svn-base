package com.bluemobi.ybb.ps.app;


import android.os.Environment;
import android.os.Handler;

import com.bluemobi.ybb.ps.network.model.ParamModel;
import com.bluemobi.ybb.ps.network.model.UserInfo;
import org.litepal.LitePalApplication;

/**
 * Created by wangzhijun on 2015/7/13.
 */
public class YbbPsApplication extends LitePalApplication {

    public static YbbPsApplication instance;

    private final String SD_PATH = Environment.getExternalStorageDirectory()
            .getAbsolutePath();
    public final String FILE_PATH = SD_PATH + "/YBBLOG/";


    private static int mainTid;
    private static Handler handler;

    private UserInfo myUserInfo;
    private long pageTime;

    public String userPhone;

    /**
     * 餐厅id
     */
    private String canteenId;

    private ParamModel paramModel;
/*
    * 病患
    */
    public static String role_bh = "8aba20b64edda41a014edda5ee0d0003";

    /**
     * 病患
     */
    public static String role_bh_name = "患者";
    /**
     * 医护
     */
    public static String role_yh = "8aba20b64edda41a014edda633f20005";
    /**
     * 医护
     */
    public static String role_yh_name = "医护";

    private long requestTime;


    @Override
    public void onCreate()
    {
        super.onCreate();
        init();
        instance = this;
/*	    CrashHandler crashHandler = CrashHandler.getInstance();
	    crashHandler.init(getApplicationContext());*/
        mainTid = android.os.Process.myTid();
        handler=new Handler();
    }

    private void init()
    {
        VolleyManager.init(this);
    }


    public static YbbPsApplication getInstance()
    {
        return instance;
    }


    public static int getMainTid() {
        return mainTid;
    }
    public static Handler getHandler() {
        return handler;
    }

    public UserInfo getMyUserInfo() {
        return myUserInfo;
    }

    public void setMyUserInfo(UserInfo myUserInfo) {
        this.myUserInfo = myUserInfo;
    }

    public long getPageTime() {
        return pageTime;
    }

    public void setPageTime(long pageTime) {
        this.pageTime = pageTime;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getCanteenId() {
        return canteenId;
    }

    public void setCanteenId(String canteenId) {
        this.canteenId = canteenId;
    }

    public ParamModel getParamModel() {
        return paramModel;
    }

    public void setParamModel(ParamModel paramModel) {
        this.paramModel = paramModel;
    }

    public long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(long requestTime) {
        this.requestTime = requestTime;
    }
}
