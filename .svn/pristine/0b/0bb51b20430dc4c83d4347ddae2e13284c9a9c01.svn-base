package com.bluemobi.ybb.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.network.model.AddressModel;
import com.bluemobi.ybb.network.request.ModificationAddressRequest;
import com.bluemobi.ybb.network.response.ModificationAddressResponse;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.LoadingPage;

import org.w3c.dom.Text;

/**
 * Created by gaoyn on 2015/7/6.
 * <p/>
 * P2-4医护-填写地址
 */
public class ModificationAddressMedicalActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout address_beds;
    private RelativeLayout hospitalRl;
    private RelativeLayout departmentRl;
    private TextView username;
    private TextView phone;

    private AddressModel hospital;
    private AddressModel department;
    private AddressModel bed;

    private TextView addressAll;

    private String addressString;

    private static final int REQUEST_HOSPITAL = 1;
    private static final int REQUEST_DEPARTMENT = 2;
    private static final int REQUEST_BED = 3;

    private String address;

    private TextView hospital_tv;  //显示选中的  医院
    private TextView department_tv;//显示选中的  科室


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(ModificationAddressMedicalActivity.this, getSupportActionBar());
        //titleBarManager.showCommonTitleBar(R.string.modification_address, R.drawable.common_back, true);
        titleBarManager.showTitleTextBar(R.string.modification_address, R.drawable.common_back, R.string.submit);
        address=getIntent().getStringExtra("address");
        showLoadingPage(false);
    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.activity_modification_address_manual, null);

        address_beds = (RelativeLayout) view.findViewById(R.id.address_beds);
        hospitalRl = (RelativeLayout) view.findViewById(R.id.hospital_rl);
        departmentRl = (RelativeLayout) view.findViewById(R.id.department_rl);
        addressAll = (TextView) view.findViewById(R.id.address_all);
        addressAll.setText(address);
        username = (TextView) view.findViewById(R.id.username);
        phone = (TextView) view.findViewById(R.id.phone);

        hospital_tv = (TextView) view.findViewById(R.id.hospital_tv);
        department_tv = (TextView) view.findViewById(R.id.department_tv);

        username.setText(YbbApplication.getInstance().getMyUserInfo().getNickName());
        phone.setText(YbbApplication.getInstance().getUserPhone());

        address_beds.setVisibility(View.GONE);
        hospitalRl.setOnClickListener(this);
        departmentRl.setOnClickListener(this);
        address_beds.setOnClickListener(this);


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
        if(hospital ==null){
            Utils.makeToastAndShow(getBaseContext(), "请先选择医院");
            return;
        }
        if(department == null){
            Utils.makeToastAndShow(getBaseContext(), "请先选择科室");
            return;
        }
        Request();
    }

    private void Request() {

        ModificationAddressRequest request = new ModificationAddressRequest(new Response.Listener<ModificationAddressResponse>() {
            @Override
            public void onResponse(ModificationAddressResponse response) {

                if (response != null && response.getStatus() == 0) {// success

                    Utils.moveTo(mContext, HomeActivity.class);

                } else {// failed
                    Log.e("error", "error");
                }
            }
        },this);
        request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setUsername(YbbApplication.getInstance().getMyUserInfo().getNickName());
        request.setHospitalId(hospital.getId());
        request.setHospitalName(hospital.getHospitalName());
        request.setDepartmentId(department.getId());
        request.setDepartmentName(department.getDepartmentName());
        if(bed != null){
            request.setBedId(bed.getId());
            request.setBedName(bed.getBedName());
        }
        request.setAddress(addressString);
        request.setCellPhone(YbbApplication.getInstance().getMyUserInfo().getUserName());
        Utils.showProgressDialog(this);
        WebUtils.doPost(request);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hospital_rl:
                doSelectHospital();
                break;
            case R.id.department_rl:
                doSelectDepartment();
                break;
            case R.id.address_beds:
                doSelectBeds();
                break;

        }
    }

    private void doSelectBeds() {
        if(hospital == null){
            Utils.makeToastAndShow(getBaseContext(), "请先选择医院");
            return;
        }
        if(department == null){
            Utils.makeToastAndShow(getBaseContext(), "请先选择科室");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("category", String.valueOf(REQUEST_BED));
        intent.putExtra("hospitalId", hospital.getId());
        intent.putExtra("departmentId", department.getId());
        intent.setClass(this, AddressListActivity.class);

        startActivityForResult(intent, REQUEST_BED);
    }

    private void doSelectDepartment() {
        if(hospital == null){
            Utils.makeToastAndShow(getBaseContext(), "请先选择医院");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("category", String.valueOf(REQUEST_DEPARTMENT));
        intent.putExtra("hospitalId", hospital.getId());
        intent.setClass(this, AddressListActivity.class);
        startActivityForResult(intent, REQUEST_DEPARTMENT);
    }

    private void doSelectHospital() {

        Intent intent = new Intent();
        intent.putExtra("category", String.valueOf(REQUEST_HOSPITAL));
        intent.setClass(this, AddressListActivity.class);
        startActivityForResult(intent, REQUEST_HOSPITAL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
        if(resultCode == Activity.RESULT_OK){
            Bundle bundle = result.getExtras();
            AddressModel model = (AddressModel)bundle.getSerializable("item");
//                    Utils.makeToastAndShow(getBaseContext(), model.getHospitalId() + "" + model.getId());
            switch (requestCode){
                case REQUEST_HOSPITAL:
                    hospital = model;
                    addressString = hospital.getHospitalName();
                    department = null;
                    bed = null;
                    hospital_tv.setText(hospital.getHospitalName());
                    break;
                case REQUEST_DEPARTMENT:
                    department = model;
                    addressString = hospital.getHospitalName() + department.getDepartmentName();
                    bed = null;
                    department_tv.setText(department.getDepartmentName());
                    break;
                case REQUEST_BED:
//                    bed = model;
//                    addressString += bed.getBedName();
//                    address_tv.setText(bed.getBedName());
                    break;
            }
            addressAll.setText(addressString);
        }
    }
}
