package com.bluemobi.ybb.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.network.request.AlipayTransferOutRequest;
import com.bluemobi.ybb.network.request.TrunInorOutRequest;
import com.bluemobi.ybb.network.response.AlipayTransferOutResponse;
import com.bluemobi.ybb.network.response.TrunInorOutResponse;
import com.bluemobi.ybb.util.AlipayUtil;
import com.bluemobi.ybb.util.Constants;
import com.bluemobi.ybb.util.StringUtils;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.LoadingPage;
import com.bluemobi.ybb.view.PaymentDialog;
import com.bluemobi.ybb.view.YbbAlertDialog;

/**
 * Created by wangzhijun on 2015/7/11.
 */
public class AccountOutActivity extends BaseActivity implements View.OnClickListener, AlipayUtil.AlipayStausListener {

    private EditText amount;

    private int payType = 1;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLoadingPage(false);
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.my_account_out, R.drawable.common_back, true);
    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.activity_account_out, null);
        view.findViewById(R.id.rl_wx).setOnClickListener(this);
        view.findViewById(R.id.rl_zfb).setOnClickListener(this);
        amount = (EditText) view.findViewById(R.id.amount);
        return view;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void onClick(View v) {
        PaymentDialog paymentDialog = new PaymentDialog();
        switch (v.getId()) {
            case R.id.rl_wx:
                payType = 1;
                Utils.makeToastAndShow(getBaseContext(), "敬请期待");
                /*if(StringUtils.isEmpty(amount.getText().toString())){
                    Utils.makeToastAndShow(getBaseContext(), "请输入金额");
                    return;
                }
                sendRequest("4");*/
                break;
            case R.id.rl_zfb:
                payType = 2;
                if (StringUtils.isEmpty(amount.getText().toString())) {
                    Utils.makeToastAndShow(getBaseContext(), "请输入金额");
                    return;
                }
                sendRequest("3");
                break;
        }
    }

    Dialog dialog_passowrd;
    public void sendRequest(final String type) {
        /*final  PaymentDialog paymentDialog = new PaymentDialog();
        TrunInorOutRequest request = new TrunInorOutRequest(new Response.Listener<TrunInorOutResponse>() {
            @Override
            public void onResponse(TrunInorOutResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    id=response.getData().getValue();//订单
                    AlipayUtil pay= new AlipayUtil(AccountOutActivity.this);
                    pay.pay("ebb","ebb transfer out",amount.getText().toString(),id,Constants.SERVER_URL+Constants.CALL_BACK);
                } else {// failed
                    Log.e("error", "error");
                }
            }
        }, AccountOutActivity.this);
        request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setRechargeChannel(type);//支付宝
        request.setRechargeAmount(amount.getText().toString());
        request.setRechargeType("4");//4转出
        Utils.showProgressDialog(this);
        WebUtils.doPost(request);
*/

        final View view = LayoutInflater.from(this).inflate(R.layout.dialog_alipay_transfer_out, null);


        final EditText alipay_account = (EditText) view.findViewById(R.id.alipay_account);
        final EditText alipay_name = (EditText) view.findViewById(R.id.alipay_name);
        Button confirm = (Button) view.findViewById(R.id.btn_cancel);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_passowrd.dismiss();
            }
        });
        Button sure = (Button) view.findViewById(R.id.btn_sure);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(StringUtils.isEmpty(alipay_account.getText().toString())){
                    Utils.makeToastAndShow(getBaseContext(), "请输入支付宝账号");
                    return;
                }  if(StringUtils.isEmpty(alipay_name.getText().toString())){
                    Utils.makeToastAndShow(getBaseContext(), "请输入支付宝名称");
                    return;
                }
                AlipayTransferOutRequest request = new AlipayTransferOutRequest(new Response.Listener<AlipayTransferOutResponse>() {
                    @Override
                    public void onResponse(AlipayTransferOutResponse response) {
                        Utils.closeDialog();
                        if (response != null && response.getStatus() == 0) {
                            Utils.makeToastAndShow(getBaseContext(), "转出成功");
                            dialog_passowrd.dismiss();
                        } else {
                            dialog_passowrd.dismiss();
                            Utils.makeToastAndShow(getBaseContext(), response.getMsg()==null?"转出失败":response.getMsg());
                        }
                    }
                }, AccountOutActivity.this);
                request.setHandleCustomErr(false);
                request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
                request.setRechargeChannel(type);
                request.setRechargeAmount(amount.getText().toString());
                request.setRechargeType("4");
                request.setAlipayId(alipay_account.getText().toString());
                request.setAlipayName(alipay_name.getText().toString());
                WebUtils.doPost(request);
                Utils.showProgressDialog(AccountOutActivity.this);
            }
        });

        dialog_passowrd = new Dialog(this);
        Window window = dialog_passowrd.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        dialog_passowrd.setContentView(view, new LinearLayout.LayoutParams(-2, -2));
        dialog_passowrd.setCanceledOnTouchOutside(false);
        dialog_passowrd.show();

    }

    @Override
    public void checkResult(boolean isExist) {

    }

    @Override
    public void payOk() {
        Toast.makeText(AccountOutActivity.this, "成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void payFailed() {
        Toast.makeText(AccountOutActivity.this, "失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void paying() {

    }
}
