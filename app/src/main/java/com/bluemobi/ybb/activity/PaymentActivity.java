package com.bluemobi.ybb.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.callback.PaymentDialogOnclick;
import com.bluemobi.ybb.network.request.BalancePayRequest;
import com.bluemobi.ybb.network.request.WxOrderRequest;
import com.bluemobi.ybb.network.response.BalancePayResponse;
import com.bluemobi.ybb.network.response.WxOrderResponse;
import com.bluemobi.ybb.util.AlipayUtil;
import com.bluemobi.ybb.util.Constants;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.util.WxPayUtils;
import com.bluemobi.ybb.view.LoadingPage;
import com.bluemobi.ybb.view.PaymentDialog;
import com.bluemobi.ybb.view.YbbAlertDialog;

/**
 * Created by liufy on 2015/7/9.
 */
public class PaymentActivity extends BaseActivity implements View.OnClickListener,
        AlipayUtil.AlipayStausListener, PaymentDialogOnclick {

    private TitleBarManager titleBarManager;

    private RelativeLayout rl_alipay;
    private RelativeLayout rl_wechat;
    private RelativeLayout rl_balance_pay;
    private TextView amount;

    private String orderNo;
    private String totalAmount;

    private int weiPayCode = -4;

    private MyBroadcastReciver mReciver;
    /**
     * mineOrder、
     */
    private String from;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderNo = getIntent().getStringExtra("orderNo");
        totalAmount = getIntent().getStringExtra("totalAmount");
        from = getIntent().getStringExtra("from");
        titleBarManager = new TitleBarManager();
        titleBarManager.init(PaymentActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.str_shop_payment, R.drawable.common_back, true);
        showLoadingPage(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("cn.abel.action.broadcast");
        mReciver = new MyBroadcastReciver();
        this.registerReceiver(mReciver, intentFilter);
        if (0 == weiPayCode) {//支付成功
            payOk();
        }
        if (-1 == weiPayCode) {//失败
            final YbbAlertDialog dialog = new YbbAlertDialog(this);
            dialog
                    .setTitle("支付失败")
                    .setMessage("请重新支付。")
                    .setNegativeButtonClickListener("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    })
            ;
            dialog.show();
        }
        if (-2 == weiPayCode) {//取消支付
            Utils.makeToastAndShow(this, "取消了支付");
        }
    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {

        View paymentView = inflater.inflate(R.layout.activity_payment, null);
        rl_alipay = (RelativeLayout) paymentView.findViewById(R.id.rl_alipay);
        rl_wechat = (RelativeLayout) paymentView.findViewById(R.id.rl_wechat);
        rl_balance_pay = (RelativeLayout) paymentView.findViewById(R.id.rl_balance_pay);
        amount = (TextView) paymentView.findViewById(R.id.amount);
        amount.setText(totalAmount);
        rl_alipay.setOnClickListener(this);
        rl_wechat.setOnClickListener(this);
        rl_balance_pay.setOnClickListener(this);
        return paymentView;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_alipay:
                AlipayUtil alipayUtil = new AlipayUtil(this, this);
//                alipayUtil.pay("ebb " + orderNo,"body " + orderNo,totalAmount, orderNo, Constants.SERVER_URL + Constants.CALL_BACK);
                alipayUtil.pay("ebb " + orderNo, "body " + orderNo, totalAmount,
                        orderNo, Constants.CALL_BACK);
//                alipayUtil.pay("ebb " + orderNo,"body " + orderNo,"0.01", orderNo, "http://lcg711624.xicp.net:34087/ebbmgr/ali/alipayNotify");
                break;
            case R.id.rl_balance_pay:
                showPayDialog();
                break;
            case R.id.rl_wechat:
                doWxPay();
                break;

        }

    }

    private void doWxPay() {
        WxOrderRequest request = new WxOrderRequest(new Response.Listener<WxOrderResponse>() {
            @Override
            public void onResponse(WxOrderResponse response) {
                Utils.closeDialog();
                if(paymentDialog!=null){
                    paymentDialog.dismiss();
                }
                if(response != null&& response.getStatus()==0){
                    WxPayUtils.pay(PaymentActivity.this, response.getData());
                }else{
                    Utils.makeToastAndShow(getBaseContext(), response.getMsg());
                }
            }
        },this);
        request.setOrderNo(orderNo);
        request.setTotalPrice(totalAmount);
//        request.setTotalPrice("0.01");
        request.setHandleCustomErr(false);
        WebUtils.doPost(request);
        Utils.showProgressDialog(this);
    }

    PaymentDialog paymentDialog;
    public void showPayDialog() {
        paymentDialog = new PaymentDialog();
        paymentDialog.setmListener(this);
        paymentDialog.show(getFragmentManager(), null);
    }

    @Override
    public void checkResult(boolean isExist) {

    }

    private void sendBroadCast(){
        if("mineOrder".equals(from)){
            Intent intent = new Intent();
            intent.setAction(Constants.RECEIVER_ORDER_DATA_CHANGE);
            intent.putExtra("type", MineOrderActivcity.PAY);
            sendBroadcast(intent);
        }
    }
    @Override
    public void payOk() {
        if("ordermake".equals(from)){
            Utils.moveTo(this, MineOrderActivcity.class);
        }else{
            sendBroadCast();
        }
        finish();
    }

    @Override
    public void payFailed() {

    }

    @Override
    public void paying() {

    }

    @Override
    public void doSure(View v, String pwd) {
        BalancePayRequest request = new BalancePayRequest(new Response.Listener<BalancePayResponse>() {
            @Override
            public void onResponse(BalancePayResponse response) {
                Utils.closeDialog();
                if(paymentDialog!=null){
                    paymentDialog.dismiss();
                }

                if(response!= null && response.getStatus()==0){
                    Utils.makeToastAndShow(getBaseContext(), "支付成功");
                    payOk();
                }else {
                    Utils.makeToastAndShow(getBaseContext(), response.getMsg());
                }
            }
        }, this);
        request.setOrderNo(orderNo);
        request.setPaypassword(pwd);
        request.setTotalPrice(totalAmount);
//        request.setTotalPrice("0.01");
        request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setHandleCustomErr(false);
        WebUtils.doPost(request);
        Utils.showProgressDialog(this);
    }

    @Override
    public void doCancel(View v) {

    }

    private class MyBroadcastReciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            if (action.equals("cn.abel.action.broadcast")) {
                weiPayCode = intent.getIntExtra("errCode", -4);
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReciver);
    }
}
