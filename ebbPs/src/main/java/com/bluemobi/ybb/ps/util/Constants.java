package com.bluemobi.ybb.ps.util;

public class Constants
{
    /**
     * default encoding
     */
    public static final String DEFAULT_CHARSET = "utf-8";

    /**
     * app server address
     */
       public static final String SERVER_URL = "http://124.207.60.82:8070/ebb-api/";
//public static final String SERVER_URL = " http://10.58.160.51:8080/ebbapi/";//didi

//   public static final String SERVER_URL = "http://112.64.173.178/ebb-api/";
//   public static final String SERVER_URL = "http://172.51.101.100:8080/ebb-api/";
//    public static final String SERVER_URL = "http://10.58.160.51:8080/ebb-api/";//didi
//  public static final String SERVER_URL = "http://10.58.160.89:8080/ebbapi/";
//  public static final String SERVER_URL = "http://124.207.60.82:8070/ebb-api/";
//      public static final String SERVER_URL = "http://10.58.160.73:8080/ebbapi/";//王楠


    /**
     * user email or phonenum
     */
    public static final String USERACCOUNT = "useraccount";

    public static  int  NOTIFICATION = 1;
    /**
     * user pwd by md5
     */
    public static final String USERPWD = "userpwd";

    /**
     * is first load app,if so start app may load guide page
     */
    public static final String FIRSTLOADAPP = "firstloadapp";
    /**
     * record user is login
     */
    public static final String HASLOGIN = "haslogin";
    
 // 每页的基础数量
    public static final int CURRENTNUMBASE = 10;
    
    public static final String ISREMBERPWD = "0";//0 没有记住密码  1 记住密码
    
    public static final String SSID ="ssid";//ssid
    public static final String ID = "id";//id
    
    /** 
     *  服务器返回数据 status =0 有数据 
     */
    
    public static final int SERVER_BACK_HAS_DATA = 0;

//    public static final String DOWNLOAD_URL = "http://124.207.60.82:8080/FileLoad";

    /** 
     *  服务器返回数据 status =2 无数据 
     */
    
    public static final int SERVER_BACK_NO_DATA = 2;
    
    /**
     * 默认经度   南京
     */
    public static String DEFAULT_LATITUDE = "32.057233";
    /**
     * 默认纬度  南京
     */
    public static String DEFAULT_LONGITUDE = "118.778068";
    
    /**
     * 默认城市南京市
     */
    public static String DEFAULT_CITYNAME = "南京市";
    /**
     * apk下载地址
     */
    public static String FILE_PATH = "https://www.baidu.com";
    /**
     *  填写从短信SDK应用后台注册得到的APPKEY
     */
    public static final String MOB_SMS_KEY = "60a896a0d1fc";
    /**
     * 填写从短信SDK应用后台注册得到的APPSECRET
     */
    public static final String MOB_SMS_SECRET = "af197f79d8278a459d89e9395891ad7e";
    /**
     * 客服电话
     */
    public static final String SERVICE_TEL = "4001578889";

    /**
     *   不显示titlebar 右边的图片
     */

    public static final int NO_DISPLAY_TABBAR_RIGHT = -1;


    /**
     * @ClassName: Api
     * @Description: API类
     * @author wangzhijun
     * 
     */
    public class Api
    {
        /**
         * 
         * @ClassName: Http
         * @Description: http通讯类
         * 
         */
        public class Http
        {
            			public static final String TEST = "";
            			public static final String AREA = "index.php/appsever/area/getarea/";
            			public static final String RESETPHONE = "index.php/appsever/entrance/getcode/resetphone";
            			public static final String GETPWD = "index.php/appsever/entrance/getcode/getpwd";
            			//专线接单接口
            			public static final String LINE_RECEIVE = "mobi/lineinfo/order";
            			//车源接单接口
            			public static final String CARSOURCE_RECEIVE = "mobi/carsource/order";
            			public static final String APP_AOUBT_ID = "ff8080814c5f4937014c695c33a90096";
            			/**
            			 * 报警接口
            			 */
            			public static final String SYSTEMALERTS_MAIL = "mobi/systemalerts/email";
            			public static final String SYSTEMALERTS_SMS = "mobi/systemalerts/sms";
        }

        public class Tcp
        {

        }
    }
}
