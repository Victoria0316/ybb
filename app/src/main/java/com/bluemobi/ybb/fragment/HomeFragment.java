package com.bluemobi.ybb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.activity.CommentListActivity;
import com.bluemobi.ybb.activity.HomeActivity;
import com.bluemobi.ybb.activity.InformationDetailsActivity;
import com.bluemobi.ybb.activity.InformationListActivity;
import com.bluemobi.ybb.activity.MedicalPlanMealsActivity;
import com.bluemobi.ybb.activity.RestaurantBookingActivity;
import com.bluemobi.ybb.adapter.CommonAdapter;
import com.bluemobi.ybb.adapter.ViewHolder;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.request.InformationLisRequest;
import com.bluemobi.ybb.network.response.InformationListResponse;
import com.bluemobi.ybb.util.Constants;
import com.bluemobi.ybb.util.StringUtils;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.AutoScrollViewPager;
import com.bluemobi.ybb.view.LoadingPage;
import com.bluemobi.ybb.view.SwitchDotView;
import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liufy on 2015/6/29.
 * 主页
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener{

    private CommonAdapter<InformationListResponse.InformationData.InformationDTo> adapter;

    private InformationLisRequest request;

    private List<InformationListResponse.InformationData.InformationDTo> dataList = new ArrayList<InformationListResponse.InformationData.InformationDTo>();

    ArrayList<InformationListResponse.InformationData.InformationDTo> info;

    private AutoScrollViewPager autoScrollViewPager;
    private SwitchDotView switchDotView;

    private boolean listLoadOk;
    private boolean imgListOk;

    private List<InformationListResponse.InformationData.InformationDTo> adLists;

    private List<ImageView> mViewList = new ArrayList<ImageView>();

    private ViewpagerAdapter mAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        isShowLoadPage = false;
    }

    private void loadData() {
        InformationLisRequest request;
        request = new InformationLisRequest
                (
                        new Response.Listener<InformationListResponse>() {
                            @Override
                            public void onResponse(InformationListResponse response) {
                                Utils.closeDialog();
                                if (response.getStatus() == 0)
                                {
                                    showListData(response.data);
                                }
                                else
                                {
                                    Toast.makeText(mContext, response.getContent(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, (Response.ErrorListener) getActivity());
        request.setLoginuserid(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setCurrentnum(Constants.CURRENTNUMBASE + "");
        request.setCurrentpage(0 + "");
        request.setColumnId("2");
        this.request = request;
        WebUtils.doPost(request);
        getImgs();
    }


    private void getImgs() {
        InformationLisRequest request;
        request = new InformationLisRequest
                (
                        new Response.Listener<InformationListResponse>() {
                            @Override
                            public void onResponse(InformationListResponse response) {
                                Utils.closeDialog();
                                if (response.getStatus() == 0)
                                {

                                    for(InformationListResponse.InformationData.InformationDTo item:response.data.info){
                                        ImageView imageView = new ImageView(getActivity());
                                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                                        mViewList.add(imageView);
                                        String temp = item.imagePath;
                                        Logger.e("wangzhijun", temp);
                                        if(!StringUtils.isEmpty(temp)){
                                            Glide.with(getActivity())
                                                    .load(temp).override(Utils.getDeviceWidth(getActivity()),
                                                    (int) (Utils.getDeviceWidth(getActivity())/2.5))
                                                            .placeholder(R.drawable.home_bar)
                                                    .into(imageView)
                                            ;
                                        }
                                    }

                                        mAdapter = new ViewpagerAdapter();
                                        autoScrollViewPager.setAdapter(mAdapter);
                                        switchDotView.generateDots(mViewList.size());
                                    switchDotView.generateDots(mViewList.size());
                                    autoScrollViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                        @Override
                                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                        }

                                        @Override
                                        public void onPageSelected(int position) {
                                            if (mViewList.size() == 1) {
                                                return;
                                            }
                                            switchDotView.setCurrentIndex(position);
                                        }

                                        @Override
                                        public void onPageScrollStateChanged(int state) {

                                        }
                                    });

                                }
                                else
                                {
                                    Toast.makeText(mContext, response.getContent(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, (Response.ErrorListener) getActivity());
        request.setLoginuserid(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setCurrentnum(Constants.CURRENTNUMBASE + "");
        request.setCurrentpage(0+"");
        request.setColumnId("1");
        request.setAdvertisingPositionId(Constants.AD_MAIN_ID);
        WebUtils.doPost(request);
    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {
        View homeView = inflater.inflate(R.layout.fragment_home, null);
        autoScrollViewPager =(AutoScrollViewPager) homeView.findViewById(R.id.auto_viewpaper);
        switchDotView =(SwitchDotView) homeView.findViewById(R.id.swicth_dots);
        autoScrollViewPager.setInterval(2000);
        autoScrollViewPager.setCycle(true);
        autoScrollViewPager.startAutoScroll();

//        if(imgListOk){
//            mAdapter = new ViewpagerAdapter();
//            autoScrollViewPager.setAdapter(mAdapter);
//            switchDotView.generateDots(mViewList.size());
//        }


        LinearLayout restaurant_booking = (LinearLayout) homeView.findViewById(R.id.ll_restaurant_booking);
        LinearLayout ordering_goods = (LinearLayout) homeView.findViewById(R.id.ll_ordering_goods);
        LinearLayout sales_promotion = (LinearLayout) homeView.findViewById(R.id.ll_sales_promotion);
        plv_refresh = (PullToRefreshListView) homeView.findViewById(R.id.plv_refresh);
        initPullToRefresh(plv_refresh);

        plv_refresh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext,InformationDetailsActivity.class);
                InformationListResponse.InformationData.InformationDTo informationDTo = dataList.get(i-1);
                intent.putExtra("articleId",informationDTo.id);
                startActivity(intent);


//                Intent intent = new Intent(mContext,CommentListActivity.class);
//                InformationListResponse.InformationData.InformationDTo informationDTo = dataList.get(i-1);
//                intent.putExtra("articleId",informationDTo.id);
//                startActivity(intent);

            }
        });

        restaurant_booking.setOnClickListener(this);
        ordering_goods.setOnClickListener(this);
        sales_promotion.setOnClickListener(this);
        loadData();
        return homeView;
    }




    @Override
    protected LoadingPage.LoadResult load() {

        return LoadingPage.LoadResult.success;
    }

    @Override
    protected boolean getPage(int type) {

        if (!super.getPage(type)){
            return  false;
        }
        connectToServer();
        return true;

    }

    private void connectToServer()
    {
        InformationLisRequest request = new InformationLisRequest
                (
                        new Response.Listener<InformationListResponse>() {
                            @Override
                            public void onResponse(InformationListResponse response) {
                                Utils.closeDialog();
                               plv_refresh.onRefreshComplete();
                                if (response.getStatus() == 0)
                                {
                                    showListData(response.data);
                                }
                                else
                                {
                                    Toast.makeText(mContext, response.getContent(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, (Response.ErrorListener) getActivity());
        request.setLoginuserid(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setCurrentnum(Constants.CURRENTNUMBASE + "");
        request.setColumnId("2");
        request.setCurrentpage(getCurPage() + "");
        WebUtils.doPost(request);

    }

    /**
     *
     * 显示列表数据
     *
     * */
    private void showListData(InformationListResponse.InformationData list)
    {


        if (list == null)
        {
            return;
        }
        if (list.info.size() == 0)
        {
            return;
        }

        if (list.getCurrentpage().equals("0"))
        {
            dataList.clear();
        }

        for (InformationListResponse.InformationData.InformationDTo lineDto : list.info)
        {
            dataList.add(lineDto);
        }

        if (adapter == null)
        {
            plv_refresh.setAdapter(
                    adapter = new CommonAdapter<InformationListResponse.InformationData.InformationDTo>(mContext,
                            dataList, R.layout.lv__main_info_item) {

                        @Override
                        public void convert(ViewHolder helper, InformationListResponse.InformationData.InformationDTo bean) {
//                            helper.setImageByUrl(R.id.iv_image_bg, bean.imgList.get(0), mContext);
                            helper.setText(R.id.tv_artTitle, bean.artTitle);
                            helper.setText(R.id.tv_artAbstract,bean.artAbstract);
                            ImageView temp = helper.getView(R.id.iv_image_bg);
                            Glide.with(mContext)
                                    .load(bean.imagePath)
                                    .into(temp);

                        }
                    });
        }
        else
        {
            adapter.notifyDataSetChanged();
        }

        if (dataList.size()==50)
        {
            LayoutInflater inflate = LayoutInflater.from(mContext);
            View addMoreBtn = inflate.inflate(R.layout.lv_item_add_more_btn, null);
            LinearLayout ll_load_more = (LinearLayout) addMoreBtn.findViewById(R.id.ll_load_more);
            plv_refresh.getRefreshableView().addFooterView(addMoreBtn);
            plv_refresh.setMode(PullToRefreshBase.Mode.DISABLED);
            ll_load_more.setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //点击订餐服务
            case R.id.ll_restaurant_booking:
                if(YbbApplication.role_bh.equals(YbbApplication.getInstance().getMyUserInfo().getTypeId())){//病患
                    Utils.moveTo(mContext, RestaurantBookingActivity.class);
                }else if(YbbApplication.role_yh.equals(YbbApplication.getInstance().getMyUserInfo().getTypeId())){
                    Utils.moveTo(mContext, MedicalPlanMealsActivity.class);
                }
                break;
            //点击商品订购
            case R.id.ll_ordering_goods:
                Utils.makeToastAndShow(getActivity(), "敬请期待");
                break;
            //点击促销活动
            case R.id.ll_sales_promotion:
                Utils.makeToastAndShow(getActivity(), "敬请期待");
                break;

            case R.id.ll_load_more:
                Utils.moveTo(mContext, InformationListActivity.class);
                break;
        }
    }

    class ViewpagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mViewList == null ? 0 : mViewList.size();
        }

        public void destroyItem(View container, int position, Object object) {
            ImageView view = mViewList.get(position % mViewList.size());
            ((AutoScrollViewPager) container).removeView(view);
            view.setImageBitmap(null);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            return super.instantiateItem(container, position);
            container.addView(mViewList.get(position), 0);//添加页卡
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView(mViewList.get(position));
        }
    }

}
