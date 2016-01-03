package com.bluemobi.ybb.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.activity.CommodityDetailActivity;
import com.bluemobi.ybb.activity.HomeActivity;
import com.bluemobi.ybb.adapter.CommodityAdapter;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.model.CommodityModel;
import com.bluemobi.ybb.network.model.FoodProductModel;
import com.bluemobi.ybb.network.request.CommoditiesRequest;
import com.bluemobi.ybb.network.request.FoodProductListRequest;
import com.bluemobi.ybb.network.request.HotRecommendRequest;
import com.bluemobi.ybb.network.response.CommodtiesResponse;
import com.bluemobi.ybb.network.response.FoodProductListResponse;
import com.bluemobi.ybb.network.response.HotRecommendResponse;
import com.bluemobi.ybb.util.StringUtils;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.AutoScrollViewPager;
import com.bluemobi.ybb.view.CustomExpandableListView;
import com.bluemobi.ybb.view.CustomListView;
import com.bluemobi.ybb.view.LoadingPage;
import com.bluemobi.ybb.view.SwitchDotView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liufy on 2015/6/29.
 * 类目
 */
public class CategoryFragment extends BaseFragment {

    private List<Level1> group = new ArrayList<Level1>();

    private List<List<Level2>> child = new ArrayList<List<Level2>>();
    /**
     * 分类列表
     */
    private ExpandableListView expandableListView;

    private List<Store> stores = new ArrayList<Store>();

    private List<List<FoodProductModel>> commodities = new ArrayList<List<FoodProductModel>>();
    /**
     * 推荐列表
     */
    private CustomExpandableListView defaultRightListView;

    private ExpandAdapter mAdapter;

    private RightViewAdapter rightViewAdapter;

    private Level lastItem;

    private ImageView defaultImg;

    private boolean loadFirst = true;
    private int width;
    private int height;
    /**
     * 普通列表
     */
    private CustomListView categoryList;

    private CommodityAdapter commodityAdapter;

    private LinearLayout rightView;

    private HotRecommendRequest request;

    private  List<FoodProductModel> list = new ArrayList<FoodProductModel>();
    private  List<FoodProductModel> recommendList = new ArrayList<FoodProductModel>();

    private String shitangID;
    private HomeActivity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (HomeActivity) activity;
    }

    @Override
    public void initData(Bundle savedInstanceState)
    {
    }

    @Override
    protected YbbHttpJsonRequest initRequest() {
        HotRecommendRequest request;
        request = new HotRecommendRequest
                (
                        new Response.Listener<HotRecommendResponse>() {
                            @Override
                            public void onResponse(HotRecommendResponse response) {
                                Utils.closeDialog();
                                if (response!=null&&response.getStatus() == 0) {
                                    shitangID=response.getData().getCanteen_id();
                                    if (response.getData().getCombo_category() != null) {
                                        if (response.getData().getCombo_category().getEntry() != null && response.getData().getCombo_category().getEntry().size() != 0) {
                                            group.add(new Level1("热门推荐", "0"));
                                            group.add(new Level1("餐品", "1"));
                                            group.get(0).selected = true;
                                            lastItem = group.get(0);
                                            child.add(null);
                                            ArrayList<Level2> temp1 = new ArrayList<Level2>();
                                            for (int i = 0; i < response.getData().getCombo_category().getEntry().size(); i++) {
                                                if(YbbApplication.role_bh.equals(YbbApplication.getInstance().getMyUserInfo().getTypeId())){//病患
                                                    if("1".equals(response.getData().getCombo_category().getEntry().get(i).getKey())||"2".equals(response.getData().getCombo_category().getEntry().get(i).getKey())||"4".equals(response.getData().getCombo_category().getEntry().get(i).getKey())){
                                                        temp1.add(new Level2(response.getData().getCombo_category().getEntry().get(i).getValue(), response.getData().getCombo_category().getEntry().get(i).getKey()));
                                                    }
                                                }else if(YbbApplication.role_yh.equals(YbbApplication.getInstance().getMyUserInfo().getTypeId())){//医患
                                                    if("3".equals(response.getData().getCombo_category().getEntry().get(i).getKey())||"2".equals(response.getData().getCombo_category().getEntry().get(i).getKey())){
                                                        temp1.add(new Level2(response.getData().getCombo_category().getEntry().get(i).getValue(), response.getData().getCombo_category().getEntry().get(i).getKey()));
                                                    }
                                                }
                                            }
                                            child.add(temp1);
                                        }
                                        if (mAdapter != null) {
                                            mAdapter.notifyDataSetChanged();
                                        }
                                    }
                                    if (response.getData().getRecommend_food()!=null&&response.getData().getRecommend_food().size()!=0){
                                        for(int i=0;i<response.getData().getRecommend_food().size();i++){
                                            list.add(response.getData().getRecommend_food().get(i));
                                        }
                                        recommendList.addAll(list);
                                        stores.add(new Store("餐品"));
                                        commodities.add(list);
                                        if (response.getData().getRecommend_pic()!=null &&response.getData().getRecommend_pic().size()!=0){
                                            for (int i=0;i<response.getData().getRecommend_pic().size();i++){
                                                ImageView iv1 = new ImageView(getActivity());
                                                Glide.with(mContext).load(response.getData().getRecommend_pic().get(i)).placeholder(
                                                        R.drawable.temp_item).into(iv1);
                                                mViewList.add(iv1);
                                            }
                                            switchDotView.generateDots(mViewList.size());
                                        }else {
                                                ImageView iv1 = new ImageView(getActivity());
                                                iv1.setBackgroundResource(R.drawable.top_temp);
                                                mViewList.add(iv1);
                                        }
                                        if(mViewpagerAdapter!=null){
                                            mViewpagerAdapter.notifyDataSetChanged();
                                        }
                                        if (commodityAdapter!=null){
                                            commodityAdapter.notifyDataSetChanged();
                                        }
                                        if (rightViewAdapter==null){
                                            rightViewAdapter = new RightViewAdapter();
                                            defaultRightListView.setAdapter(rightViewAdapter);
                                            for(int i=0; i < rightViewAdapter.getGroupCount(); i++)
                                                defaultRightListView.expandGroup(i);
                                        }else {
                                            rightViewAdapter.notifyDataSetChanged();

                                        }
                                        for(int i = 0; i < mAdapter.getGroupCount(); i++){

                                            expandableListView.expandGroup(i);

                                        }

                                    }
                                }
                                else
                                {
                                    Toast.makeText(mContext, response.getContent(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, (Response.ErrorListener) getActivity());
        request.setLoginuserid(YbbApplication.getInstance().getMyUserInfo().getUserId());
        this.request = request;
        return request;
    }
    @Override
    public View createSuccessView(LayoutInflater inflater) {
        View homeView = inflater.inflate(R.layout.fragment_category,null);
        expandableListView = (ExpandableListView)homeView.findViewById(R.id.category_list);
        rightView = (LinearLayout)homeView.findViewById(R.id.right_view);
        mAdapter = new ExpandAdapter();
        expandableListView.setAdapter(mAdapter);





        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Level temp = group.get(groupPosition);
                if(!temp.equals(lastItem) && child.get(groupPosition) == null){
                    lastItem.selected = false;
                    lastItem = temp;
                    lastItem.selected = true;
                    list.clear();
                    list.addAll(recommendList);
                    mAdapter.notifyDataSetChanged();
                    showDefaultPage();
                }
                return false;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Level temp = child.get(groupPosition).get(childPosition);
                if(!temp.equals(lastItem)){
                    lastItem.selected = false;
                    lastItem = temp;
                    lastItem.selected = true;
                    FoodProductListRequest request = new FoodProductListRequest(new Response.Listener<FoodProductListResponse>() {
                        @Override
                        public void onResponse(FoodProductListResponse response) {
                            Utils.closeDialog();
                            if (response != null && response.getStatus() == 0) {// success
                                list.clear();
                                list.addAll(response.getData().getInfo());
                            }else {
                                list.clear();
                                if (response.getData()!=null){
                                    list.addAll(response.getData().getInfo());
                                }
                                mAdapter.notifyDataSetChanged();
                                showCommodityList();
                                Toast.makeText(mContext,response.getContent()==null ?"网络异常":response.getContent(),Toast.LENGTH_SHORT).show();
                            }
                            mAdapter.notifyDataSetChanged();
                            showCommodityList();
                        }
                    },(Response.ErrorListener) getActivity());
                    request.setHandleCustomErr(false);
                    request.setLoginuserid(YbbApplication.getInstance().getMyUserInfo().getUserId());
                    request.setCategoryId(temp.key);//套餐类型（1营养餐2零点餐3医护套餐4医患套餐）
                    request.setCanteenId(shitangID);
                    request.setAttributeId("");
                    request.setProductId("");
                    request.setKeyword("");
                    request.setCurrentnum("50");//写死不分页 因为不会太多
                    request.setCurrentpage(0 + "");
                    Utils.showProgressDialog(mContext);
                    request.setHandleCustomErr(false);
                    WebUtils.doPost(request);

                }
                return false;
            }
        });
        initListView();
        showDefaultPage();
        return homeView;
    }
    private AutoScrollViewPager autoScrollViewPager;
    private SwitchDotView switchDotView;
    private List<ImageView> mViewList = new ArrayList<ImageView>();
    private ViewpagerAdapter mViewpagerAdapter;

    private void showDefaultPage() {
        rightView.removeAllViews();
        autoScrollViewPager = new AutoScrollViewPager(getActivity());
        switchDotView= new SwitchDotView(getActivity());
        autoScrollViewPager.setInterval(2000);
        autoScrollViewPager.setCycle(true);
        autoScrollViewPager.startAutoScroll();
        mViewpagerAdapter = new ViewpagerAdapter();
        autoScrollViewPager.setAdapter(mViewpagerAdapter);
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
        height = (int)((Utils.getDeviceWidth(getActivity())-Utils.dip2px(getActivity(),100)) /2.7f);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT,
                height);
        width =  Utils.getDeviceWidth()-Utils.dip2px(getActivity(),100);
        params.width =width;
        FrameLayout f = new FrameLayout(getActivity());
        FrameLayout.LayoutParams flp0 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        f.addView(autoScrollViewPager, 0, flp0);
        FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        flp.gravity = Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL;
        flp.bottomMargin = Utils.dip2px(getActivity(),15);
        f.addView(switchDotView, 1, flp);
        rightView.addView(f,0,params);
        rightView.addView(defaultRightListView, 1);
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

    private void showCommodityList() {
        rightView.removeAllViews();
        rightView.addView(categoryList);
    }

    private void initListView(){
        categoryList = new CustomListView(getActivity());
        categoryList.setCacheColorHint(getActivity().getResources().getColor(R.color.transparent));
        categoryList.setDivider(null);
        categoryList.setSelector(R.color.transparent);

        commodityAdapter = new CommodityAdapter(mActivity, list, R.layout.adapter_commodity);
        categoryList.setAdapter(commodityAdapter);
        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Utils.moveTo(getActivity(), CommodityDetailActivity.class);
                Intent intent = new Intent();
                intent.setClass(getActivity(), CommodityDetailActivity.class);
                intent.putExtra("id", list.get(position).getId());
                startActivity(intent);
            }
        });


        defaultRightListView = new CustomExpandableListView(getActivity());
        defaultRightListView.setCacheColorHint(getActivity().getResources().getColor(R.color.transparent));
        defaultRightListView.setDivider(null);
//        defaultRightListView.setDividerHeight(Utils.dip2px(getActivity(), 1));
        defaultRightListView.setSelector(R.color.transparent);
        defaultRightListView.setBackgroundColor(Color.WHITE);
        defaultRightListView.setGroupIndicator(null);
//        rightViewAdapter = new RightViewAdapter();
//        defaultRightListView.setAdapter(rightViewAdapter);
//        for(int i=0; i < rightViewAdapter.getGroupCount(); i++)
//            defaultRightListView.expandGroup(i);
        defaultRightListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        defaultRightListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                Utils.moveTo(getActivity(), CommodityDetailActivity.class);
                Intent intent = new Intent();
                intent.setClass(getActivity(), CommodityDetailActivity.class);
                intent.putExtra("id", list.get(childPosition).getId());
                startActivity(intent);
                return false;
            }
        });
    }


    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    class ExpandAdapter extends BaseExpandableListAdapter{

        @Override
        public int getGroupCount() {
            return group == null? 0:group.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
//            return child.get(groupPosition).size();
            return child.get(groupPosition) == null? 0:child.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return group.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return child.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.adapter_category, parent, false);
            ImageView imageView = (ImageView)convertView.findViewById(R.id.arrow);
            TextView textView = (TextView)convertView.findViewById(R.id.category_display);
            Level1 level1 = group.get(groupPosition);

            if(groupPosition == 0){
                imageView.setVisibility(View.GONE);
            }else{
                imageView.setVisibility(View.VISIBLE);
            }
            if(isExpanded){
                imageView.setBackgroundResource(R.drawable.arrow_up);
//                imageView.setImageResource(R.drawable.arrow_up);
            }else{
                imageView.setBackgroundResource(R.drawable.arrow_down);
//                imageView.setImageResource(R.drawable.arrow_down);
            }
            textView.setText(level1.item);
            if(level1.selected){
                textView.setTextColor(getActivity().getResources().getColor(R.color.light_green));
            }else{
                textView.setTextColor(getActivity().getResources().getColor(R.color.black));
            }
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.adapter_category, parent, false);
            ImageView imageView = (ImageView)convertView.findViewById(R.id.arrow);
            TextView textView = (TextView)convertView.findViewById(R.id.category_display);
            imageView.setVisibility(View.GONE);
            Level2 level2 = child.get(groupPosition).get(childPosition);
            textView.setText(level2.item);
            if(level2.selected){
                textView.setTextColor(getActivity().getResources().getColor(R.color.light_green));
            }else{
                textView.setTextColor(getActivity().getResources().getColor(R.color.black));
            }
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    class Level1 extends Level{
        Level1(String item,String key){
            this.item = item;
            this.key=key;
        }
    }
    class Level2 extends Level{
        Level2(String item,String key){
            this.item = item;
            this.key=key;
        }
    }

    class Level {
        String item;
        String key;
        boolean selected;
    }



    class RightViewAdapter extends BaseExpandableListAdapter{

        @Override
        public int getGroupCount() {
            return stores == null? 0:stores.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
//            return child.get(groupPosition).size();
            return commodities.get(groupPosition) == null? 0:commodities.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return stores.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return commodities.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.adapter_group_commodity, parent, false);
            Store store = stores.get(groupPosition);

            TextView textView = (TextView)convertView.findViewById(R.id.group_name);
            textView.setText(store.name);
            return convertView;
        }

        @Override
        public View getChildView(final int groupPosition,final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.adapter_commodity, parent, false);
            TextView line = (TextView)convertView.findViewById(R.id.line);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)line.getLayoutParams();
            final FoodProductModel commodity = commodities.get(groupPosition).get(childPosition);
            ImageView isColl = (ImageView)convertView.findViewById(R.id.isColl);
            final TextView title = (TextView)convertView.findViewById(R.id.title);
            TextView evaluate_count = (TextView)convertView.findViewById(R.id.evaluate_count);
            TextView commodity_price = (TextView)convertView.findViewById(R.id.commodity_price);
            RatingBar commodity_score=(RatingBar) convertView.findViewById(R.id.commodity_score);
            ImageView item_img=(ImageView)convertView.findViewById(R.id.item_img);
            title.setText(commodity.getShopName()+" "+commodity.getProductName());
            evaluate_count.setText("(" + commodity.getCommentCount() + "人评价)");
            if(StringUtils.isNotEmpty(commodity.getCustomerPrice())){
                commodity_price.setText("￥"+commodity.getCustomerPrice());
            }else {
                commodity_price.setText("￥0");
            }
            if(commodity.getCommentStar()!=null){
                commodity_score.setRating(Float.parseFloat(commodity.getCommentStar()));
            }else {
                commodity_score.setRating(0);
            }
            String imgPath = StringUtils.isEmpty(commodity.getImgStr())?"":commodity.getImgStr().split(",")[0];
            Glide.with(mContext)
                    .load(imgPath)
                    .placeholder(R.drawable.temp_item)
                    .into(item_img);
//                        .onLoadFailed(null, mContext.getResources().getDrawable(R.drawable.temp_item));


            if("1".equals(commodity.getIsColl())){//是否被当前用户收藏，0：未收藏，1：已收藏
                isColl.setBackgroundResource(R.drawable.coll);
            }else{
                isColl.setBackgroundResource(R.drawable.un_coll);
            }
            isColl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if("1".equals(commodity.getIsColl())){//是否被当前用户收藏，0：未收藏，1：已收藏
                        setDelCollectionRequest(commodity.getId(), "3", refresh,groupPosition,childPosition);//收藏信息类型 1:店铺，2:商品，3：餐品，4：资讯
//                        commodity.setIsColl("0");
                    }else{
                        setCollectionRequest(commodity.getId(), "3", refresh,groupPosition,childPosition);
//                        commodity.setIsColl("1");
                    }
                    RightViewAdapter.this.notifyDataSetChanged();
                }
            });
            if(isLastChild){
                params.leftMargin = 0;
            }else{
                params.leftMargin = Utils.dip2px(getActivity(), 13);
            }
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    class Store{
        Store(String name){
            this.name = name;
        }
        String name;
    }

    RefreshCollectListener refresh = new RefreshCollectListener() {
        @Override
        public void refreshView(String flag, int indexP,int indexC) {
            commodities.get(indexP).get(indexC).setIsColl(flag);
            rightViewAdapter.notifyDataSetChanged();

        }
    };
}
