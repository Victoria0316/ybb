package com.bluemobi.ybb.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.fragment.MineOrderGoodsFragment;
import com.bluemobi.ybb.fragment.MineOrderMealsFragment;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.util.Constants;
import com.bluemobi.ybb.view.LoadingPage;

/**
 * Created by 丰宇 on 2015/7/12.
 */
public class MineOrderActivcity extends BaseActivity {


    private MineOrderMealsFragment mineOrderMealsFragment;

    private MineOrderGoodsFragment mineOrderGoodsFragment;

    private FragmentManager fragmentManager;

    private FrameLayout fl_content;

    private TitleBarManager titleBarManager;

    private RelativeLayout rl_order_meals;

    private RelativeLayout rl_order_goods;

    private TextView tv_order_meals;
    private TextView tv_order_goods;

    public static final int MEAL = 0;
    public static final int GOOD = 1;
    public static final int COMMENT = 2;
    public static final int PAY = 3;
    private OrderDataChangeReceiver mReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        titleBarManager = new TitleBarManager();
        titleBarManager.init(MineOrderActivcity.this,getSupportActionBar());
        titleBarManager.showTwoTabTitleBar(R.drawable.common_back, Constants.NO_DISPLAY_TABBAR_RIGHT, R.string.str_shop_meals, R.string.str_shop_goods);
        showLoadingPage(false);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.RECEIVER_ORDER_DATA_CHANGE);
        mReceiver = new OrderDataChangeReceiver();
        mContext.registerReceiver(mReceiver, intentFilter);

    }


    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {
        View mineOrder = inflater.inflate(R.layout.activity_mine_order, null);
        rl_order_meals = (RelativeLayout) titleBarManager.getTwoTabLeftView();
        rl_order_goods = (RelativeLayout) titleBarManager.getTwoTabRightView();
        tv_order_meals = (TextView) rl_order_meals.getChildAt(0);
        tv_order_goods = (TextView) rl_order_goods.getChildAt(0);
        fragmentManager = getFragmentManager();
        setTabSelection(0);
        return mineOrder;
    }

    @Override
    public void clickTwoTableft() {
        setTabSelection(0);
    }

    @Override
    public void clickTwoTabright() {
        setTabSelection(1);
    }

    @Override
    public void clickImageRight() {
        Intent intent = new Intent(mContext,HomeSearchActivity.class);
        intent.putExtra("From","P7");
        startActivity(intent);
        finish();
    }

    private void setTabSelection(int index) {
        clearSelection();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:

                rl_order_meals.setBackgroundResource(R.drawable.two_tab_left);
                tv_order_meals.setTextColor(getResources().getColor(R.color.common_blue));
                if (mineOrderMealsFragment == null)
                {
                    mineOrderMealsFragment = new MineOrderMealsFragment();
                    transaction.add(R.id.fl_content, mineOrderMealsFragment);
                    //transaction.add()
                } else
                {
                    transaction.show(mineOrderMealsFragment);

                }
                break;
            case 1:
                rl_order_goods.setBackgroundResource(R.drawable.two_tab_right);
                tv_order_goods.setTextColor(getResources().getColor(R.color.common_blue));
                if (mineOrderGoodsFragment == null) {
                    mineOrderGoodsFragment = new MineOrderGoodsFragment();

                    transaction.add(R.id.fl_content, mineOrderGoodsFragment);
                } else {
                    transaction.show(mineOrderGoodsFragment);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        rl_order_meals.setBackgroundResource(R.color.common_transparent);
        rl_order_goods.setBackgroundResource(R.color.common_transparent);
        tv_order_meals.setTextColor(getResources().getColor(R.color.white));
        tv_order_goods.setTextColor(getResources().getColor(R.color.white));
    }


    private void hideFragments(FragmentTransaction transaction) {
        if (mineOrderGoodsFragment != null) {
            transaction.hide(mineOrderGoodsFragment);
        }
        if (mineOrderMealsFragment != null) {
            transaction.hide(mineOrderMealsFragment);
        }

    }


    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    public void notifyChanged(int type) {
        if(type == MEAL){
            mineOrderMealsFragment.notifyChanged();
        }
    }
    protected class OrderDataChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Logger.e("wangzhijun", "EVALUATE OrderCommentReceiver");
            if(Constants.RECEIVER_ORDER_DATA_CHANGE.equals(intent.getAction())){
//                int index = intent.getIntExtra("index", -2);
//                Logger.e("wangzhijun", "intent.getIntExtra(\"index\", -2);--> " + index);
//                if(index != -1 && index != -2){
//                    dataList.remove(index);
//                    adapter.notifyDataSetChanged();
//                }
                int type = intent.getIntExtra("type", -1);
                switch (type){
                    case COMMENT:
                        mineOrderMealsFragment.notifyCommentChanged();
                        break;
                    case PAY:
                        mineOrderMealsFragment.notifyPayChanged();
                        break;
                }
            }
        }
    }
}
