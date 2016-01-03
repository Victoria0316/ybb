package com.bluemobi.ybb.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluemobi.ybb.R;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.fragment.NutritiousFoodsFragment;
import com.bluemobi.ybb.fragment.PatientEatFragment;
import com.bluemobi.ybb.fragment.ZeroPointmealFragment;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.view.LoadingPage;

/**
 * Created by liufy on 2015/7/1.
 * P6营养餐
 */
public class RestaurantBookingActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 营养餐病患
     */
    private NutritiousFoodsFragment nutritiousFoodsFragment;
    /**
     * 病患
     */
    private PatientEatFragment patientEatFragment;

    private ZeroPointmealFragment zeroPointmealFragment;

    private FragmentManager fragmentManager;

    private TitleBarManager titleBarManager;

    private RelativeLayout patientsmealsId;
    private RelativeLayout zeroPointMealId;
    private RelativeLayout nutritiousFoodId;

    private TextView tv_nutritious_food;
    private TextView tv_patients_meals;
    private TextView tv_zero_point_meal;
    /**
     * 购物车金额
     */
    private TextView tv_shopcar_total;

    private RelativeLayout rl_shop;

    private int shopNum = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleBarManager = new TitleBarManager();
        titleBarManager.init(RestaurantBookingActivity.this, getSupportActionBar());
        titleBarManager.showThreeTabTitleBar(R.drawable.common_back);
        showLoadingPage(false);

    }


    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {

        View restaurantBookView = inflater.inflate(R.layout.activity_restaurant_booking, null);
        rl_shop = (RelativeLayout) restaurantBookView.findViewById(R.id.rl_shop);
        tv_shopcar_total = (TextView) restaurantBookView.findViewById(R.id.tv_shopcar_total);
        shopCart = (ImageView)restaurantBookView. findViewById(R.id.iv_shop_car);
        rl_shop.setOnClickListener(this);
        phone=(RelativeLayout)restaurantBookView. findViewById(R.id.phone);
        phone.setOnClickListener(this);
        nutritiousFoodId = titleBarManager.getNutritiousFoodId();
        patientsmealsId = titleBarManager.getPatientsmealsId();
        zeroPointMealId = titleBarManager.getZeroPointMealId();
        tv_nutritious_food = (TextView) nutritiousFoodId.getChildAt(0);
        tv_patients_meals = (TextView) patientsmealsId.getChildAt(0);
        tv_zero_point_meal = (TextView) zeroPointMealId.getChildAt(0);
        fragmentManager = getFragmentManager();
        setTabSelection(0);
        return restaurantBookView;
    }


    public void setCarShopTotal(String price)
    {
        tv_shopcar_total.setText("￥" + price);
    }

    public TextView getTv_shopcar_total() {
        return tv_shopcar_total;
    }

    public void setTv_shopcar_total(TextView tv_shopcar_total) {
        this.tv_shopcar_total = tv_shopcar_total;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index
     */
    private void setTabSelection(int index) {
        clearSelection();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:

                tv_nutritious_food.setTextColor(getResources().getColor(R.color.common_blue));
                nutritiousFoodId.setBackgroundResource(R.drawable.tab_left);
                if (nutritiousFoodsFragment == null) {
                    nutritiousFoodsFragment = new NutritiousFoodsFragment();
                    transaction.add(R.id.fl_content, nutritiousFoodsFragment);
                } else {
                    transaction.show(nutritiousFoodsFragment);

                }
                break;
            case 1:
                tv_patients_meals.setTextColor(getResources().getColor(R.color.common_blue));

                patientsmealsId.setBackgroundResource(R.drawable.tab_middle);

                if (patientEatFragment == null) {
                    patientEatFragment = new PatientEatFragment();
                    transaction.add(R.id.fl_content, patientEatFragment);
                } else {
                    transaction.show(patientEatFragment);
                }
                break;
            case 2:
                tv_zero_point_meal.setTextColor(getResources().getColor(R.color.common_blue));
                zeroPointMealId.setBackgroundResource(R.drawable.tab_right);

                if (zeroPointmealFragment == null) {
                    zeroPointmealFragment = new ZeroPointmealFragment();

                    transaction.add(R.id.fl_content, zeroPointmealFragment);
                } else {
                    transaction.show(zeroPointmealFragment);
                }

                break;

        }
        transaction.commit();
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {

        tv_nutritious_food.setTextColor(getResources().getColor(R.color.white));
        tv_patients_meals.setTextColor(getResources().getColor(R.color.white));
        tv_zero_point_meal.setTextColor(getResources().getColor(R.color.white));
        nutritiousFoodId.setBackgroundResource(R.color.trans);
        patientsmealsId.setBackgroundResource(R.color.trans);
        zeroPointMealId.setBackgroundResource(R.color.trans);

    }


    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (nutritiousFoodsFragment != null) {
            transaction.hide(nutritiousFoodsFragment);
        }
        if (patientEatFragment != null) {
            transaction.hide(patientEatFragment);
        }
        if (zeroPointmealFragment != null) {
            transaction.hide(zeroPointmealFragment);
        }

    }

    @Override
    public void clickleftTab() {
        setTabSelection(0);
    }

    @Override
    public void clickMiddleTab() {
        setTabSelection(1);
    }

    @Override
    public void clickRightTab() {
        setTabSelection(2);
    }

    @Override
    public void clickBarleft() {
        finish();
    }

    @Override
    public void onClick(View v)
    {
           switch (v.getId())
           {
               case R.id.rl_shop:
                   Utils.moveTo(mContext,ShopCarActivity.class);
                   break;
               case R.id.phone:
                   call();
                   break;


           }
    }



}
