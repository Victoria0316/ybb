package com.bluemobi.ybb.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.network.model.PersonalInfo;
import com.bluemobi.ybb.network.request.GetPersonalInfoRequest;
import com.bluemobi.ybb.network.request.OrderinfoRequest;
import com.bluemobi.ybb.network.request.UploadMyPicRequest;
import com.bluemobi.ybb.network.response.GetPersonalInfoResponse;
import com.bluemobi.ybb.network.response.OrderinfoResponse;
import com.bluemobi.ybb.network.response.UploadMyPicResponse;
import com.bluemobi.ybb.util.Base64;
import com.bluemobi.ybb.util.Constants;
import com.bluemobi.ybb.util.GlideCircleTransform;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.CircleImageView;
import com.bluemobi.ybb.view.LoadingPage;
import com.bluemobi.ybb.view.SelectPicPopupWindow;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

/**
 * Created by gaoyn on 2015/7/7.
 * <p/>
 * p14-16 基本信息
 */
public class BasicInformationActivity extends BaseActivity implements View.OnClickListener {
    private final static String tag = "BasicInformationActivity";


    private RelativeLayout nickname, pic_lay;

    private PersonalInfo personalinfo;

    private TextView nickname_text;

    private CircleImageView avatar;
    public SelectPicPopupWindow pw;
    private String isChanged = "0";//0没修改头像和名字 1修改了

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(BasicInformationActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.basic_information, R.drawable.common_back, true);
        showLoadingPage(false);
    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {

        GetPersonalInfoRequest();


        View view = inflater.inflate(R.layout.activity_basic_information, null);

        nickname = (RelativeLayout) view.findViewById(R.id.nickname);
        nickname.setOnClickListener(this);
        pic_lay = (RelativeLayout) view.findViewById(R.id.pic_lay);
        pic_lay.setOnClickListener(this);

        nickname_text = (TextView) view.findViewById(R.id.nickname_text);

        avatar = (CircleImageView) view.findViewById(R.id.avatar);

        return view;
    }



    private void GetPersonalInfoRequest() {

        GetPersonalInfoRequest request = new GetPersonalInfoRequest(new Response.Listener<GetPersonalInfoResponse>() {
            @Override
            public void onResponse(GetPersonalInfoResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    Logger.d(tag, "P14-16获取基本信息成功");

                    personalinfo = response.getData();

                    Logger.d(tag, personalinfo.getNickName());
                    Logger.d(tag, personalinfo.getHeadPicUrl());

                    nickname_text.setText(personalinfo.getNickName());
                    RequestManager glideRequest;
                    glideRequest = Glide.with(BasicInformationActivity.this);
                    glideRequest.load(personalinfo.getHeadPicUrl()).transform(new GlideCircleTransform(BasicInformationActivity.this)).into(avatar);


                }
            }
        }, BasicInformationActivity.this);

        request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
        Utils.showProgressDialog(this);
        WebUtils.doPost(request);
    }

    @Override
    protected LoadingPage.LoadResult load() {

        return LoadingPage.LoadResult.success;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nickname:
                Intent intent = new Intent();
                intent.putExtra("nickname", personalinfo.getNickName());
                intent.setClass(this, BasicInformationNicknameActivity.class);
                startActivityForResult(intent, 200);
                break;
            case R.id.pic_lay:
                pw = new SelectPicPopupWindow(this, new PicOnClickListener(1));
                pw.showAtLocation(this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
        }
    }

    private class PicOnClickListener implements View.OnClickListener {
        private int n;

        PicOnClickListener(int number) {
            this.n = number;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            pw.dismiss();
            switch (v.getId()) {
                case R.id.btn_take_photo:
                    takePhoto(n, avatar);
                    break;
                case R.id.btn_pick_photo:
                    selectPic(n, avatar);
                    break;
                default:
                    break;
            }

        }
    }

    public void doPicOK() {
        uploadHeadPic();
    }

    public void uploadHeadPic() {

        UploadMyPicRequest request = new UploadMyPicRequest(new Response.Listener<UploadMyPicResponse>() {
            @Override
            public void onResponse(UploadMyPicResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    Toast.makeText(mContext, "修改头像成功", Toast.LENGTH_SHORT).show();
                    isChanged = "1";
                } else {// failed
                    Toast.makeText(mContext, response == null ? "网络异常" : response.getContent(), Toast.LENGTH_SHORT).show();
                }
            }
        }, this);
        request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
        Bitmap image = ((BitmapDrawable) avatar.getDrawable()).getBitmap();
        request.setPicArray(Base64.encodeBytes(Utils.Bitmap2Bytes(image)));
        request.setPicType("jpg");
        Utils.showProgressDialog(BasicInformationActivity.this);
        WebUtils.doPost(request);
    }

    @Override
    public void clickBarleft() {
        Intent intent = getIntent();
        intent.putExtra("change", isChanged);
        setResult(10, intent);
        finish();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == 201) {
            if (data != null) {
                nickname_text.setText(data.getStringExtra("name"));
            }
            isChanged = "1";
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //按下的如果是BACK，同时没有重复
            Intent intent = getIntent();
            intent.putExtra("change", isChanged);
            setResult(10, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
