package com.bluemobi.ybb.app;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;

import com.bluemobi.ybb.network.model.EditShopCarModel;
import com.bluemobi.ybb.network.model.ParamModel;
import com.bluemobi.ybb.network.model.DefaultParams;
import com.bluemobi.ybb.network.model.UserInfo;
import com.bluemobi.ybb.network.response.ShopCartResponse;
import com.bluemobi.ybb.util.PreferencesService;
import com.bluemobi.ybb.util.StringUtils;

import org.litepal.LitePalApplication;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liufy on 2015/6/23.
 */
public class YbbApplication extends LitePalApplication
{
    public static YbbApplication instance;

    private final String SD_PATH = Environment.getExternalStorageDirectory()
            .getAbsolutePath();
    public final String FILE_PATH = SD_PATH + "/YBBLOG/";


    private static int mainTid;
    private static Handler handler;

    private UserInfo myUserInfo;

    private DefaultParams defaultParams;
    /**
     * 餐厅id
     */
    private String canteenId;
    /**
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


    public String userPhone;
    /**
     * 分页查询 往server 传递 时间
     */
    private long pageTime;

    private ParamModel paramModel;
    private long requestTime;

    private boolean ext;
    private long endTime;
    /**
     * 购物车
     */
    private int cartCounts;
    /**
     * 未支付的订单总额
     */
    private String orderprice_count;
    private String cartAmount;
    private String agentID;//配送员ID


    private List<ShopCartResponse.ShopCartData.ShopCartDTo> selectDataList;

    private boolean orderNeedLoad;

    private HashMap<String, ParamModel.ProductAttributeEntity> attributeEntityHashMap = new HashMap<String, ParamModel.ProductAttributeEntity>();

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


    public static YbbApplication getInstance()
    {
        return instance;
    }

    public String getCartAmount() {
        if(StringUtils.isEmpty(cartAmount)){
            return "0";
        }
        BigDecimal bigDecimal = new BigDecimal(cartAmount);
        bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_UP);
        return String.valueOf(bigDecimal.floatValue());
    }

    public void setCartAmount(String cartAmount) {
        this.cartAmount = cartAmount;
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

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public ParamModel getParamModel() {
        return paramModel;
    }

    public void setParamModel(ParamModel paramModel) {
        this.paramModel = paramModel;
    }

    public long getPageTime()
    {
        return pageTime;
    }

    public void setPageTime(long pageTime)
    {
        this.pageTime = pageTime;
    }

    public DefaultParams getDefaultParams() {
        return defaultParams;
    }

    public void setDefaultParams(DefaultParams defaultParams) {
        this.defaultParams = defaultParams;
    }

    public long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(long requestTime) {
        this.requestTime = requestTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getCanteenId() {
        return canteenId;
    }

    public void setCanteenId(String canteenId) {
        this.canteenId = canteenId;
    }

    public int getCartCounts() {
        return cartCounts;
    }

    public void setCartCounts(int cartCounts) {
        this.cartCounts = cartCounts;
        try {
            PreferencesService.getInstance(getApplicationContext()).saveInt("cartCounts", cartCounts);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getOrderprice_count() {
        return orderprice_count;
    }

    public void setOrderprice_count(String orderprice_count) {
        this.orderprice_count = orderprice_count;
    }


    public List<ShopCartResponse.ShopCartData.ShopCartDTo> getSelectDataList() {
        return selectDataList;
    }

    public void setSelectDataList(List<ShopCartResponse.ShopCartData.ShopCartDTo> selectDataList) {
        this.selectDataList = selectDataList;
    }

    public void setExt(boolean b) {
        ext = b;
    }
    public boolean getExt() {
        return ext;
    }

    public boolean isOrderNeedLoad() {
        return orderNeedLoad;
    }

    public void setOrderNeedLoad(boolean orderNeedLoad) {
        this.orderNeedLoad = orderNeedLoad;
    }

    public String getAgentID() {
        return agentID;
    }

    public void setAgentID(String agentID) {
        this.agentID = agentID;
    }

    public HashMap<String, ParamModel.ProductAttributeEntity> getAttributeEntityHashMap() {
        return attributeEntityHashMap;
    }

    public void setAttributeEntityHashMap(HashMap<String, ParamModel.ProductAttributeEntity> attributeEntityHashMap) {
        this.attributeEntityHashMap = attributeEntityHashMap;
    }
}
