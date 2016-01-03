package com.bluemobi.ybb.ps.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.base.utils.StringUtils;
import com.bluemobi.base.utils.Utils;
import com.bluemobi.base.utils.WebUtils;
import com.bluemobi.ybb.ps.R;
import com.bluemobi.ybb.ps.app.BackGroundTask;
import com.bluemobi.ybb.ps.app.DbManager;
import com.bluemobi.ybb.ps.app.TitleBarManager;
import com.bluemobi.ybb.ps.app.YbbPsApplication;
import com.bluemobi.ybb.ps.base.BaseActivity;
import com.bluemobi.ybb.ps.db.entity.PSMessage;
import com.bluemobi.ybb.ps.db.entity.PSMessageFoods;
import com.bluemobi.ybb.ps.network.request.LoginRequest;
import com.bluemobi.ybb.ps.network.response.LoginResponse;
import com.bluemobi.ybb.ps.view.LoadingPage;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;


/**
 * Created by gaoyn on 2015/6/29.
 *
 * p3 登录
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private TextView PasswordForget;
    private Button button;
    private ImageButton qq;
    private ImageButton weibo;
    private ImageButton sina;

    private EditText username;
    private EditText password;


    TitleBarManager titleBarManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        titleBarManager = new TitleBarManager();
        titleBarManager.init(LoginActivity.this,getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.Login, R.drawable.common_back,false);

        showLoadingPage(false);
    }


    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.activity_login,null);
        PasswordForget = (TextView)view.findViewById(R.id.password_forget);
        PasswordForget.setOnClickListener(this);
        button = (Button)view.findViewById(R.id.Login);
        button.setOnClickListener(this);

        /*二期做 第三方登入*/
        qq = (ImageButton)view.findViewById(R.id.qq);
        weibo = (ImageButton)view.findViewById(R.id.weibo);
        sina = (ImageButton)view.findViewById(R.id.sina);
        qq.setOnClickListener(this);
        weibo.setOnClickListener(this);
        sina.setOnClickListener(this);

        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);

        return view;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }


    @Override
    public void clickBarleft() {

        finish();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.password_forget:
                Utils.moveTo(LoginActivity.this,RetrievePasswordActivity.class);
                break;
            case R.id.Login:

//                username.setText("13804040812");
//                password.setText("123456");


//                username.setText("13654990999");
//                password.setText("111111");
                if (checkUserPas()) {
                    LoginRequest request = new LoginRequest(new Response.Listener<LoginResponse>() {
                        @Override
                        public void onResponse(LoginResponse response) {
                            Utils.closeDialog();
                            if (response != null && response.getStatus() == 0) {// success
                                YbbPsApplication.getInstance().setUserPhone(username.getText().toString());
                                YbbPsApplication.getInstance().setMyUserInfo(response.getData());
//                                BackGroundTask task = new BackGroundTask();
//                                task.execute();
                                DbUtils dbUtils = DbManager.getInstance(mContext)
                                        .getDefaultDbUtils();
                                try {
                                    boolean isExist = dbUtils.tableIsExist(PSMessage.class);
                                    if (!isExist) {// insert
                                        dbUtils.createTableIfNotExist(PSMessage.class);
                                    }
                                    boolean is = dbUtils.tableIsExist(PSMessageFoods.class);
                                    if (!is) {// insert
                                        dbUtils.createTableIfNotExist(PSMessageFoods.class);
                                    }

                                } catch (DbException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                Utils.moveTo(LoginActivity.this, DiliverymanMainActivity.class);
                                finish();
                            }else{
                                Utils.makeToastAndShow(getBaseContext(), response.getMsg());
                            }
                        }
                    }, LoginActivity.this);
                    request.setUsername(username.getText().toString());
                    request.setPassword(password.getText().toString());
                    request.setHandleCustomErr(false);
                    Utils.showProgressDialog(this);
                    WebUtils.doPost(request);
                }
                break;



            //二期做 第三方登入
            case R.id.qq:
                Toast.makeText(this,R.string.looking_forward_to,Toast.LENGTH_SHORT).show();
                break;
            case R.id.weibo:
                Toast.makeText(this,R.string.looking_forward_to,Toast.LENGTH_SHORT).show();
                break;
            case R.id.sina:
                Toast.makeText(this,R.string.looking_forward_to,Toast.LENGTH_SHORT).show();
                break;

        }
    }

    private boolean checkUserPas() {
        if (StringUtils.isEmpty(username.getText().toString())) {
            Toast.makeText(LoginActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (StringUtils.isEmpty(password.getText().toString())) {
            Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Utils.checkPhoneNum(username.getText().toString())) {
            Toast.makeText(LoginActivity.this, "手机号不合法", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.getText().toString().length() < 6 || password.getText().toString().length() > 22) {
            Toast.makeText(this, "密码长度为6-22", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
