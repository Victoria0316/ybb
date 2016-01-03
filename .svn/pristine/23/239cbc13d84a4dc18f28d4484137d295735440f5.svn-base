package com.bluemobi.ybb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.bluemobi.ybb.network.model.ShippingAddressBean;
import com.bluemobi.ybb.network.request.ShippingAddressRequest;
import com.bluemobi.ybb.network.response.ShippingAddressResponse;
import com.bluemobi.ybb.util.StringUtils;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.LoadingPage;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoyn on 2015/7/7.
 * <p/>
 * p14-19 收货地址管理
 */
public class ShippingAddressActivity extends BaseActivity implements View.OnClickListener {

    private final static int SCANNIN_GREQUEST_CODE = 1;

    private RelativeLayout modificationAddress;

    private ShippingAddressBean data;

    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(ShippingAddressActivity.this, getSupportActionBar());
        titleBarManager.showTitleTextBar(R.string.shipping_address, R.drawable.common_back, R.string.scanning);

        showLoadingPage(true);


    }

    @Override
    protected YbbHttpJsonRequest initRequest() {
        ShippingAddressRequest request = new ShippingAddressRequest(new Response.Listener<ShippingAddressResponse>() {
            @Override
            public void onResponse(ShippingAddressResponse response) {

                if (response != null && response.getStatus() == 0) {
                    Utils.closeDialog();
                    // success
                    data = response.getData();

                    TextView nickname = (TextView) findViewById(R.id.nickname);
                    TextView telephone = (TextView) findViewById(R.id.telephone);
                    TextView address_detailed = (TextView) findViewById(R.id.address_detailed);
                    nickname.setText(YbbApplication.getInstance().getMyUserInfo().getNickName() + " :");
                    telephone.setText(YbbApplication.getInstance().getMyUserInfo().getUserName());
                    StringBuffer bf = new StringBuffer();
                    if(StringUtils.isNotEmpty(data.getProvinceName())){
                        bf.append(data.getProvinceName());
                        bf.append(" ");
                    }
                    if(StringUtils.isNotEmpty(data.getCityName())){
                        bf.append(data.getCityName());
                        bf.append(" ");
                    }
                    if(StringUtils.isNotEmpty(data.getDistrictName())){
                        bf.append(data.getDistrictName());
                    }
                    address = bf.toString();
                    address_detailed.setText(address);
                } else {// failed
                    Log.e("error", "error");
                }
            }
        }, this);
        request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
        Utils.showProgressDialog(this);
        return request;
    }

    private void request() {
        ShippingAddressRequest request = new ShippingAddressRequest(new Response.Listener<ShippingAddressResponse>() {
            @Override
            public void onResponse(ShippingAddressResponse response) {

                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {
                    Logger.d("gzq","成功");
                    // success
                    data = response.getData();

                    TextView nickname = (TextView) findViewById(R.id.nickname);
                    TextView telephone = (TextView) findViewById(R.id.telephone);
                    TextView address_detailed = (TextView) findViewById(R.id.address_detailed);
                    nickname.setText(YbbApplication.getInstance().getMyUserInfo().getNickName() + " :");
                    telephone.setText(YbbApplication.getInstance().getMyUserInfo().getUserName());
                    address = data.getAddress();
                    address_detailed.setText(address);
                } else {// failed
                    Logger.d("gzq","失败");
                    Log.e("error", "error");
                }
            }
        }, this);
        request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
        Utils.showProgressDialog(this);

        WebUtils.doPost(request);
    }


    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {


        View view = inflater.inflate(R.layout.activity_shipping_address, null);

        modificationAddress = (RelativeLayout) view.findViewById(R.id.modificationAddress);
        modificationAddress.setOnClickListener(this);

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
    public void clickBarRight() {
        //跳到扫描二维码
        Intent intent = new Intent();
        intent.setClass(this, MipcaActivityCapture.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, SCANNIN_GREQUEST_CODE);

//        Utils.moveTo(this, MipcaActivityCapture.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();

                    if ("ok".equals(bundle.getString("ok"))) {
                    //更新数据，请求服务器
                        request();
                    }

                }
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.modificationAddress:
//                Utils.moveTo(ShippingAddressActivity.this, ModificationAddressMedicalActivity.class);
                Intent intent = new Intent();
                intent.putExtra("from", "login_register");
                intent.putExtra("address", address);
                if (YbbApplication.getInstance().role_bh.equals(
                        YbbApplication.getInstance().getMyUserInfo().getTypeId()
                )) {//病患
                    intent.setClass(this, ModificationAddressPatientActivity.class);
                    //病患
                }
                if (YbbApplication.getInstance().role_yh.equals(
                        YbbApplication.getInstance().getMyUserInfo().getTypeId())) {
                    intent.setClass(this, ModificationAddressMedicalActivity.class);
                    //医护
                }
                startActivity(intent);
                break;
        }
    }
}
