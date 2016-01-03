package com.bluemobi.ybb.fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.activity.HomeActivity;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.view.LoadingPage;

/**
 * Created by liufy on 2015/6/29.
 * 购物车
 */
public class ShoppingCartFragment extends BaseFragment {

    private ShopCarGoodsFragment shopCarGoodsFragment;

    private ShopCarMealsFragment shopCarMealsFragment;

    private FrameLayout fl_shopcard;

    private RelativeLayout leftView;

    private TextView tv_left;

    private TextView tv_right;

    private RelativeLayout rightView;

    private  boolean isFrist = false;

    private FragmentManager fragmentManager;

    @Override
    public void initData(Bundle savedInstanceState) {
        isShowLoadPage = false;
    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {
        View shopcarView = inflater.inflate(R.layout.fragment_shopcar, null);
        TitleBarManager titleBarManager = ((HomeActivity) getActivity()).getTitleBarManager();
        fl_shopcard = (FrameLayout) shopcarView.findViewById(R.id.fl_shopcard);
        leftView = (RelativeLayout) titleBarManager.getTwoTabLeftView();
        tv_left = (TextView) leftView.getChildAt(0);
        rightView = (RelativeLayout) titleBarManager.getTwoTabRightView();
        tv_right = (TextView) rightView.getChildAt(0);
        leftView = (RelativeLayout) titleBarManager.getTwoTabLeftView();
        tv_left = (TextView) leftView.getChildAt(0);
        rightView = (RelativeLayout) titleBarManager.getTwoTabRightView();
        tv_right = (TextView) rightView.getChildAt(0);
        fragmentManager = getFragmentManager();
        setTabSelection(0);
        return shopcarView;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {

        super.onHiddenChanged(hidden);
        if (((HomeActivity) mContext).isHiddChange)
        {

            clearSelection();
        }


    }

    public void setTabSelection(int index) {
        isFrist = true;
        clearSelection();
        android.util.Log.e("tag", "setTabSelection");
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                android.util.Log.e("tag","setTabSelection----000");
                tv_left.setTextColor(getResources().getColor(R.color.common_blue));
                leftView.setBackgroundResource(R.drawable.two_tab_left);
                if (shopCarMealsFragment == null) {
                    shopCarMealsFragment = new ShopCarMealsFragment();
                    transaction.add(R.id.fl_shopcard, shopCarMealsFragment);
                } else {
                    transaction.show(shopCarMealsFragment);

                }
                break;
            case 1:
                android.util.Log.e("tag","setTabSelection----111");
                tv_right.setTextColor(getResources().getColor(R.color.common_blue));
                rightView.setBackgroundResource(R.drawable.two_tab_right);
                if (shopCarGoodsFragment == null) {
                    shopCarGoodsFragment = new ShopCarGoodsFragment();
                    transaction.add(R.id.fl_shopcard, shopCarGoodsFragment);
                } else {
                    transaction.show(shopCarGoodsFragment);
                }
                break;


        }
        transaction.commit();
    }

    private void clearSelection() {

        tv_right.setTextColor(getResources().getColor(R.color.white));
        tv_left.setTextColor(getResources().getColor(R.color.white));
        rightView.setBackgroundResource(R.color.trans);
        leftView.setBackgroundResource(R.color.trans);

    }



    private void hideFragments(FragmentTransaction transaction) {
        if (shopCarMealsFragment != null) {
            transaction.hide(shopCarMealsFragment);
        }
        if (shopCarGoodsFragment != null) {
            transaction.hide(shopCarGoodsFragment);
        }


    }
    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }
    public void notifyDataSetChanged()
    {
        shopCarMealsFragment.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
        Logger.e("wangzhijun", "shoppingcartFragment");
//        shopCarMealsFragment.notifySave();
    }
}
