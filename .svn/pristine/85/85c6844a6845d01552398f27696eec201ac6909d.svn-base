package com.bluemobi.ybb.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.adapter.CommonAdapter;
import com.bluemobi.ybb.adapter.ViewHolder;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.model.AccountBillInfo;
import com.bluemobi.ybb.network.model.MyAccountBean;
import com.bluemobi.ybb.network.request.AccountBillRequest;
import com.bluemobi.ybb.network.request.MyAccountRequest;
import com.bluemobi.ybb.network.response.AccountBillResponse;
import com.bluemobi.ybb.network.response.MyAccountResponse;
import com.bluemobi.ybb.util.Constants;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.LoadingPage;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的账户页面
 * Created by wangzhijun on 2015/7/10.
 */
public class MyAccountActivity extends BaseActivity implements View.OnClickListener {

    private ListView listView;

    private CommonAdapter<AccountBillInfo> adapter;
    private List<AccountBillInfo> info = new ArrayList<AccountBillInfo>();

    private MyAccountBean data;

    private static final int TRANSFER_IN = 0;

    private static final int TRANSFER_OUT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLoadingPage(false);
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.my_account_title, R.drawable.common_back, true);
    }

    @Override
    protected void initBaseData() {

    }

    @Override
    protected YbbHttpJsonRequest initRequest() {

        MyAccountRequest request = new MyAccountRequest(new Response.Listener<MyAccountResponse>() {
            @Override
            public void onResponse(MyAccountResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success

                    data = response.getData();
                    TextView account_balance_value = (TextView) findViewById(R.id.account_balance_value);
                    account_balance_value.setText("¥ " + data.getBalanceAmount());
                    TextView account_pay_limit_value = (TextView) findViewById(R.id.account_pay_limit_value);
                    account_pay_limit_value.setText("¥ " + data.getAccountLimit());

                } else {// failed
                    Log.e("error", "error");
                }
            }
        }, MyAccountActivity.this);
        request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setHandleCustomErr(false);
        Utils.showProgressDialog(this);
        return request;
    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.activity_my_account, null);
        listView = (ListView) view.findViewById(R.id.account_his_list);
        listView.setCacheColorHint(getResources().getColor(R.color.transparent));
        listView.setDivider(new ColorDrawable(Color.rgb(229, 229, 229)));
        listView.setDividerHeight(1);
        listView.setSelector(R.color.transparent);

        view.findViewById(R.id.set_pwd).setOnClickListener(this);
        view.findViewById(R.id.set_limit).setOnClickListener(this);
        view.findViewById(R.id.account_out).setOnClickListener(this);
        view.findViewById(R.id.account_in).setOnClickListener(this);

        AccountRequest();

        return view;
    }

    private void AccountRequest() {
        AccountBillRequest request = new AccountBillRequest(new Response.Listener<AccountBillResponse>() {
            @Override
            public void onResponse(AccountBillResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    info.clear();
                    info.addAll(response.getData().getInfo());
                    adapter = new CommonAdapter<AccountBillInfo>(mContext,
                            info, R.layout.adapter_account_his) {
                        @Override
                        public void convert(ViewHolder helper, AccountBillInfo item) {
                            TextView time = helper.getView(R.id.time);
                            TextView detail = helper.getView(R.id.detail);
                            time.setText(item.getRechargeTime());
                            StringBuffer buffer = new StringBuffer();
                            buffer.append(getChannel(item.getRechargeChannel()));//渠道
                            buffer.append(getType(item.getRechargeType()));//充值类型
                            buffer.append(item.getRechargeAmount());//金额
                            detail.setText(buffer.toString());
                        }
                    };
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {// failed
                    Log.e("error", "error");
                }
            }
        }, MyAccountActivity.this);
        request.setHandleCustomErr(false);
        request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setCurrentnum(Constants.CURRENTNUMBASE + "");
        request.setCurrentpage(0 + "");
        request.setHandleCustomErr(false);
        Utils.showProgressDialog(this);
        WebUtils.doPost(request);
    }

    /**
     * 1注册充值，2充值，3补欠，4转出
     *
     * @param rechargeType
     * @return
     */
    private String getType(String rechargeType) {
        if ("1".equals(rechargeType)) {
            return "注册充值";
        } else if ("2".equals(rechargeType)) {
            return "充值";
        } else if ("3".equals(rechargeType)) {
            return "补欠";
        } else if ("4".equals(rechargeType)) {
            return "转出";
        }
        return "";
    }

    /**
     * 1现金，2银行卡转账，3支付宝，4微信，5银联
     *
     * @param rechargeChannel
     * @return
     */
    private String getChannel(String rechargeChannel) {
        if ("1".equals(rechargeChannel)) {
            return "现金";
        } else if ("2".equals(rechargeChannel)) {
            return "银行卡转账";
        } else if ("3".equals(rechargeChannel)) {
            return "支付宝";
        } else if ("4".equals(rechargeChannel)) {
            return "微信";
        } else if ("5".equals(rechargeChannel)) {
            return "银联";
        }
        return "";
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();

        switch (v.getId()) {
            case R.id.set_pwd:
                Utils.moveTo(this, PayPwdSetActivity.class);
                break;
            case R.id.set_limit:
                Utils.moveTo(this, PayLimitSetActivity.class);
                break;
            case R.id.account_out://出
                Utils.moveTo(this, AccountOutActivity.class);
                break;
            case R.id.account_in://入
//                Utils.moveTo(this, AccountInActivity.class);
                intent.setClass(this, AccountInActivity.class);
                startActivityForResult(intent, TRANSFER_IN);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case TRANSFER_IN:
                    AccountRequest();
                    break;
                case TRANSFER_OUT:
                    break;
            }
        }
    }
}
