package com.bluemobi.ybb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.network.YbbHttpResponse;
import com.bluemobi.ybb.network.request.SmsCodeRequest;
import com.bluemobi.ybb.network.request.SmsCodeValidateRequest;
import com.bluemobi.ybb.network.request.UpdatePasswordRequest;
import com.bluemobi.ybb.network.response.SmsCodeResponse;
import com.bluemobi.ybb.network.response.SmsCodeValidateResponse;
import com.bluemobi.ybb.network.response.UpdatePasswordResponse;
import com.bluemobi.ybb.network.util.SmsType;
import com.bluemobi.ybb.util.StringUtils;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.LoadingPage;

/**
 * Created by gaoyn on 2015/6/30.
 * <p/>
 * p4 找回密码
 */
public class
        RetrievePasswordActivity extends BaseActivity implements View.OnClickListener {
    private final static String tag = "RetrievePasswordActivity";

    private Button button;

    private EditText phone;
    //验证码输入框
    private EditText verification_code;
    //获取验证码按钮
    private Button verification_code_button;

    private TimeCount time;

    private ImageView iv_clear_phone;

    private ImageView iv_clear_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(RetrievePasswordActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.retrieve_password, R.drawable.common_back, true);


        showLoadingPage(false);
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {


        View view = inflater.inflate(R.layout.activity_retrieve_password, null);

        button = (Button) view.findViewById(R.id.next_step);
        button.setOnClickListener(this);

        phone = (EditText) view.findViewById(R.id.phone);
        verification_code = (EditText) view.findViewById(R.id.verification_code);

        verification_code_button = (Button) view.findViewById(R.id.verification_code_button);
        verification_code_button.setOnClickListener(this);


        iv_clear_phone = (ImageView) view.findViewById(R.id.iv_clear_phone);
        iv_clear_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone.setText("");
            }
        });

        iv_clear_code = (ImageView) view.findViewById(R.id.iv_clear_code);
        iv_clear_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verification_code.setText("");
            }
        });


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
            case R.id.next_step:

                if (checkInput()) {
                    if (StringUtils.isEmpty(verification_code.getText().toString())) {
                        Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                        break ;
                    }
                    Logger.d(tag, phone.getText().toString() + "");

                    SmsCodeValidateRequest request = new SmsCodeValidateRequest(new Response.Listener<SmsCodeValidateResponse>() {
                        @Override
                        public void onResponse(SmsCodeValidateResponse response) {
                            if (response != null && response.getStatus() == 0) {

                                Intent intent = new Intent();
                                intent.setClass(RetrievePasswordActivity.this, RetrievePasswordNextActivity.class);   //描述起点和目标
                                Bundle bundle = new Bundle();                           //创建Bundle对象
                                bundle.putString("phone", phone.getText().toString());     //装入数据
                                intent.putExtras(bundle);                                //把Bundle塞入Intent里面
                                startActivity(intent);

                            } else {
                                Utils.makeToastAndShow(getBaseContext(), "验证码验证失败");
                            }
                        }
                    }, RetrievePasswordActivity.this);
                    request.setCellphone(phone.getText().toString());
                    request.setType(SmsType.FORGOTPASSWORD);
                    request.setValidationCode(verification_code.getText().toString());
                    WebUtils.doPost(request);


                }


                break;

            case R.id.verification_code_button:
                Logger.d(tag, "你单击了获取验证码");
                if (checkInput()) {

                    SmsCodeRequest();
                }

                break;
        }
    }

    private void SmsCodeRequest() {
        SmsCodeRequest request = new SmsCodeRequest(new Response.Listener<SmsCodeResponse>() {
            @Override
            public void onResponse(SmsCodeResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {
                    Utils.makeToastAndShow(getBaseContext(), "验证码已发送");
                    time.start();
                } else {
                    Utils.makeToastAndShow(getBaseContext(), "验证码获取失败，请重新获取");
                }
            }
        }, this);
        request.setType(SmsType.FORGOTPASSWORD);
        request.setCellphone(phone.getText().toString());
        Utils.showProgressDialog(this);
        WebUtils.doPost(request);
    }


    /**
     * 检测表单
     *
     * @return
     */
    private boolean checkInput() {

        if (StringUtils.isEmpty(phone.getText().toString())) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Utils.checkPhoneNum(phone.getText().toString())) {
            Toast.makeText(this, "手机号不合法", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;


    }
    private class TimeCount extends CountDownTimer {
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示

            verification_code_button.setClickable(false);
            verification_code_button.setText(millisUntilFinished /1000+"秒");
        }

        @Override
        public void onFinish() {//计时完毕时触发
            verification_code_button.setText("重新验证");
            verification_code_button.setClickable(true);
        }
    }
}
