package com.bluemobi.ybb.ps.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.base.utils.Base64;
import com.bluemobi.base.utils.StringUtils;
import com.bluemobi.base.utils.Utils;
import com.bluemobi.base.utils.WebUtils;
import com.bluemobi.ybb.ps.R;
import com.bluemobi.ybb.ps.app.TitleBarManager;
import com.bluemobi.ybb.ps.app.YbbPsApplication;
import com.bluemobi.ybb.ps.base.BaseActivity;
import com.bluemobi.ybb.ps.network.request.RefundMealReasonRequest;
import com.bluemobi.ybb.ps.network.response.RefundMealReasonResponse;
import com.bluemobi.ybb.ps.view.LoadingPage;
import com.bluemobi.ybb.ps.view.SelectPicPopupWindow;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gaoyn on 2015/7/9.
 *
 * p23-1 退餐原因
 */
public class RefundMealReasonActivity extends BaseActivity implements View.OnClickListener{

    private Button next_step;
    private EditText content;
    private String  id;

    public SelectPicPopupWindow pw;
    public ImageView add_image1,add_image2,add_image3,add_image4,add_image5;
    private  int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(RefundMealReasonActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.refund_meal_reason, R.drawable.common_back, true);

        showLoadingPage(false);
    }

    @Override
    protected void initBaseData() {
        id=getIntent().getStringExtra("id");
        pos=getIntent().getIntExtra("pos",0);

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.activity_refund_meal_reason,null);

         add_image1 = (ImageView)view.findViewById(R.id.add_image1);
        add_image1.setOnClickListener(this);
         add_image2 = (ImageView)view.findViewById(R.id.add_image2);
        add_image2.setOnClickListener(this);
         add_image3 = (ImageView)view.findViewById(R.id.add_image3);
        add_image3.setOnClickListener(this);
         add_image4 = (ImageView)view.findViewById(R.id.add_image4);
        add_image4.setOnClickListener(this);
         add_image5 = (ImageView)view.findViewById(R.id.add_image5);
        add_image5.setOnClickListener(this);

        next_step = (Button)view.findViewById(R.id.next_step);
        next_step.setOnClickListener(this);

        content = (EditText)view.findViewById(R.id.content);

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
        switch(v.getId()){
            case R.id.next_step:
                Request();
                break;
            case R.id.add_image1:
                pw = new SelectPicPopupWindow(this, new PicOnClickListener(1,add_image1));
                pw.showAtLocation(this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.add_image2:
                pw = new SelectPicPopupWindow(this, new PicOnClickListener(2,add_image2));
                pw.showAtLocation(this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.add_image3:
                pw = new SelectPicPopupWindow(this, new PicOnClickListener(3,add_image3));
                pw.showAtLocation(this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.add_image4:
                pw = new SelectPicPopupWindow(this, new PicOnClickListener(4,add_image4));
                pw.showAtLocation(this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.add_image5:
                pw = new SelectPicPopupWindow(this, new PicOnClickListener(5,add_image5));
                pw.showAtLocation(this.findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
        }
    }

    private void Request() {

        if(StringUtils.isEmpty(content.getText().toString())){
            Toast.makeText(mContext,"提交内容不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        RefundMealReasonRequest request = new RefundMealReasonRequest(new Response.Listener<RefundMealReasonResponse>() {
            @Override
            public void onResponse(RefundMealReasonResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    Toast.makeText(RefundMealReasonActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    intent.putExtra("pos", pos);
                    setResult(201, intent);
                    finish();
                } else {// failed
                    Log.e("error", "error");
                }
            }
        },this);
        String str1="";
        String str2="";
        String str3="";
        String str4="";
        String str5="";
        ArrayList<String> list= new ArrayList<String>();
        if(getPictureStatus(1)==1){
            Bitmap image1 = ((BitmapDrawable) add_image1.getDrawable()).getBitmap();
             str1=Base64.encodeBytes(Utils.Bitmap2Bytes(image1));
        }
        if(getPictureStatus(2)==1){
            Bitmap image2 = ((BitmapDrawable) add_image2.getDrawable()).getBitmap();
            str2=Base64.encodeBytes(Utils.Bitmap2Bytes(image2));
        }
        if(getPictureStatus(3)==1){
            Bitmap image3 = ((BitmapDrawable) add_image3.getDrawable()).getBitmap();
             str3=Base64.encodeBytes(Utils.Bitmap2Bytes(image3));
        }
        if(getPictureStatus(4)==1){
            Bitmap image4 = ((BitmapDrawable) add_image4.getDrawable()).getBitmap();
            str4=Base64.encodeBytes(Utils.Bitmap2Bytes(image4));
        }
        if(getPictureStatus(5)==1){
            Bitmap image5 = ((BitmapDrawable) add_image5.getDrawable()).getBitmap();
            str5=Base64.encodeBytes(Utils.Bitmap2Bytes(image5));
        }
        if(StringUtils.isNotEmpty(str1)){
            list.add(str1);
        }
        if(StringUtils.isNotEmpty(str2)){
            list.add(str2);
        }
        if(StringUtils.isNotEmpty(str3)){
            list.add(str3);
        }
        if(StringUtils.isNotEmpty(str4)){
            list.add(str4);
        }
        if(StringUtils.isNotEmpty(str5)){
            list.add(str5);
        }
        if (list.size()>0){
            request.setImgList(list);//"imgPathList":["图片base64码","图片base64码"]
        }
        request.setId(id);
        request.setBackReason(content.getText().toString());
        Utils.showProgressDialog(this);
        WebUtils.doPost(request);
    }

    private class PicOnClickListener implements View.OnClickListener {
        private int n;
        private ImageView view;
        PicOnClickListener(int number,ImageView image) {
            this.n = number;
            this.view=image;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            pw.dismiss();
            switch (v.getId()) {
                case R.id.btn_take_photo:
                    takePhoto(n, view);
                    break;
                case R.id.btn_pick_photo:
                    selectPic(n, view);
                    break;
                default:
                    break;
            }

        }
    }
}
