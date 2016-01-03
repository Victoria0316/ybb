package com.bluemobi.ybb.ps.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.base.utils.StringUtils;
import com.bluemobi.base.utils.Utils;
import com.bluemobi.ybb.ps.R;
import com.bluemobi.ybb.ps.app.DbManager;
import com.bluemobi.ybb.ps.app.TitleBarManager;
import com.bluemobi.ybb.ps.app.YbbPsApplication;
import com.bluemobi.ybb.ps.base.BaseActivity;
import com.bluemobi.ybb.ps.db.entity.PSMessage;
import com.bluemobi.ybb.ps.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.ps.network.model.MineBean;
import com.bluemobi.ybb.ps.network.request.MineRequest;
import com.bluemobi.ybb.ps.network.response.MineResponse;
import com.bluemobi.ybb.ps.view.LoadingPage;
import com.jauker.widget.BadgeView;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

/**
 * Created by liufy on 2015/7/15.
 * P25我的医帮宝
 */
public class MineActivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout rl_total_money;
    private RelativeLayout  rl_products_delivery;
    private RelativeLayout  rl_meals_delivery;
    private RelativeLayout  Instead;
    private ImageView iv_info_hint;

    private MineBean data;

    private ImageView iv_photo_bg;
    private TextView tv_name;
    private TextView tv_collect_meals;
    private TextView tv_collect_goods;
    private TextView tv_collect_info;
    private TextView totalAmount;
    private RatingBar roomRatingBar;
    String num="0";
    BadgeView badgeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this,getSupportActionBar());
        titleBarManager.showBtnTitleTextBar(R.string.my, R.drawable.common_back, R.drawable.set_image, true);

        showLoadingPage(false);
    }
    @Override
    protected void initBaseData() {

    }

    @Override
    protected YbbHttpJsonRequest initRequest() {

        MineRequest request = new MineRequest(new Response.Listener<MineResponse>() {
            @Override
            public void onResponse(MineResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    data = response.getData();

                    tv_collect_meals.setText(data.getDistributionCount());
                    tv_collect_goods.setText(data.getPraiseCount());
                    tv_collect_info.setText(data.getRank());
                    tv_name.setText(data.getNickName());
                    totalAmount.setText(data.getTotalAmount());
                    String temp = data.getAvgComment();
                    if(StringUtils.isEmpty(temp)){
                        temp = "0";
                        Logger.e("wangzhijun", temp + "");
                    }

//                    roomRatingBar.setRating(Double.parseDouble(temp));
                    roomRatingBar.setRating(1.5f);


                } else {// failed
                    Log.e("error", "error");
                }
            }
        },this);
        request.setUserId(YbbPsApplication.getInstance().getMyUserInfo().getUserId());
        Utils.showProgressDialog(this);

        return request;
    }

    @Override
    public View createSuccessView(LayoutInflater inflater)
    {
        View mineView = inflater.inflate(R.layout.activity_mine,null);
        rl_total_money = (RelativeLayout) mineView.findViewById(R.id.rl_total_money);
        rl_products_delivery = (RelativeLayout) mineView.findViewById(R.id.rl_products_delivery);
        rl_meals_delivery = (RelativeLayout) mineView.findViewById(R.id.rl_meals_delivery);
        Instead = (RelativeLayout) mineView.findViewById(R.id.Instead);
        iv_info_hint = (ImageView)mineView.findViewById(R.id.iv_info_hint);

        iv_photo_bg = (ImageView)mineView.findViewById(R.id.iv_photo_bg);
        tv_name = (TextView)mineView.findViewById(R.id.tv_name);
        tv_collect_meals = (TextView)mineView.findViewById(R.id.tv_collect_meals);
        tv_collect_goods = (TextView)mineView.findViewById(R.id.tv_collect_goods);
        tv_collect_info = (TextView)mineView.findViewById(R.id.tv_collect_info);
        totalAmount = (TextView)mineView.findViewById(R.id.totalAmount);
        roomRatingBar = (RatingBar)mineView.findViewById(R.id.roomRatingBar);
        DbUtils db = DbManager.getInstance(this).getDefaultDbUtils();
                try {
             num=String.valueOf(db.count(Selector.from(PSMessage.class).where("isread", "=", "0")));
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        badgeView = new BadgeView(mContext);
        badgeView.setTargetView(iv_info_hint);
        badgeView.setBackgroundResource(R.drawable.home_badgeview);
        badgeView.setBadgeGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        badgeView.setBadgeMargin(0, 0, 0, 0);
        badgeView.setBadgeCount(Integer.parseInt(num));
        badgeView.setTextSize(10);
        if("0".equals(num)){
            badgeView.setVisibility(View.INVISIBLE);
        }
        rl_meals_delivery.setOnClickListener(this);
        rl_products_delivery.setOnClickListener(this);
        rl_total_money.setOnClickListener(this);
        iv_info_hint.setOnClickListener(this);
        Instead.setOnClickListener(this);
        return mineView;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.rl_total_money:
                //Utils.moveTo(mContext, MineOrderActivcity.class);
                break;
            case R.id.rl_products_delivery:
                break;
            case R.id.rl_meals_delivery:
                Utils.moveTo(this, MealsDeliveryActivity.class);
                break;
            case R.id.Instead:
                Utils.moveTo(this, InsteadDeliveryActivity.class);
                break;
            case R.id.iv_info_hint:
                Utils.moveTo(this, PSMessageActivity.class);
                break;



        }
    }

    @Override
    public void clickImageRight() {
        Utils.moveTo(this,SettingActivity.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DbUtils db = DbManager.getInstance(this).getDefaultDbUtils();
        try {
            num=String.valueOf(db.count(Selector.from(PSMessage.class).where("isread", "=", "0")));
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        badgeView.setBadgeCount(Integer.parseInt(num));
        if("0".equals(num)){
            badgeView.setVisibility(View.INVISIBLE);
        }else {
            badgeView.setVisibility(View.VISIBLE);
        }
    }
}
