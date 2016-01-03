package com.bluemobi.ybb.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.network.YbbHttpResponse;
import com.bluemobi.ybb.network.request.PayLimitRequest;
import com.bluemobi.ybb.network.request.PayPwdModifyRequest;
import com.bluemobi.ybb.network.request.SmsCodeRequest;
import com.bluemobi.ybb.network.request.SmsCodeValidateRequest;
import com.bluemobi.ybb.network.response.PayLimitResponse;
import com.bluemobi.ybb.network.response.PayPwdModifyResponse;
import com.bluemobi.ybb.network.response.SmsCodeResponse;
import com.bluemobi.ybb.network.response.SmsCodeValidateResponse;
import com.bluemobi.ybb.network.util.SmsType;
import com.bluemobi.ybb.util.StringUtils;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.LoadingPage;

/**
 * 设置支付限额 P14-13
 * Created by wangzhijun on 2015/7/11.
 */
public class PayLimitSetActivity extends BaseActivity implements View.OnClickListener{

    private EditText validateCode;
    private EditText Pay_limitation;

    private String limi;

    private TimeCount time;
    private Button get_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLoadingPage(false);
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.my_account_pay_limit_title, R.drawable.common_back, true);

        time = new TimeCount(60000, 1000);//构造CountDownTimer对象

    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.activity_pay_limit_set, null);

        get_code = (Button) view.findViewById(R.id.get_code);
        get_code.setOnClickListener(this);

        TextView submit = (TextView) view.findViewById(R.id.submit);
        submit.setOnClickListener(this);

        validateCode = (EditText) view.findViewById(R.id.validateCode);
        Pay_limitation = (EditText)view.findViewById(R.id.Pay_limitation);

        return view;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_code:
                doGetCode();
                break;
            case R.id.submit:
                doValidate();
                break;
        }
    }

    private void doValidate() {
        limi = Pay_limitation.getText().toString();
        if (StringUtils.isEmpty(validateCode.getText().toString())) {
            Utils.makeToastAndShow(getBaseContext(), "请输入验证码");
            return;
        }
        SmsCodeValidateRequest request = new SmsCodeValidateRequest(new Response.Listener<SmsCodeValidateResponse>() {
            @Override
            public void onResponse(SmsCodeValidateResponse response) {
                if (response != null && response.getStatus() == 0) {
                    Request();
                } else {
                    Utils.makeToastAndShow(getBaseContext(), "验证码验证失败");
                }
            }
        }, this);
        request.setCellphone(YbbApplication.getInstance().getMyUserInfo().getUserName());
        request.setType(SmsType.REGISTER);
        request.setValidationCode(validateCode.getText().toString());
        WebUtils.doPost(request);
    }

    private void Request() {

        PayLimitRequest request = new PayLimitRequest(new Response.Listener<PayLimitResponse>() {
            @Override
            public void onResponse(PayLimitResponse response) {
                Utils.closeDialog();

                if (response != null && response.getStatus() == 0) {// success
                    Utils.makeToastAndShow(getBaseContext(), "支付限额设置成功");
                    Utils.moveTo(mContext,MyAccountActivity.class);
                } else {// failed
                    Log.e("error", "error");
                }
            }
        }, this);
        request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setAccountLimit(limi);
        Utils.showProgressDialog(this);
        WebUtils.doPost(request);

    }

    private void doGetCode() {

        if (StringUtils.isEmpty(Pay_limitation.getText().toString())) {
            Utils.makeToastAndShow(getBaseContext(), "请输入支付限额");
            return;
        }
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
        request.setType(SmsType.REGISTER);
        request.setCellphone(YbbApplication.getInstance().getMyUserInfo().getUserName());
        Utils.showProgressDialog(this);
        WebUtils.doPost(request);
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

            get_code.setClickable(false);
            get_code.setText(millisUntilFinished /1000+"秒");
        }

        @Override
        public void onFinish() {//计时完毕时触发
            get_code.setText("重新验证");
            get_code.setClickable(true);
        }
    }
}
