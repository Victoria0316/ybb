package com.bluemobi.ybb.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.adapter.HorizontalAdapter;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.network.model.FoodProductDetailModel;
import com.bluemobi.ybb.network.model.OrderMakeModel;
import com.bluemobi.ybb.network.model.ParamModel;
import com.bluemobi.ybb.network.model.ProductInfoDTOs;
import com.bluemobi.ybb.network.request.CollectionRequest;
import com.bluemobi.ybb.network.request.DelCollectionRequest;
import com.bluemobi.ybb.network.request.FoodProductDetailRequest;
import com.bluemobi.ybb.network.response.CollectionResponse;
import com.bluemobi.ybb.network.response.DelCollectionResponse;
import com.bluemobi.ybb.network.response.FoodProductDetailResponse;
import com.bluemobi.ybb.util.StringUtils;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.AutoScrollViewPager;
import com.bluemobi.ybb.view.ExtRecommendView;
import com.bluemobi.ybb.view.HorizontalListView;
import com.bluemobi.ybb.view.LoadingPage;
import com.bluemobi.ybb.view.SwitchDotView;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 餐品详情
 * Created by wangzhijun on 2015/7/7.
 */
public class CommodityDetailActivity extends BaseActivity implements View.OnClickListener {
    private final static String tag = "CommodityDetailActivity";

    private TitleBarManager titleBarManager;

    private AutoScrollViewPager autoScrollViewPager;


    private List<ImageView> mViewList = new ArrayList<ImageView>();

    private ViewpagerAdapter mAdapter;

    private SwitchDotView switchDotView;

    private int currPosition;

    private ExtRecommendView extRecommendView;

    private TextView more_reviews;

    private String id;
    /**
     * 预定日期
     */
    private String reserveTime;

    //    口味正宗
    private TextView title;
    //    早餐
    private TextView breakfast;
    //    回民灶
    private TextView breakfast_below;
    //    ￥30.00
    private TextView price_now;
    //    ￥50.00
    private TextView price_old;
    //    2015-7-1~2015-08-15
    private TextView time;
    //    月售300
    private TextView commodity_count;
    //    评价头像
    private ImageView user_avatar;
    //    2231xw
    private TextView user_nickname;
    // 评论线
    private TextView reviews_line;
    //    还不错还不错
    private TextView user_reviews;
    //    主食
    private TextView commodity_detail_main_all;

    private int sumAddShopCar;
    private RatingBar score;

    private TextView tv_add_shop;
    private HorizontalListView horizontalListView;

    private HorizontalAdapter horizontalAdapter;

    private List<ProductInfoDTOs> mListData = new ArrayList<ProductInfoDTOs>();

    private int HorizontalHeight = 300;

    private TextView coll_count;
    private String productName;
    private String attributeName;
    private String customerPrice;
    private String imagePath;
    private String timeValue;

    private FoodProductDetailModel data;

    private String imgPath;

    private ImageView common_back;

    private ImageView btn_coll;
    private RelativeLayout rl_collect;

    private String isColl = "0"; //0：未收藏，1：已收藏",

    private boolean isCollB;

    private int count;
    /**
     * 套餐类型（1营养餐2零点餐3医护套餐4医患套餐）
     */
    private String categoryId;
    /**
     * 餐次
     */
    private String attributeId;


    public CommodityDetailActivity() {
    }


    @Override
    protected void initBaseData() {

        id = getIntent().getStringExtra("id");
        imgPath = getIntent().getStringExtra("imgPath");
        Logger.d(tag, "articleId:" + id);
        sumAddShopCar = getIntent().getIntExtra("sumAddShopCar", 0);
        timeValue = getIntent().getStringExtra("timeValue");
        categoryId = getIntent().getStringExtra("categoryId");
        attributeId = getIntent().getStringExtra("attributeId");

        Logger.d(tag, "sumAddShopCar:" + sumAddShopCar);
        reserveTime = getIntent().getStringExtra("reserveTime");
        if (reserveTime == null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String date = format.format(new Date());
            reserveTime = date;
        }
    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.activity_commodity_detail, null);

        title = (TextView) view.findViewById(R.id.title);
        breakfast = (TextView) view.findViewById(R.id.breakfast);
        breakfast_below = (TextView) view.findViewById(R.id.breakfast_below);
        price_now = (TextView) view.findViewById(R.id.price_now);
        price_old = (TextView) view.findViewById(R.id.price_old);
        time = (TextView) view.findViewById(R.id.time);
        commodity_count = (TextView) view.findViewById(R.id.commodity_count);
        user_avatar = (ImageView) view.findViewById(R.id.user_avatar);
        user_nickname = (TextView) view.findViewById(R.id.user_nickname);
        reviews_line = (TextView) view.findViewById(R.id.reviews_line);
        user_reviews = (TextView) view.findViewById(R.id.user_reviews);
        more_reviews = (TextView) view.findViewById(R.id.more_reviews);
        commodity_detail_main_all = (TextView) view.findViewById(R.id.commodity_detail_main_all);
        score = (RatingBar) view.findViewById(R.id.score);
        coll_count = (TextView) view.findViewById(R.id.coll_count);
        btn_coll = (ImageView) view.findViewById(R.id.btn_coll);
        rl_collect = (RelativeLayout) view.findViewById(R.id.rl_collect);



        horizontalListView = (HorizontalListView) view.findViewById(R.id.horizontalListView);


        horizontalListView.getLayoutParams().height = HorizontalHeight;

        horizontalAdapter = new HorizontalAdapter(this, mListData);
        horizontalListView.setAdapter(horizontalAdapter);


        switchDotView = (SwitchDotView) view.findViewById(R.id.swicth_dots);
        autoScrollViewPager = (AutoScrollViewPager) view.findViewById(R.id.auto_viewpaper);
        tv_add_shop = (TextView) view.findViewById(R.id.tv_add_shop);
        tv_add_shop.setOnClickListener(this);
        autoScrollViewPager.setInterval(2000);
        autoScrollViewPager.setCycle(true);
        autoScrollViewPager.startAutoScroll();
//        autoScrollViewPager.setBackgroundResource(R.drawable.temp_item);

        mAdapter = new ViewpagerAdapter();
        autoScrollViewPager.setAdapter(mAdapter);
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
        view.findViewById(R.id.btn_buy_now).setOnClickListener(this);

//        获取营养餐详情 接口请求
        NutritiousFoodDetailsRequest();

        return view;
    }

    private void NutritiousFoodDetailsRequest() {

        FoodProductDetailRequest request = new FoodProductDetailRequest(new Response.Listener<FoodProductDetailResponse>() {
            @Override
            public void onResponse(FoodProductDetailResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    Logger.d(tag, "获取营养餐详情 ok");

                    data = response.getData();

                    isColl = response.getData().getIsColl(); //0：未收藏，1：已收藏",
                    if ("1".equals(isColl)) {
                        isCollB = true;
                        btn_coll.setBackgroundResource(R.drawable.coll);
                    } else {
                        isCollB = false;
                        btn_coll.setBackgroundResource(R.drawable.un_coll);
                    }
                    rl_collect.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Logger.d(tag, "你单击了收藏");

                            if (!isCollB) {  //收藏

                                CollectionRequest request = new CollectionRequest(new Response.Listener<CollectionResponse>() {
                                    @Override
                                    public void onResponse(CollectionResponse response) {
                                        Utils.closeDialog();
                                        if (response != null && response.getStatus() == 0) {// success
                                            Utils.makeToastAndShow(mContext, "收藏成功");
                                            btn_coll.setImageResource(R.drawable.coll);
//                                            NutritiousFoodDetailsRequest();
                                            isCollB = !isCollB;
                                            count++;
                                            coll_count.setText(String.valueOf(count));

                                        } else {// failed
                                            Log.e("error", "error");
                                        }
                                    }
                                }, CommodityDetailActivity.this);
                                request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
                                request.setCollectionId(id);
                                request.setCollectionType("3");//收藏信息类型 1:店铺，2:商品，3：餐品，4：资讯
                                Utils.showProgressDialog(mContext);
                                WebUtils.doPost(request);

                            } else {  //取消收藏

                                DelCollectionRequest request = new DelCollectionRequest(new Response.Listener<DelCollectionResponse>() {
                                    @Override
                                    public void onResponse(DelCollectionResponse response) {
                                        Utils.closeDialog();
                                        if (response != null && response.getStatus() == 0) {// success
                                            Utils.makeToastAndShow(mContext, "取消收藏");
                                            btn_coll.setImageResource(R.drawable.un_coll);
//                                            NutritiousFoodDetailsRequest();
                                            isCollB = !isCollB;
                                            count--;
                                            coll_count.setText(String.valueOf(count));

                                        } else {// failed
                                            Log.e("error", "error");
                                        }
                                    }
                                }, CommodityDetailActivity.this);
                                request.setUserid(YbbApplication.getInstance().getMyUserInfo().getUserId());
                                request.setCollectionId(id);
                                request.setCollectionType("3"); //收藏信息类型 1:店铺，2:商品，3：餐品，4：资讯
                                Utils.showProgressDialog(mContext);
                                WebUtils.doPost(request);

                            }
                        }
                    });
                    productName = response.getData().getProductName();
                    attributeName = response.getData().getAttributeName();
                    customerPrice = response.getData().getCustomerPrice();
                    imagePath = response.getData().getImgList().get(0);
                    title.setText(response.getData().getProductName());
                    breakfast.setText(response.getData().getAttributeName());
                    /*if (response.getData().getProductInfoDTOs()!=null&&response.getData().getProductInfoDTOs().size()!=0){
                        if (response.getData().getProductInfoDTOs().get(0).getProductCategoryManyList()!=null&&response.getData().getProductInfoDTOs().get(0).getProductCategoryManyList().size()!=0) {
                            if(StringUtils.isNotEmpty(response.getData().getProductInfoDTOs().get(0).getProductCategoryManyList().get(0).getCategoryName())&&!"null".equals(response.getData().getProductInfoDTOs().get(0).getProductCategoryManyList().get(0).getCategoryName())){
                                breakfast_below.setText(response.getData().getProductInfoDTOs().get(0).getProductCategoryManyList().get(0).getCategoryName());
                            }else {
                                breakfast_below.setText("");
                            }
                        }
                    }*/
                    if ("2".equals(response.getData().getCategoryId())){
                        breakfast.setVisibility(View.INVISIBLE);
                    }
                    price_now.setText("￥" + response.getData().getCustomerPrice());
                    price_old.setText("￥" + response.getData().getSellPrice());
                    StringBuffer buffer = new StringBuffer();
                    if(StringUtils.isNotEmpty(response.getData().getStartTime()) ){
                        buffer.append(response.getData().getStartTime());
                        buffer.append("~");
                    }
                    if(StringUtils.isNotEmpty(response.getData().getEndTime()) ){
                        buffer.append(response.getData().getEndTime());
                    }
                    time.setText(buffer.toString());

                    if (StringUtils.isNotEmpty(response.getData().getSaleCount())) {
                        commodity_count.setText("月售 " + response.getData().getSaleCount());
                    } else {
                        commodity_count.setText("月售 0");
                    }
                    if(response.getData().getCommentInfoDTO() == null ||
                            StringUtils.isEmpty(response.getData().getCommentInfoDTO().getId())){
                        user_avatar.setVisibility(View.GONE);
                        user_nickname.setVisibility(View.GONE);
                        user_reviews.setVisibility(View.GONE);
                        reviews_line.setVisibility(View.GONE);
                        more_reviews.setText("暂无评论");
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)more_reviews.getLayoutParams();
                        params.topMargin = Utils.dip2px(CommodityDetailActivity.this,
                                10);
                    }else {
                        more_reviews.setOnClickListener(CommodityDetailActivity.this);
                    }
                    if (response.getData().getCommentInfoDTO() != null && StringUtils.isNotEmpty(response.getData().getCommentInfoDTO().getImg())) {
                        Glide.with(CommodityDetailActivity.this)
                                .load(response.getData().getCommentInfoDTO().getImg())
                                .placeholder(R.drawable.mine_phote_bg)
                                .into(user_avatar);//评价头像目前没有图片加载
                    }
                    //======================================
                    String disName = "";
                    if (StringUtils.isNotEmpty(response.getData().getCommentInfoDTO().getUserName())) {
                        if (response.getData().getCommentInfoDTO().getUserName().length() > 1) {
                            int l = response.getData().getCommentInfoDTO().getUserName().length();
                            StringBuffer star = new StringBuffer();
                            for (int i = 0; i < l - 1; i++) {
                                star.append("*");
                            }
                            disName = response.getData().getCommentInfoDTO().getUserName().substring(0, 1) + star;
                        } else {
                            disName = response.getData().getCommentInfoDTO().getUserName();
                        }
                    }
                    user_nickname.setText(disName);
                    //======================================
                    user_reviews.setText(response.getData().getCommentInfoDTO().getContent());
                    String temp = response.getData().getCommentStar();
                    score.setRating(Float.parseFloat(StringUtils.isEmpty(temp) ? "0" : temp));
                    count = Integer.parseInt(StringUtils.isEmpty(response.getData().getCollectCount()) ? "0" :
                            response.getData().getCollectCount());
                    coll_count.setText(String.valueOf(count));

                    mViewList.clear();
                    if (response.getData().getImgList() == null || response.getData().getImgList().size() < 1) {
                        ImageView iv1 = new ImageView(CommodityDetailActivity.this);
                        iv1.setBackgroundResource(R.drawable.temp_item);
                        iv1.setScaleType(ImageView.ScaleType.FIT_XY);
                        mViewList.add(iv1);
                    } else {
                        for (int i = 0; i < response.getData().getImgList().size(); i++) {
                            ImageView iv1 = new ImageView(CommodityDetailActivity.this);
                            iv1.setScaleType(ImageView.ScaleType.FIT_XY);
//                        iv1.setImageResource(R.drawable.temp_item);
                            Glide.with(CommodityDetailActivity.this)
                                    .load(response.getData().getImgList().get(0).toString())
                                    .placeholder(R.drawable.temp_item)
                                    .into(iv1);
                            mViewList.add(iv1);
                        }
                    }

                    mAdapter.notifyDataSetChanged();

                    mListData.clear();
//mListData.addAll(response.getData().getProductInfoDTOs());
                    horizontalAdapter.notifyDataSetChanged();

                    String main_food = "";
//for (int i = 0; i < response.getData().getProductInfoDTOs().size(); i++) {
//    main_food += response.getData().getProductInfoDTOs().get(i).getProductName() + " ";
//}
                    commodity_detail_main_all.setText(StringUtils.isEmpty(
                            response.getData().getProductBurdening())?"":
                            response.getData().getProductBurdening());

                } else {
                    Utils.makeToastAndShow(getBaseContext(), response.getMsg());
                }


            }
        }, CommodityDetailActivity.this);

        request.setLoginuserid(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setId(id);
        request.setAttributeId(attributeId);
        request.setCategoryId(categoryId);
        Utils.showProgressDialog(this);
        WebUtils.doPost(request);
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        titleBarManager = new TitleBarManager();
//        titleBarManager.init(this, getSupportActionBar());
//        titleBarManager.showSearchTitleBar();
//        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.commodity_detail_title, R.drawable.common_back, true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        showLoadingPage(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_reviews:
                Intent i = new Intent(this, ReviewsActivity.class);
                i.putExtra("id", id);
                startActivity(i);
//                Utils.moveTo(this, ReviewsActivity.class);
                break;
            case R.id.btn_buy_now://立即购买
                if(!checkPreDate()){//不符合订餐时间
                    break;
                }
                Intent intent = new Intent();
                intent.setClass(this, OrderMakeActivity.class);
                OrderMakeModel model = new OrderMakeModel();
                model.setId(data.getId());
                model.setCategoryId(data.getCombogroup_categoryId());
                model.setImg(imgPath);
                model.setCount("1");
                model.setName(data.getProductName());
                model.setTitle(data.getProductName());
                model.setPrice(data.getCustomerPrice());
                model.setReserveTime(reserveTime);
                model.setAttributeId(data.getAttributeId());
                intent.putExtra("item", model);
                startActivity(intent);
                break;
            case R.id.tv_add_shop://添加购物车
                // Utils.moveTo(this, AddShopCarActivity.class);
                Intent intent1 = new Intent(mContext, AddShopCarActivity.class);
                OrderMakeModel model1 = new OrderMakeModel();
                model1.setId(data.getId());
                model1.setCategoryId(data.getCombogroup_categoryId());
                model1.setImg(imgPath);
                model1.setCount("1");
                model1.setName(data.getProductName());
                model1.setTitle(data.getProductName());
                model1.setPrice(data.getCustomerPrice());
                model1.setReserveTime(reserveTime);
                model1.setAttributeId(data.getAttributeId());
                intent1.putExtra("item", model1);
                intent1.putExtra("attributeName", attributeName);
                startActivity(intent1);
                break;

        }
    }

    /**
     *    if("1".equals(categoryId)){
     category = "营养餐";
     }if("2".equals(categoryId)){
     category = "零点餐";
     }if("3".equals(categoryId)){
     category = "医护套餐";
     }if("4".equals(categoryId)){
     category = "医患套餐";
     }
     零点餐不做比较，其他套餐 按照餐次时间比较
     */
    private boolean checkPreDate() {
        boolean temp = false;
        if("2".equals(data.getCategoryId())){
            temp = true;
        }else{
            HashMap<String, ParamModel.ProductAttributeEntity> map = YbbApplication.getInstance().getAttributeEntityHashMap();
            ParamModel.ProductAttributeEntity item = map.get(data.getAttributeId());
            if(item == null){
                return true;
            }
            String min = item.getMinValue();
            if(StringUtils.isEmpty(min) || !min.contains(":")){
                return true;
            }
            String[] time = min.split(":");
            int hour = Integer.parseInt(time[0]);
            int minute = Integer.parseInt(time[1]);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = null;//预定时间
            try {
                date = format.parse(reserveTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar pre = Calendar.getInstance();
            pre.setTime(date);
Logger.e("wangzhijun", "Calendar-->" + pre.getTime().toString());
            pre.set(pre.get(Calendar.YEAR), pre.get(Calendar.MONTH),pre.get(Calendar.DAY_OF_MONTH),
                    hour, minute);
Logger.e("wangzhijun", pre.getTime().toString());
            if(StringUtils.isNotEmpty(item.getOrderingTimeType()) &&
                    StringUtils.isNotEmpty(item.getOrderingTime())){
                if("1".equals(item.getOrderingTimeType())){//小时
                    pre.add(Calendar.HOUR, -Integer.parseInt(item.getOrderingTime()));
                }else{
                    pre.add(Calendar.DATE, -Integer.parseInt(item.getOrderingTime()));
                }
            }
Logger.e("wangzhijun", pre.getTime().toString());
            Calendar current = Calendar.getInstance();
            if(current.before(pre)){
                temp = true;
            }
            if(!temp){
                Utils.makeToastAndShow(getBaseContext(), "请在" + format2.format(pre.getTime()) +  "之前下单");
            }
        }
        return temp;
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
