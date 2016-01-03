package com.bluemobi.ybb.ps.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.base.utils.StringUtils;
import com.bluemobi.base.utils.Utils;
import com.bluemobi.base.utils.WebUtils;
import com.bluemobi.ybb.ps.R;
import com.bluemobi.ybb.ps.app.TitleBarManager;
import com.bluemobi.ybb.ps.base.BaseActivity;
import com.bluemobi.ybb.ps.network.request.UpdatePasswordRequest;
import com.bluemobi.ybb.ps.network.response.UpdatePasswordResponse;
import com.bluemobi.ybb.ps.view.LoadingPage;


/**
 * Created by gaoyn on 2015/6/30.
 *
 * p4-1 找回密码
 */
public class RetrievePasswordNextActivity extends BaseActivity implements View.OnClickListener{

    private final static String tag = "RetrievePasswordNextActivity";

    private Button button;
    private EditText password;
    private EditText password_confirm;

    private String str_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(RetrievePasswordNextActivity.this,getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.retrieve_password, R.drawable.common_back,true);
        showLoadingPage(false);

        //        获取上个页面传过来的手机号
        Intent intent = this.getIntent();        //获取已有的intent对象
        Bundle bundle = intent.getExtras();    //获取intent里面的bundle对象
        str_phone = bundle.getString("phone");    //获取Bundle里面的字符串
        Logger.d(tag, "上个页面传过来的手机号：" + str_phone);
    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.activity_retrieve_password_next,null);

        button = (Button)view.findViewById(R.id.Login);
        button.setOnClickListener(this);

        password = (EditText) view.findViewById(R.id.password);
        password_confirm = (EditText) view.findViewById(R.id.password_confirm);

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
            case R.id.Login:
                if (checkInput()) {
                    Logger.d(tag, "手机号：" + str_phone + " 密码：" + password.getText().toString());
                    Request(str_phone, password.getText().toString());
                }
                break;
        }
    }

    private void Request(String username, String password) {
        UpdatePasswordRequest request = new UpdatePasswordRequest(new Response.Listener<UpdatePasswordResponse>() {
            @Override
            public void onResponse(UpdatePasswordResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    Logger.d(tag, "修改密码成功");
                    finishAll();
                    Utils.moveTo(RetrievePasswordNextActivity.this, LoginActivity.class);

                } else {
                    Toast.makeText(RetrievePasswordNextActivity.this, response.getContent(), Toast.LENGTH_SHORT).show();
                }
            }
        }, RetrievePasswordNextActivity.this);

        request.setUsername(username);
        request.setPassword(password);


        request.setHandleCustomErr(false);

        Utils.showProgressDialog(this);
        WebUtils.doPost(request);
    }

    private boolean checkInput() {


        if (StringUtils.isEmpty(password.getText().toString())) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.getText().toString().length() < 6 || password.getText().toString().length() > 22) {
            Toast.makeText(this, "密码长度为6-22", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (StringUtils.isEmpty(password_confirm.getText().toString())) {
            Toast.makeText(this, "确认密码不能为空", Toast.LENGTH_LONG).show();
            return false;
        }
        if (password_confirm.getText().toString().length() < 6 || password_confirm.getText().toString().length() > 22) {
            Toast.makeText(this, "确认密码长度为6-22", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.getText().toString().equals(password_confirm.getText().toString())) {
            Toast.makeText(this, "密码与确认密码不一致", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }
}
