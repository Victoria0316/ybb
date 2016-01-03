package com.bluemobi.ybb.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.adapter.CommonAdapter;
import com.bluemobi.ybb.adapter.ViewHolder;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.model.IntegralBillInfo;
import com.bluemobi.ybb.network.model.IntegralExchangeBean;
import com.bluemobi.ybb.network.model.IntegralExchangeInfo;
import com.bluemobi.ybb.network.request.IntegralBillRequest;
import com.bluemobi.ybb.network.request.IntegralExchangeRequest;
import com.bluemobi.ybb.network.response.IntegralBillResponse;
import com.bluemobi.ybb.network.response.IntegralExchangeResponse;
import com.bluemobi.ybb.util.Constants;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.LoadingPage;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liufy on 2015/7/14.
 * Integral 我的积分
 */
public class IntegralActivity extends BaseActivity implements View.OnClickListener {

    private TitleBarManager titleBarManager;

    //private  RelativeLayout rl_integral_detail;

    private RelativeLayout rl_redemption;

    private CommonAdapter<IntegralExchangeInfo> adapter;

    private CommonAdapter<IntegralBillInfo> adapterBill;

    private ListView lv_list_item;

    private ListView lv_detail;

    private boolean flag = false;

    private List<IntegralExchangeInfo> info;

    private List<IntegralBillInfo> infoBill;

    private int startHeight;
    private int targetHeight;

    private String integrate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        titleBarManager = new TitleBarManager();
        titleBarManager.init(IntegralActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.str_my_integral, R.drawable.common_back, true);

        integrate = getIntent().getStringExtra("integrate");
        showLoadingPage(false);
    }

    @Override
    protected void initBaseData() {

    }

    @Override
    protected YbbHttpJsonRequest initRequest() {

        IntegralExchangeRequest request;
        request = new IntegralExchangeRequest
                (
                        new Response.Listener<IntegralExchangeResponse>() {
                            @Override
                            public void onResponse(IntegralExchangeResponse response) {
                                Utils.closeDialog();
                                if (response != null && response.getStatus() == 0) {
                                    info = response.getData().getInfo();
                                    lv_list_item.setAdapter(
                                            adapter = new CommonAdapter<IntegralExchangeInfo>(mContext,
                                                    info, R.layout.lv_integral_item) {
                                                @Override
                                                public void convert(ViewHolder helper, IntegralExchangeInfo item) {
                                                    TextView tv_search_item = helper.getView(R.id.tv_search_item);
                                                    RatingBar rb_comment = helper.getView(R.id.rb_comment);
                                                    TextView rb_comment_text = helper.getView(R.id.rb_comment_text);
                                                    TextView tv_charge = helper.getView(R.id.tv_charge);
                                                    tv_search_item.setText(item.getProductName());
                                                    rb_comment.setVisibility(View.INVISIBLE);
                                                    rb_comment_text.setVisibility(View.INVISIBLE);
                                                    tv_charge.setText("积分：" + item.getIntegralQuantity());
                                                    TextView tv_num=helper.getView(R.id.tv_num);
                                                    tv_num.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            Toast.makeText(mContext,"敬请期待",Toast.LENGTH_SHORT).show();
                                                        }
                                                    });


                                                }
                                            });
                                } else {
                                    Toast.makeText(mContext, response.getContent(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, this);
        request.setCurrentnum(Constants.CURRENTNUMBASE + "");
        request.setCurrentpage(0 + "");
        return request;
    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {

        View integralView = inflater.inflate(R.layout.activity_integral, null);
        // rl_integral_detail = (RelativeLayout) integralView.findViewById(R.id.rl_integral_detail);
        rl_redemption = (RelativeLayout) integralView.findViewById(R.id.rl_redemption);
        rl_redemption.setOnClickListener(this);
        lv_detail = (ListView) integralView.findViewById(R.id.lv_detail);
        lv_list_item = (ListView) integralView.findViewById(R.id.lv_list_item);
        TextView inte = (TextView)integralView.findViewById(R.id.integrate);
        inte.setText("积分："+integrate);

        LayoutParams layoutParams = lv_detail.getLayoutParams();
        layoutParams.height = 0;
        lv_detail.setLayoutParams(layoutParams);
        return integralView;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_redemption:

                int startHeight;
                int targetHeight;
                if (!flag) {
                    Request();
                } else {
                    flag = false;
                    startHeight = getMeasureHeight();
                    targetHeight = 0;
                    ValueAnimator animator = ValueAnimator.ofInt(startHeight, targetHeight);
                    final RelativeLayout.LayoutParams layoutParams = (android.widget.RelativeLayout.LayoutParams) lv_detail.getLayoutParams();
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {  // 监听值的变化

                        @Override
                        public void onAnimationUpdate(ValueAnimator animator) {
                            int value = (Integer) animator.getAnimatedValue();
                            layoutParams.height = value;
                            lv_detail.setLayoutParams(layoutParams);
                            System.out.println(value);
                        }
                    });
                    animator.setDuration(300);
                    animator.start();
                }



                break;

        }
    }

    private void animate(){
        if (!flag) {
            startHeight = 0;
            targetHeight = getMeasureHeight();

            flag = true;
            lv_detail.getMeasuredHeight();  //  0

        } else {
            flag = false;
            startHeight = getMeasureHeight();
            targetHeight = 0;
        }
        ValueAnimator animator = ValueAnimator.ofInt(startHeight, targetHeight);
        final RelativeLayout.LayoutParams layoutParams = (android.widget.RelativeLayout.LayoutParams) lv_detail.getLayoutParams();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {  // 监听值的变化

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int value = (Integer) animator.getAnimatedValue();
                layoutParams.height = value;
                lv_detail.setLayoutParams(layoutParams);
                System.out.println(value);
            }
        });


        animator.setDuration(300);
        animator.start();
    }

    private void Request() {
        final IntegralBillRequest request;
        request = new IntegralBillRequest
                (
                        new Response.Listener<IntegralBillResponse>() {
                            @Override
                            public void onResponse(IntegralBillResponse response) {
                                Utils.closeDialog();
                                if (response != null && response.getStatus() == 0) {
                                    infoBill = response.getData().getInfo();
                                    lv_detail.setAdapter(
                                            adapterBill = new CommonAdapter<IntegralBillInfo>(mContext,
                                                    infoBill, R.layout.lv_integral_water) {

                                                @Override
                                                public void convert(ViewHolder helper, IntegralBillInfo item) {
                                                    TextView reason = helper.getView(R.id.reason);
                                                    TextView optTime = helper.getView(R.id.optTime);
                                                    reason.setText(item.getReason()+item.getAvailableValue()+"积分");
                                                    optTime.setText(item.getCreateTime());
                                                }
                                            });
                                    animate();
                                } else {
                                    Toast.makeText(mContext, response.getContent(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, this);
        request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setCurrentnum(Constants.CURRENTNUMBASE + "");
        request.setCurrentpage(0 + "");
        WebUtils.doPost(request);
    }

    public int getMeasureHeight() {
        int width = lv_detail.getMeasuredWidth();
        lv_detail.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(1000, MeasureSpec.AT_MOST);
        lv_detail.measure(widthMeasureSpec, heightMeasureSpec);
        return lv_detail.getMeasuredHeight();

    }
}
