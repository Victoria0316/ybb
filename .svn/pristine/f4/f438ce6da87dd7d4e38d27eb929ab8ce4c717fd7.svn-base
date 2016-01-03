package com.bluemobi.ybb.fragment;

import android.app.Activity;
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
import com.bluemobi.ybb.activity.RestaurantBookingActivity;
import com.bluemobi.ybb.app.AttributeComparator;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.fragment.page.AttributePage;
import com.bluemobi.ybb.fragment.page.BasePage;
import com.bluemobi.ybb.network.model.ParamModel;
import com.bluemobi.ybb.view.LoadingPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by liufy on 2015/6/29.
 * P8病患套餐
 */
public class PatientEatFragment extends BaseFragment implements View.OnClickListener {

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

    private ArrayList<ParamModel.ProductAttributeEntity> attributeEntities;


    private TextView cartAmountTv;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof MedicalPlanMealsActivity){
            cartAmountTv =  ((MedicalPlanMealsActivity)activity).getTv_shopcar_total();
        }else if(activity instanceof RestaurantBookingActivity){
            cartAmountTv =  ((RestaurantBookingActivity)activity).getTv_shopcar_total();
        }
    }
    @Override
    public void initData(Bundle savedInstanceState)
    {
        isShowLoadPage = false;
        prepareData();
        for(int i=0; i<PAGESIZE; i++){
            AttributePage page = new AttributePage(mContext,this,cartAmountTv ,
                    attributeEntities.get(i).getId(),attributeEntities.get(i).getAttributeName()
                    ,"4");//4 病患套餐
            mPages.add(page.getRootView());
            mBasePages.add(page);
        }

    }

    private void prepareData() {
        attributeEntities = YbbApplication.getInstance().getParamModel().
                getProductAttributeInfoList();
        Collections.sort(attributeEntities, new AttributeComparator());
    }

    @Override
    public View createSuccessView(LayoutInflater inflater)
    {
        View patientFoodView = inflater.inflate(R.layout.fragment_patient_foods,null);
        mViewPager = (ViewPager) patientFoodView.findViewById(R.id.vp_tab);
        rl_tab_breakfast = (RelativeLayout) patientFoodView.findViewById(R.id.rl_tab_breakfast);
        rl_tab_lunch = (RelativeLayout) patientFoodView.findViewById(R.id.rl_tab_lunch);
        rl_tab_dinner = (RelativeLayout) patientFoodView.findViewById(R.id.rl_tab_dinner);
        tv_tab_breakfast = (TextView) patientFoodView.findViewById(R.id.tv_tab_breakfast);
        tv_tab_lunch = (TextView) patientFoodView.findViewById(R.id.tv_tab_lunch);
        tv_tab_dinner = (TextView) patientFoodView.findViewById(R.id.tv_tab_dinner);
        iv_tab_breakfast = (ImageView) patientFoodView.findViewById(R.id.iv_tab_breakfast);
        iv_tab_lunch = (ImageView) patientFoodView.findViewById(R.id.iv_tab_lunch);
        iv_tab_dinner = (ImageView) patientFoodView.findViewById(R.id.iv_tab_dinner);

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
                Logger.e("wangzhijun", "onPageSelected-->" + position);
                mBasePages.get(position).loaded();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setAdapter(myTabPageAdapter);
        mBasePages.get(0).loaded();

        return patientFoodView;
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


    private  class MyTabPageAdapter extends  PagerAdapter
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




}
