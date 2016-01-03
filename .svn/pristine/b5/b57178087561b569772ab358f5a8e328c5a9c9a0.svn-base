package com.bluemobi.ybb.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.app.BackGroundTask;
import com.bluemobi.ybb.app.DbManager;
import com.bluemobi.ybb.app.YbbActivityManager;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.db.entity.Message;
import com.bluemobi.ybb.db.entity.MessageFoods;
import com.bluemobi.ybb.network.request.ExtLoginRequest;
import com.bluemobi.ybb.network.request.GetDefaultParamsRequest;
import com.bluemobi.ybb.network.response.GetDefaultParamsResponse;
import com.bluemobi.ybb.network.response.LoginResponse;
import com.bluemobi.ybb.util.Constants;
import com.bluemobi.ybb.util.SharedPreferencesUtil;
import com.bluemobi.ybb.util.StringUtils;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

/**
 * Created by wangzhijun on 2015/7/1.
 */
public class AppStartActivity extends Activity {
    private final static String tag ="AppStartActivity";

    private FrameLayout frameLayout;

    private ImageView imageView;

    private TextView textView;

    private int defaulTime = 3;

    private CountDownTimer downTimer;

    private boolean ext;

    private LoginResponse loginResponse;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.e("wangzhijun", "(onCreate");
        YbbApplication.getInstance().setMyUserInfo(null);
        YbbActivityManager.getInstance().popAllActivityExceptOne(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Logger.e("wangzhijun", "(bundle != null");
            String ssid = bundle.getString("ssid");//配送员
            String checkUserId = bundle.getString("checkUserId");//配送员
            String userId = bundle.getString("userId");//病人ID
            YbbApplication.getInstance().setAgentID(checkUserId);
            ext = false;
            if(StringUtils.isNotEmpty(ssid)){
                Logger.e("wangzhijun", "StringUtils.isNotEmpty(ssid)");
                ext = true;
                showView();
                YbbApplication.getInstance().setExt(true);

                ExtLoginRequest request = new ExtLoginRequest(new Response.Listener<LoginResponse>() {
                    @Override
                    public void onResponse(LoginResponse response) {
                        loginResponse = response;
                        if (response != null && response.getStatus() == 0) {// success
                            YbbApplication.getInstance().setUserPhone(response.getData().getUserName());
                            YbbApplication.getInstance().setMyUserInfo(response.getData());
                            BackGroundTask task = new BackGroundTask();
                            task.execute();
                            DbUtils dbUtils = DbManager.getInstance(AppStartActivity.this)
                                    .getDefaultDbUtils();
                            try {
                                boolean isExist = dbUtils.tableIsExist(Message.class);
                                if (!isExist) {// insert
                                    dbUtils.createTableIfNotExist(Message.class);
                                }
                                boolean is = dbUtils.tableIsExist(MessageFoods.class);
                                if (!is) {// insert
                                    dbUtils.createTableIfNotExist(MessageFoods.class);
                                }

                            } catch (DbException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        } else {
                            Utils.makeToastAndShow(getBaseContext(), response.getMsg());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                request.setUserId(userId);
                request.setSsid(ssid);
                request.setCheckUserId(checkUserId);
                WebUtils.doPost(request);
            }
        }

        process();
        getParams();

//        finish();
//        Utils.moveTo(this, AppStartActivity.class);


    }

    private void getParams() {
        GetDefaultParamsRequest request = new GetDefaultParamsRequest(new Response.Listener<GetDefaultParamsResponse>() {
            @Override
            public void onResponse(GetDefaultParamsResponse response) {
//                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                   /* Logger.d(tag, "获取未登录静态数据（用户类型列表）ok");
                    Logger.d(tag,"type:"+response.getData().getType());
                    Logger.d(tag,"remark:"+response.getData().getAdminUserTypeList().get(0).getRemark());*/
                    YbbApplication.getInstance().setDefaultParams(response.getData());
                    //Logger.d(tag,"shopsId: "+YbbApplication.getInstance().getDefaultParams().getAdminUserTypeList().get(0).getShopsId());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AppStartActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }
        });

//        Utils.showProgressDialog(this);
        WebUtils.doPost(request);
    }
    private void showView(){
        frameLayout = new FrameLayout(this);
//            imageView = new ImageView(this);
        textView = new TextView(this);
        textView.setTextColor(Color.RED);
        textView.setText(String.valueOf(defaulTime));
        downTimer = new CountDownTimer(defaulTime * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                SharedPreferencesUtil.saveToFile(AppStartActivity.this, Constants.FIRSTLOADAPP, "false");
                finish();
                if(!ext){
                    Utils.moveTo(AppStartActivity.this, LoginActivity.class);
                }else{
                    if(loginResponse == null){
                        return;
                    }
                    if ("1".equals(loginResponse.getData().getIsBind())) {//是否绑定地址：1已绑定地址、0未绑定"
                        // 把TokenID \ Userid \ Usertype 保存
                        Utils.moveTo(AppStartActivity.this, HomeActivity.class);
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra("from", "login_register");
                        if (YbbApplication.getInstance().role_bh.equals(loginResponse.getData().getTypeId())) {//病患
                            intent.setClass(AppStartActivity.this, ModificationAddressPatientActivity.class);
                            //病患
                        }
                        if (YbbApplication.getInstance().role_yh.equals(loginResponse.getData().getTypeId())) {
                            intent.setClass(AppStartActivity.this, ModificationAddressMedicalActivity.class);
                            //医护
                        }
                        startActivity(intent);
                    }
                    // 把TokenID \ Userid \ Usertype 保存
//                                YbbApplication.getInstance().setMyUserInfo(response.getData());
//                                Utils.moveTo(AppStartActivity.this,HomeActivity.class);
                    finish();
                }
            }
        };
        downTimer.start();
//            imageView.setImageResource(R.drawable.loading);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.TOP | Gravity.RIGHT;
        params.topMargin = Utils.dip2px(this, 8);
        params.rightMargin = Utils.dip2px(this, 8);
        frameLayout.addView(textView, params);
        frameLayout.setBackgroundResource(R.drawable.loading);
        setContentView(frameLayout);
    }

    private void process() {
        if(ext){
            return;
        }
        String temp = SharedPreferencesUtil.getFromFileByDefault(this, Constants.FIRSTLOADAPP, "true");
        if (Boolean.parseBoolean(temp)) {
            showView();
        } else {
            finish();
            Utils.moveTo(this, LoginActivity.class);
//            Utils.moveTo(this, MyAlarmActivity.class);
        }
    }


}
