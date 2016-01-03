package com.bluemobi.ybb.activity;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.network.request.RegisteredRequest;
import com.bluemobi.ybb.network.response.RegisteredResponse;
import com.bluemobi.ybb.util.StringUtils;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.LoadingPage;

/**
 * Created by gaoyn on 2015/6/29.
 * <p>
 * p2_1 注册—手机
 */
public class RegisteredNextActivity extends BaseActivity implements View.OnClickListener {

    private Button button;
    /**
     * 页面传递
     */
    private String userType;
    /**
     * 页面传递
     */
    private String userTypeName;

    /**
     * 页面传递
     */
    private String registerPhone;

    private EditText nickname;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userType = getIntent().getStringExtra("userType");
        userTypeName = getIntent().getStringExtra("userTypeName");
        registerPhone = getIntent().getStringExtra("registerPhone");
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(RegisteredNextActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.register, R.drawable.common_back, true);
        showLoadingPage(false);
    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.activity_registered_next, null);

        button = (Button) view.findViewById(R.id.str_submit);
        button.setOnClickListener(this);

        nickname = (EditText) view.findViewById(R.id.nickname);
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
        switch (v.getId()) {
            case R.id.str_submit:
                if (checkUserPas()) {
                    Request();
                }
                break;
        }
    }

    private void Request() {

        RegisteredRequest request = new RegisteredRequest(new Response.Listener<RegisteredResponse>() {
            @Override
            public void onResponse(RegisteredResponse response) {

                if (response != null && response.getStatus() == 0) {// success
                    if (userType.equals(YbbApplication.role_bh)) {
                        Utils.moveTo(RegisteredNextActivity.this, ModificationAddressPatientActivity.class);
                    } else if(userType.equals(YbbApplication.role_yh)){
                        Utils.moveTo(RegisteredNextActivity.this, ModificationAddressMedicalActivity.class);
                    }
                    Utils.moveTo(RegisteredNextActivity.this, LoginActivity.class);
                    Utils.makeToastAndShow(getBaseContext(), "注册成功");
                    finish();
                } else {// failed
                    Log.e("error", "error");
                }
            }
        }, RegisteredNextActivity.this);
        request.setUserName(registerPhone);
        request.setAdminTypeName(userTypeName);
        request.setAdminTypeId(userType);
        request.setPassword(password.getText().toString());
        request.setNickName(nickname.getText().toString());
        Utils.showProgressDialog(this);
        WebUtils.doPost(request);

    }

    private boolean checkUserPas() {
        if (StringUtils.isEmpty(nickname.getText().toString())) {
            Toast.makeText(RegisteredNextActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (nickname.getText().toString().length() > 10) {
            Toast.makeText(RegisteredNextActivity.this, "昵称长度需在10位以内", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (StringUtils.isEmpty(password.getText().toString())) {
            Toast.makeText(RegisteredNextActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.getText().toString().length() < 6 || password.getText().toString().length() > 16) {
            Toast.makeText(RegisteredNextActivity.this, "密码长度需在6-16以内", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}