package com.bluemobi.ybb.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.adapter.CommonAdapter;
import com.bluemobi.ybb.adapter.ViewHolder;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.network.model.AccountBillInfo;
import com.bluemobi.ybb.network.request.AccountBillRequest;
import com.bluemobi.ybb.network.request.TrunInorOutRequest;
import com.bluemobi.ybb.network.response.AccountBillResponse;
import com.bluemobi.ybb.network.response.TrunInorOutResponse;
import com.bluemobi.ybb.util.AlipayUtil;
import com.bluemobi.ybb.util.Constants;
import com.bluemobi.ybb.util.StringUtils;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.LoadingPage;
import com.bluemobi.ybb.view.PaymentDialog;

/**
 * Created by wangzhijun on 2015/7/11.
 * 转入
 */
public class AccountInActivity extends BaseActivity implements View.OnClickListener ,AlipayUtil.AlipayStausListener{

    private TextView amount;

    private int payType = 1;

    private  String  id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLoadingPage(false);
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.my_account_in, R.drawable.common_back, true);
    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.activity_account_in, null);
        view.findViewById(R.id.rl_wx).setOnClickListener(this);
        view.findViewById(R.id.rl_zfb).setOnClickListener(this);
        amount = (TextView)view.findViewById(R.id.amount);
        return view;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_wx:
                Utils.makeToastAndShow(getBaseContext(), "敬请期待");
                break;
               /* payType =1;
                if(StringUtils.isEmpty(amount.getText().toString())){
                    Utils.makeToastAndShow(getBaseContext(), "请输入金额");
                    return;
                }
                sendRequest("4");
                break;*/
            case R.id.rl_zfb:
                payType =2;
                if(StringUtils.isEmpty(amount.getText().toString())){
                    Utils.makeToastAndShow(getBaseContext(), "请输入金额");
                    return;
                }
                sendRequest("3");
                break;
        }
    }
    public void sendRequest(String type){
        final  PaymentDialog paymentDialog = new PaymentDialog();
        TrunInorOutRequest request = new TrunInorOutRequest(new Response.Listener<TrunInorOutResponse>() {
            @Override
            public void onResponse(TrunInorOutResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    id=response.getData().getValue();//订单
                    Log.e("idididididididid", id + "");
                    AlipayUtil pay= new AlipayUtil(AccountInActivity.this, AccountInActivity.this);
                    pay.pay("ebb","ebb transfer in",amount.getText().toString(),id,
                            Constants.TRANSFER_IN_CALL_BACK);


//                    pay.pay("ebb","ebb transfer in",amount.getText().toString(),id,
//                            Constants.CALL_BACK_);
//                    paymentDialog.show(getFragmentManager(), null);
                } else {// failed
                    Log.e("error", "error");
                }
            }
        }, AccountInActivity.this);
        request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setRechargeChannel(type);//支付宝
        request.setRechargeAmount(amount.getText().toString());
        request.setRechargeType("2");//充值
        Utils.showProgressDialog(this);
        WebUtils.doPost(request);

    }

    @Override
    public void checkResult(boolean isExist) {
        
    }

    @Override
    public void payOk() {
//        Toast.makeText(AccountInActivity.this, "转入成功", Toast.LENGTH_SHORT).show();
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void payFailed() {
        Toast.makeText(AccountInActivity.this, "转入失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void paying() {

    }
}
