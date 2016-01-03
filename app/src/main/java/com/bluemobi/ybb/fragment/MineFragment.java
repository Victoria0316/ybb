package com.bluemobi.ybb.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.activity.BasicInformationActivity;
import com.bluemobi.ybb.activity.HomeActivity;
import com.bluemobi.ybb.activity.IntegralActivity;
import com.bluemobi.ybb.activity.MineOrderActivcity;
import com.bluemobi.ybb.activity.ModificationAddressMedicalActivity;
import com.bluemobi.ybb.activity.ModificationAddressPatientActivity;
import com.bluemobi.ybb.activity.MyAccountActivity;
import com.bluemobi.ybb.activity.MyAlarmActivity;
import com.bluemobi.ybb.activity.NewMessageNoticeActivity;
import com.bluemobi.ybb.activity.SetUpActivity;
import com.bluemobi.ybb.activity.ShippingAddressActivity;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.model.MyInfo;
import com.bluemobi.ybb.network.request.MyInfoRequest;
import com.bluemobi.ybb.network.response.MyInfoResponse;
import com.bluemobi.ybb.util.GlideCircleTransform;
import com.bluemobi.ybb.util.StringUtils;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.LoadingPage;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

/**
 * Created by liufy on 2015/6/29.
 * 我的
 */
public class MineFragment extends BaseFragment implements  View.OnClickListener{

    private  RelativeLayout  rl_mine_account;
    private RelativeLayout  rl_mine_order;
    private RelativeLayout  rl_mine_card;
    private RelativeLayout  rl_mine_diy;
    private RelativeLayout  rl_mine_address;
    private RelativeLayout  rl_mine_notice;
    //private RelativeLayout  rl_mine_setting;

    private RelativeLayout  collect_products;
    private RelativeLayout  collection_goods;
    private RelativeLayout  collection_consulting;

    private ImageView iv_photo_bg;

    private HomeActivity mActivity;

    private    TextView iv_name,tv_collect_meals,tv_collect_goods,tv_collect_info,tv_integrate;
    private  MyInfo  mMyInfo;
    MyInfoRequest request;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (HomeActivity) activity;
    }


    @Override
    public View createSuccessView(LayoutInflater inflater)
    {
        View mineView = inflater.inflate(R.layout.fragment_mine,null);
        rl_mine_order = (RelativeLayout) mineView.findViewById(R.id.rl_mine_order);
        rl_mine_account = (RelativeLayout) mineView.findViewById(R.id.rl_mine_account);
        rl_mine_card = (RelativeLayout) mineView.findViewById(R.id.rl_mine_card);
        rl_mine_diy = (RelativeLayout) mineView.findViewById(R.id.rl_mine_diy);
        rl_mine_address = (RelativeLayout) mineView.findViewById(R.id.rl_mine_address);
        rl_mine_notice = (RelativeLayout) mineView.findViewById(R.id.rl_mine_notice);
        //rl_mine_setting = (RelativeLayout) mineView.findViewById(R.id.rl_mine_setting);
        iv_photo_bg = (ImageView)mineView.findViewById(R.id.iv_photo_bg);
        collect_products = (RelativeLayout) mineView.findViewById(R.id.collect_products);
        collection_goods = (RelativeLayout) mineView.findViewById(R.id.collection_goods);
        collection_consulting = (RelativeLayout) mineView.findViewById(R.id.collection_consulting);
        rl_mine_order.setOnClickListener(this);
        rl_mine_account.setOnClickListener(this);
        rl_mine_card.setOnClickListener(this);
        rl_mine_diy.setOnClickListener(this);
        rl_mine_address.setOnClickListener(this);
        rl_mine_notice.setOnClickListener(this);
        //rl_mine_setting.setOnClickListener(this);
        iv_photo_bg.setOnClickListener(this);
        collect_products.setOnClickListener(this);
        collection_goods.setOnClickListener(this);
        collection_consulting.setOnClickListener(this);
        iv_name=(TextView)mineView.findViewById(R.id.iv_name);
        tv_collect_meals=(TextView)mineView.findViewById(R.id.tv_collect_meals);
        tv_collect_goods=(TextView)mineView.findViewById(R.id.tv_collect_goods);
        tv_collect_info=(TextView)mineView.findViewById(R.id.tv_collect_info);
        tv_integrate=(TextView)mineView.findViewById(R.id.tv_integrate);
        tv_integrate.setOnClickListener(this);
        return mineView;
    }

    @Override
    protected YbbHttpJsonRequest initRequest() {
        MyInfoRequest request = new MyInfoRequest(new Response.Listener<MyInfoResponse>() {
            @Override
            public void onResponse(MyInfoResponse response) {
                if (response != null && response.getStatus() == 0) {// success
                    mMyInfo=response.getData();
                    if (mMyInfo!=null){
                        RequestManager glideRequest;
                        glideRequest = Glide.with(mActivity);
                        if (StringUtils.isNotEmpty(mMyInfo.getHeadPicUrl())){

                            glideRequest.load(mMyInfo.getHeadPicUrl()).transform(new GlideCircleTransform(mActivity)).into(iv_photo_bg);
                        }

                        iv_name.setText(mMyInfo.getNickName());
                        tv_collect_meals.setText(mMyInfo.getCollMealCount());
                        tv_collect_goods.setText(mMyInfo.getCollGoodCount());
                        tv_collect_info.setText(mMyInfo.getCollConsultingCount());
                        tv_integrate.setText(mMyInfo.getAvailableAmount()+"积分");
                    }
                }
            }
        }, mActivity);
        request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
        this.request = request;
        return request;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        isShowLoadPage = false;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.rl_mine_order:
                Utils.moveTo(mContext, MineOrderActivcity.class);
                break;
            case R.id.rl_mine_account:
                Utils.moveTo(mContext, MyAccountActivity.class);
                break;

            case R.id.rl_mine_card:
                Toast.makeText(mContext,"二期开发",Toast.LENGTH_SHORT).show();
                break;

            case R.id.rl_mine_diy:
                Utils.moveTo(mContext, MyAlarmActivity.class);
                break;

            case R.id.rl_mine_address:
                Utils.moveTo(mContext, ShippingAddressActivity.class);
                break;

            case R.id.rl_mine_notice:
                Utils.moveTo(mContext, NewMessageNoticeActivity.class);
                break;

            /*case R.id.rl_mine_setting:
                Utils.moveTo(mContext, SetUpActivity.class);
                break;*/

            case R.id.iv_photo_bg:
                Intent i=new Intent(mContext, BasicInformationActivity.class);
                startActivityForResult(i,REQUEST_CODE_CHANGE);
                break;

            case R.id.tv_integrate:
                Intent intent = new Intent();
                intent.putExtra("integrate",mMyInfo.getAvailableAmount());
                intent.setClass(mContext, IntegralActivity.class);
                startActivityForResult(intent, 200);
                break;
            case R.id.collect_products:
                Utils.makeToastAndShow(mContext,"二期开发，敬请期待");
                break;
            case R.id.collection_goods:
                Utils.makeToastAndShow(mContext,"二期开发，敬请期待");
                break;
            case R.id.collection_consulting:
                Utils.makeToastAndShow(mContext,"二期开发，敬请期待");
                break;
        }
    }
    protected static final int REQUEST_CODE_CHANGE = 150;
    /**
     * Activity回调函数
     * */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHANGE   && resultCode == 10) {
            if (data!=null && "1".equals(data.getStringExtra("change"))){
                MyInfoRequest request = new MyInfoRequest(new Response.Listener<MyInfoResponse>() {
                    @Override
                    public void onResponse(MyInfoResponse response) {
                        Utils.closeDialog();
                        if (response != null && response.getStatus() == 0) {// success
                            mMyInfo=response.getData();
                            if (mMyInfo!=null){
                                RequestManager glideRequest;
                                glideRequest = Glide.with(mActivity);
                                glideRequest.load(mMyInfo.getHeadPicUrl()).transform(new GlideCircleTransform(mActivity)).into(iv_photo_bg);

                                iv_name.setText(mMyInfo.getNickName());
                                tv_collect_meals.setText(mMyInfo.getCollMealCount());
                                tv_collect_goods.setText(mMyInfo.getCollGoodCount());
                                tv_collect_info.setText(mMyInfo.getCollConsultingCount());
                                tv_integrate.setText(mMyInfo.getAvailableAmount()+"积分");
                            }
                        }
                    }
                }, mActivity);
                request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
                Utils.showProgressDialog(mActivity);
                WebUtils.doPost(request);
            }
        }
    }


}