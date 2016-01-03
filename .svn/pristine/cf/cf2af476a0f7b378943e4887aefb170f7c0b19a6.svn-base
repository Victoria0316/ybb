package com.bluemobi.ybb.ps.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bluemobi.base.crop.Crop;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.base.utils.Utils;
import com.bluemobi.base.utils.WebUtils;
import com.bluemobi.ybb.ps.R;
import com.bluemobi.ybb.ps.app.BackGroundTask;
import com.bluemobi.ybb.ps.app.TitleBarManager;
import com.bluemobi.ybb.ps.app.UpdateChecker;
import com.bluemobi.ybb.ps.app.YbbPsApplication;
import com.bluemobi.ybb.ps.base.BaseActivity;
import com.bluemobi.ybb.ps.fragment.DiliverymanGoodsFragment;
import com.bluemobi.ybb.ps.fragment.DiliverymanMealFragment;
import com.bluemobi.ybb.ps.fragment.DiliverymanProductsFragment;
import com.bluemobi.ybb.ps.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.ps.network.request.PsUploadCidRequest;
import com.bluemobi.ybb.ps.network.response.UploadCidResponse;
import com.bluemobi.ybb.ps.view.LoadingPage;
import com.igexin.sdk.PushManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * Created by gaoyn on 2015/7/9.
 *
 * p23 餐品
 */
public class DiliverymanMainActivity extends BaseActivity implements View.OnClickListener{


    //餐品
    private DiliverymanProductsFragment diliverymanProductsFragment;
    private View ProductsLayout;
    private ImageView iv_products;
    private TextView tv_products;

    //商品
    private DiliverymanGoodsFragment diliverymanGoodsFragment;
    private View GoodsLayout;
    private ImageView iv_goods;
    private TextView tv_goods;

    //代点餐
    private DiliverymanMealFragment diliverymanMealFragment;
    private View MealLayout;
    private ImageView iv_meal;
    private TextView tv_meal;

    private long exitTime;


    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager fragmentManager;

    private FrameLayout fl_content;

    TitleBarManager titleBarManager;

    public static DiliverymanMainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance=this;
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(DiliverymanMainActivity.this,getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.distribution, R.drawable.left_head, true);
        showLoadingPage(false);
        PushManager.getInstance().initialize(this.getApplicationContext());
        UpdateChecker checker = UpdateChecker.getInstance(this);
        checker.check(false);
    }

    public static DiliverymanMainActivity getInstance()
    {
        return instance;
    }
    public TitleBarManager getTitleBarManager(){
        return titleBarManager;
    }

    @Override
    protected void initBaseData() {

    }


    @Override
    public View createSuccessView(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.activity_diliveryman_main, null);


        fl_content = (FrameLayout) view.findViewById(R.id.fl_content);
        ProductsLayout = view.findViewById(R.id.rl_products);
        GoodsLayout = view.findViewById(R.id.rl_goods);
        MealLayout = view.findViewById(R.id.rl_meal);

        iv_products = (ImageView) view.findViewById(R.id.iv_products);
        iv_goods = (ImageView) view.findViewById(R.id.iv_goods);
        iv_meal = (ImageView) view.findViewById(R.id.iv_meal);

        tv_products = (TextView) view.findViewById(R.id.tv_products);
        tv_goods = (TextView) view.findViewById(R.id.tv_goods);
        tv_meal = (TextView) view.findViewById(R.id.tv_meal);

        ProductsLayout.setOnClickListener(this);
        GoodsLayout.setOnClickListener(this);
        MealLayout.setOnClickListener(this);

        fragmentManager = getFragmentManager();
        setTabSelection(0);


        return view;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rl_products:
                setTabSelection(0);
                break;
            case R.id.rl_goods:
                Utils.makeToastAndShow(getBaseContext(), "敬请期待");
//                setTabSelection(1);
                break;
            case R.id.rl_meal:
                setTabSelection(2);
                break;
            default:
                break;
        }
    }

    public void setTabSelection(int index) {
        clearSelection();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        titleBarManager = new TitleBarManager();
        titleBarManager.init(DiliverymanMainActivity.this,getSupportActionBar());

        switch (index){
            case 0:
                titleBarManager.showCommonTitleBar(R.string.distribution, R.drawable.left_head, true);

                iv_products.setImageResource(R.drawable.products_bright);
                tv_products.setTextColor(getResources().getColor(R.color.common_blue));
                if (diliverymanProductsFragment == null)
                {
                    diliverymanProductsFragment = new DiliverymanProductsFragment();
                    transaction.add(R.id.fl_content, diliverymanProductsFragment);

                } else
                {
                    transaction.show(diliverymanProductsFragment);

                }
                break;
            case 1:


                titleBarManager.showCommonTitleBar(R.string.distribution,R.drawable.left_head,true);

                iv_goods.setImageResource(R.drawable.goods_bright);
                tv_goods.setTextColor(getResources().getColor(R.color.common_blue));

                if (diliverymanGoodsFragment == null) {
                    diliverymanGoodsFragment = new DiliverymanGoodsFragment();

                    transaction.add(R.id.fl_content, diliverymanGoodsFragment);
                } else {
                    transaction.show(diliverymanGoodsFragment);
                }
                break;
            case 2:

                titleBarManager.showCommonTitleBar(R.string.Order_meal, R.drawable.left_head, true);

                iv_meal.setImageResource(R.drawable.ordering_meal_bright);
                tv_meal.setTextColor(getResources().getColor(R.color.common_blue));

                if (diliverymanMealFragment == null) {
                    diliverymanMealFragment = new DiliverymanMealFragment();
                    transaction.add(R.id.fl_content, diliverymanMealFragment);
                } else {
                    transaction.show(diliverymanMealFragment);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        iv_products.setImageResource(R.drawable.products_dark);
        tv_products.setTextColor(getResources().getColor(R.color.common_InputBox));

        iv_goods.setImageResource(R.drawable.goods_dark);
        tv_goods.setTextColor(getResources().getColor(R.color.common_InputBox));

        iv_meal.setImageResource(R.drawable.ordering_meal_dark);
        tv_meal.setTextColor(getResources().getColor(R.color.common_InputBox));

    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction
     *            用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (diliverymanProductsFragment != null) {
            transaction.hide(diliverymanProductsFragment);
        }
        if (diliverymanGoodsFragment != null) {
            transaction.hide(diliverymanGoodsFragment);
        }
        if (diliverymanMealFragment != null) {
            transaction.hide(diliverymanMealFragment);
        }
    }

    @Override
    public void clickBarleft() {
        Utils.moveTo(this, MineActivity.class);
    }

    public  void uploadCid(String cid){
        PsUploadCidRequest request = new PsUploadCidRequest(new Response.Listener<UploadCidResponse>() {
            @Override
            public void onResponse(UploadCidResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    //Toast.makeText(HomeActivity.this, "上传cid成功", Toast.LENGTH_SHORT).show();
                } else {// failed
                    Log.e("error", "error");
                }
            }
        }, DiliverymanMainActivity.this);
        request.setClientId(cid);
        request.setUserId(YbbPsApplication.getInstance().getMyUserInfo().getUserId());
        WebUtils.doPost(request);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 3000) {
                Toast.makeText(getApplicationContext(), "再按一次将退出应用",
                        Toast.LENGTH_LONG).show();
                exitTime = System.currentTimeMillis();
            } else {
                YbbPsApplication.getInstance().setMyUserInfo(null);
                this.finish();
            }
        }
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == 201) {
            if (data != null) {
                if (diliverymanProductsFragment!=null&&diliverymanProductsFragment.adapter!=null){
                    diliverymanProductsFragment.lv.get(data.getIntExtra("pos", 0)).setDistributionType("5");
                    diliverymanProductsFragment.adapter.notifyDataSetChanged();
            }
            }
        }
    }
}
