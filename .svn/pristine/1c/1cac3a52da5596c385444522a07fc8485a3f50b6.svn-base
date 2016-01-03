package com.bluemobi.ybb.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.activity.MedicalPlanMealsActivity;
import com.bluemobi.ybb.app.AttributeComparator;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.fragment.page.AttributePage;
import com.bluemobi.ybb.fragment.page.BasePage;
import com.bluemobi.ybb.network.model.ParamModel;
import com.bluemobi.ybb.util.Constants;
import com.bluemobi.ybb.util.PreferencesService;
import com.bluemobi.ybb.util.StringUtils;
import com.bluemobi.ybb.view.LoadingPage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by liufy on 2015/6/29.
 * P7医护套餐-医护
 */
public class MedicalPlanFragment extends BaseFragment implements View.OnClickListener {

    private ViewPager mViewPager;
    private PagerAdapter mAdapter;
    private RelativeLayout rl_tab_breakfast;
    private RelativeLayout rl_tab_lunch;
    private RelativeLayout rl_tab_dinner;
    private TextView tv_tab_breakfast;
    private TextView tv_tab_lunch;
    private TextView tv_tab_dinner;
    private ImageView iv_tab_breakfast;
    private ImageView iv_tab_lunch;
    private ImageView iv_tab_dinner;
    private List<View> mPages = new ArrayList<View>();
    private List<BasePage> mBasePages = new ArrayList<BasePage>();
    /**
     * 页数
     */
    private static final int PAGESIZE = 3;

    private int current;

    private int cartCounts;

    private String cart_amount;

    private MyBroadcastReceiver mBroadcastReceiver;
    private BigDecimal cart_amount_decimal;

    private TextView cartAmountTv;

    private ArrayList<ParamModel.ProductAttributeEntity> attributeEntities;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        MedicalPlanMealsActivity activity1 = (MedicalPlanMealsActivity)activity;
        cartAmountTv = activity1.getTv_shopcar_total();
    }

    @Override
    public void initData(Bundle savedInstanceState)
    {
        isShowLoadPage = false;
        prepareData();
        for(int i=0; i<PAGESIZE; i++){
            AttributePage page = new AttributePage(mContext,this,cartAmountTv ,
                    attributeEntities.get(i).getId(),attributeEntities.get(i).getAttributeName()
                    ,"3");
            mPages.add(page.getRootView());
            mBasePages.add(page);
        }

        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(Constants.CART_COUNT);
        mBroadcastReceiver = new MyBroadcastReceiver();
        getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    private void prepareData() {
        attributeEntities = YbbApplication.getInstance().getParamModel().
                getProductAttributeInfoList();
        Collections.sort(attributeEntities, new AttributeComparator());
    }

    @Override
    public void onResume() {
        super.onResume();
        cart_amount = YbbApplication.getInstance().getCartAmount();
        cart_amount_decimal = new BigDecimal(cart_amount);
        cartCounts = YbbApplication.getInstance().getCartCounts();
        if (StringUtils.isEmpty(cart_amount))
            cart_amount = "0";
        ((MedicalPlanMealsActivity) mContext).setCarShopTotal(cart_amount);
        ((BaseActivity) mContext).setShopCarNum(cartCounts, true);

        try {
            PreferencesService.getInstance(mContext).saveInt("cartCounts", cartCounts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View createSuccessView(LayoutInflater inflater)
    {
        View medicalPlanView = inflater.inflate(R.layout.fragment_medical_meals,null);
        mViewPager = (ViewPager) medicalPlanView.findViewById(R.id.vp_tab);
        rl_tab_breakfast = (RelativeLayout) medicalPlanView.findViewById(R.id.rl_tab_breakfast);
        rl_tab_lunch = (RelativeLayout) medicalPlanView.findViewById(R.id.rl_tab_lunch);
        rl_tab_dinner = (RelativeLayout) medicalPlanView.findViewById(R.id.rl_tab_dinner);
        tv_tab_breakfast = (TextView) medicalPlanView.findViewById(R.id.tv_tab_breakfast);
        tv_tab_lunch = (TextView) medicalPlanView.findViewById(R.id.tv_tab_lunch);
        tv_tab_dinner = (TextView) medicalPlanView.findViewById(R.id.tv_tab_dinner);
        iv_tab_breakfast = (ImageView) medicalPlanView.findViewById(R.id.iv_tab_breakfast);
        iv_tab_lunch = (ImageView) medicalPlanView.findViewById(R.id.iv_tab_lunch);
        iv_tab_dinner = (ImageView) medicalPlanView.findViewById(R.id.iv_tab_dinner);

        rl_tab_breakfast.setOnClickListener(this);
        rl_tab_lunch.setOnClickListener(this);
        rl_tab_dinner.setOnClickListener(this);
        for(int i=0; i<PAGESIZE; i++){
            if(i==0){
                tv_tab_breakfast.setText(attributeEntities.get(i).getAttributeName());
            }
            if(i==1){
                tv_tab_lunch.setText(attributeEntities.get(i).getAttributeName());
            }
            if(i==2){
                tv_tab_dinner.setText(attributeEntities.get(i).getAttributeName());
            }
        }
        mViewPager.setCurrentItem(0);
        iv_tab_breakfast.setImageResource(R.drawable.search_bar_tab_line);
        tv_tab_breakfast.setTextColor(getResources().getColor(R.color.common_blue));
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                int currentItem = mViewPager.getCurrentItem();
                clearState();
                switch (currentItem) {
                    case 0:
                        iv_tab_breakfast.setImageResource(R.drawable.search_bar_tab_line);
                        tv_tab_breakfast.setTextColor(getResources().getColor(R.color.common_blue));
                        mBasePages.get(0).initData();
                        break;
                    case 1:
                        iv_tab_lunch.setImageResource(R.drawable.search_bar_tab_line);
                        tv_tab_lunch.setTextColor(getResources().getColor(R.color.common_blue));
                        mBasePages.get(1).initData();
                        break;
                    case 2:
                        iv_tab_dinner
                                .setImageResource(R.drawable.search_bar_tab_line);
                        tv_tab_dinner.setTextColor(getResources().getColor(R.color.common_blue));
                        mBasePages.get(2).initData();
                        break;

                }

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        MyTabPageAdapter myTabPageAdapter = new MyTabPageAdapter();
        mViewPager.setAdapter(myTabPageAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Logger.e("wangzhijun","onPageSelected-->" + position);
                mBasePages.get(position).loaded();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setAdapter(myTabPageAdapter);
        mBasePages.get(0).loaded();
        return medicalPlanView;
    }


    public void clearState()
    {
        tv_tab_breakfast.setTextColor(getResources().getColor(R.color.common_gray));
        tv_tab_lunch.setTextColor(getResources().getColor(R.color.common_gray));
        tv_tab_dinner.setTextColor(getResources().getColor(R.color.common_gray));
        iv_tab_breakfast.setImageResource(R.color.trans);
        iv_tab_lunch.setImageResource(R.color.trans);
        iv_tab_dinner.setImageResource(R.color.trans);

    }





    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }


    @Override
    public void onClick(View v)
    {
       switch (v.getId())
       {
           case R.id.rl_tab_breakfast:
               mViewPager.setCurrentItem(0);
               break;

           case R.id.rl_tab_lunch:
               mViewPager.setCurrentItem(1);
               break;

           case R.id.rl_tab_dinner:
               mViewPager.setCurrentItem(2);
               break;
       }

    }


    private class MyTabPageAdapter extends  PagerAdapter
    {



        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mPages.get(position));
            return mPages.get(position);

        }

        @Override
        public int getCount() {
            return mPages.size();
        }
        
        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {

            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            if (view == object)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    protected class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(Constants.CART_COUNT)){
//                Toast.makeText(Test.this, "处理action名字相对应的广播", 200);
                cartCounts = intent.getIntExtra("count",cartCounts);
                int orgPrice = intent.getIntExtra("orgPrice", 0);
                ((BaseActivity) mContext).setShopCarNum(cartCounts, true);
                try {
                    PreferencesService.getInstance(mContext).saveInt("cartCounts", cartCounts);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mBroadcastReceiver);
    }


}
