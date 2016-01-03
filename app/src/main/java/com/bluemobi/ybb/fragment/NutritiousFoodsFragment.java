package com.bluemobi.ybb.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.activity.CommodityDetailActivity;
import com.bluemobi.ybb.activity.HomeSearchActivity;
import com.bluemobi.ybb.activity.RestaurantBookingActivity;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.model.CommodityModel;
import com.bluemobi.ybb.network.model.FoodProductModel;
import com.bluemobi.ybb.network.model.ParamModel;
import com.bluemobi.ybb.network.request.FoodProductListRequest;
import com.bluemobi.ybb.network.request.FoodProductListRequest;
import com.bluemobi.ybb.network.response.CommodtiesResponse;
import com.bluemobi.ybb.network.response.FoodProductListResponse;
import com.bluemobi.ybb.util.Constants;
import com.bluemobi.ybb.util.PreferencesService;
import com.bluemobi.ybb.util.StringUtils;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.CustomSpinner;
import com.bluemobi.ybb.view.LoadingPage;
import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by liufy on 2015/6/29.
 * P6营养餐-病患
 */
public class NutritiousFoodsFragment extends BaseFragment {

    private List<String> timeList = new ArrayList<String>();
    private List<String> timeValueList = new ArrayList<String>();
    private List<String> attributeList = new ArrayList<String>();
    private List<String> attributeValueList = new ArrayList<String>();
    private List<String> mainList = new ArrayList<String>();
    private List<String> mainValueList = new ArrayList<String>();
    private CustomSpinner time;
    private CustomSpinner attribute;
    private CustomSpinner mainfood;
    private int sumAddShopCar = 0;

    private PullToRefreshExpandableListView prel_refresh;

    private List<List<String>> childArray;

    private ExpandableAdapter expandableAdapter;

    private List<FoodProductModel> dataList = new ArrayList<FoodProductModel>();

    private String categoryId = "1";

    private int currentPage = 0;
    private int totalPage = 0;
    private boolean flag = false;//标示是不是重新请求了 比如选择 时间 餐次等

    private int cartCounts;

    private String cart_amount;
    private BigDecimal cart_amount_decimal;

    private BaseActivity mBaseActivity;

    private MyBroadcastReceiver mBroadcastReceiver;

    private RelativeLayout rl_search;
    private  EditText et_search;

    @Override
    public void initData(Bundle savedInstanceState) {
        isShowLoadPage = false;
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(Constants.CART_COUNT);
        mBroadcastReceiver = new MyBroadcastReceiver();
        getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mBaseActivity = (BaseActivity) activity;
    }

    @Override
    public void onResume() {
        super.onResume();
        cartCounts = YbbApplication.getInstance().getCartCounts();
        ((BaseActivity) mContext).setShopCarNum(cartCounts, true);
        cart_amount = YbbApplication.getInstance().getCartAmount();
        if (StringUtils.isEmpty(cart_amount))
            cart_amount = "0";
        ((RestaurantBookingActivity) mContext).setCarShopTotal(cart_amount);
        ((BaseActivity) mContext).setShopCarNum(cartCounts, true);
        try {
            PreferencesService.getInstance(mContext).saveInt("cartCounts", cartCounts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void prepare(){
        String startTimeStr = YbbApplication.getInstance().getParamModel().getStartAndEndTime().getEntry().get(0).getValue().getValue();
        String endTimeStr = YbbApplication.getInstance().getParamModel().getStartAndEndTime().getEntry().get(1).getValue().getValue();//2015-07-1111:00:00
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        startTimeStr = startTimeStr.substring(0, 10);
        endTimeStr = endTimeStr.substring(0, 10);
        Date startTimeDate = null;
        try {
            startTimeDate = format.parse(startTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date endTimeDate = null;
        try {
            endTimeDate = format.parse(endTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (startTimeDate.getTime() < (new Date()).getTime()) {
            startTimeDate = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTimeDate);

        while (calendar.getTime().before(endTimeDate)) {
            timeList.add(format.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        timeList.add(endTimeStr);


        List<ParamModel.ProductAttributeEntity> attributeEntities =
                YbbApplication.getInstance().getParamModel().getProductAttributeInfoList();
        if(attributeEntities != null){
            for (ParamModel.ProductAttributeEntity item : attributeEntities) {
                attributeList.add(item.getAttributeName());
                attributeValueList.add(item.getId());

            }
        }
        List<ParamModel.MainFoodEntity> mainEntities =
                YbbApplication.getInstance().getParamModel().getMain_foods();
        if(mainEntities != null){
            for (ParamModel.MainFoodEntity item : mainEntities) {
                mainList.add(item.getCategoryName());
                mainValueList.add(item.getId());
            }
        }
    }

    protected YbbHttpJsonRequest loadRequest() {


        FoodProductListRequest request = new FoodProductListRequest(new Response.Listener<FoodProductListResponse>() {
            @Override
            public void onResponse(FoodProductListResponse response) {
                if (response != null && response.getStatus() == 0) {
                    if (response.getData().getInfo() != null) {
                        dataList.clear();
                        dataList.addAll(response.getData().getInfo());
                        Logger.e("wangzhijun", "CommodtiesResponse " + response.getData().getInfo().size());
                        expandableAdapter.notifyDataSetChanged();
//                        currentPage=Integer.valueOf(response.getData().getCurrentpage());
                        totalPage = Integer.valueOf(response.getData().getTotalpage());
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.e("wangzhijun", "error");
            }
        });
        request.setCurrentnum(Constants.CURRENTNUMBASE + "");
        request.setCurrentpage("0");
        if (attributeValueList.size() != 0) {
            request.setAttributeId(attributeValueList.get(0));
        }
//        request.setAttributeId("8aba20e84ef857e1014ef85af5940003");
        if (mainValueList.size() != 0) {
            request.setProductId(mainValueList.get(0));
        }
        request.setLoginuserid(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setCategoryId("1");
        request.setCanteenId(YbbApplication.getInstance().getCanteenId());
        request.setQueryTime(timeList.get(0));
        WebUtils.doPost(request);
        return request;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {
        prepare();
        View homeView = inflater.inflate(R.layout.fragment_nutritious_food, null);
        prel_refresh = (PullToRefreshExpandableListView) homeView.findViewById(R.id.prel_refresh);
        rl_search=(RelativeLayout)homeView.findViewById(R.id.rl_search);
        et_search=(EditText)homeView.findViewById(R.id.et_search);
//        rl_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(YbbApplication.role_bh.equals(YbbApplication.getInstance().getMyUserInfo().getTypeId())){
//                    Utils.moveTo(mContext, HomeSearchActivity.class);
//                }else if(YbbApplication.role_yh.equals(YbbApplication.getInstance().getMyUserInfo().getTypeId())){
//                    Intent intent = new Intent(mContext,HomeSearchActivity.class);
//                    intent.putExtra("From","P7");
//                    startActivity(intent);
//                }
//            }
//        });
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                // Auto-generated method stub
                if ((arg1 == 0 || arg1 == 3) && arg2 != null) {
                    currentPage = 0;
                    flag = true;
                    connectToServer();
                }
                return false;
            }
        });
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (s.length() < 1) {
//                    groupArray.clear();
//                    groupArray.addAll(searchDatas);
//                    mAdapter.notifyDataSetChanged();
//                    search.setVisibility(View.VISIBLE);
//                    t1.setVisibility(View.VISIBLE);
//                } else {
//                    search.setVisibility(View.INVISIBLE);
//                    t1.setVisibility(View.INVISIBLE);
//                }
            }
        });
        time = (CustomSpinner) homeView.findViewById(R.id.cs_breakfast);
        attribute = (CustomSpinner) homeView.findViewById(R.id.cs_lunch);
        mainfood = (CustomSpinner) homeView.findViewById(R.id.cs_dinner);
        time.setDatas(timeList);
        time.setValues(timeList);
        Logger.e("wangzhijun", attributeList.size() + "");
        Logger.e("wangzhijun", attributeValueList.size() + "");
        attribute.setDatas(attributeList);
        attribute.setValues(attributeValueList);

        mainfood.setDatas(mainList);
        mainfood.setValues(mainValueList);

        time.setSpinnerText();
        attribute.setSpinnerText();
        mainfood.setSpinnerText();

        time.setSelectLine();
        time.setSnipperClickListener(new CustomSpinner.OnSnipperClickListener() {
            @Override
            public void OnSnipperClick(int position) {
                currentPage = 0;
                flag = true;
                connectToServer();
            }
        });
        time.setOnSwitchTabClickListener(new CustomSpinner.OnSwitchTabClickListener() {
            @Override
            public void OnSwitchTabClick() {
                clearSelectLine();
                time.setSelectLine();
            }
        });
        attribute.setSnipperClickListener(new CustomSpinner.OnSnipperClickListener() {
            @Override
            public void OnSnipperClick(int position) {
                currentPage = 0;
                flag = true;
                connectToServer();
            }
        });
        attribute.setOnSwitchTabClickListener(new CustomSpinner.OnSwitchTabClickListener() {
            @Override
            public void OnSwitchTabClick() {
                clearSelectLine();
                attribute.setSelectLine();
            }
        });
        mainfood.setSnipperClickListener(new CustomSpinner.OnSnipperClickListener() {
            @Override
            public void OnSnipperClick(int position) {
                currentPage = 0;
                flag = true;
                connectToServer();
            }
        });
        mainfood.setOnSwitchTabClickListener(new CustomSpinner.OnSwitchTabClickListener() {
            @Override
            public void OnSwitchTabClick() {
                clearSelectLine();
                mainfood.setSelectLine();
            }
        });
        initExpandPullToRefresh(prel_refresh);
        expandableAdapter = new ExpandableAdapter();
        prel_refresh.getRefreshableView().setGroupIndicator(null);
        prel_refresh.getRefreshableView().setAdapter(expandableAdapter);
        prel_refresh.getRefreshableView().setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), CommodityDetailActivity.class);
                intent.putExtra("id", dataList.get(groupPosition).getId());
                intent.putExtra("reserveTime", time.getValue());
                intent.putExtra("imgPath", dataList.get(groupPosition).getImgList().get(0));
                intent.putExtra("sumAddShopCar", sumAddShopCar);
                intent.putExtra("timeValue", time.getValue());
                intent.putExtra("categoryId", dataList.get(groupPosition).getCombogroup_categoryId());
                intent.putExtra("attributeId", dataList.get(groupPosition).getAttributeId());

                startActivity(intent);
                return true;
            }
        });
        prel_refresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ExpandableListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> pullToRefreshBase) {
                new Handler().postDelayed(new Runnable() {//下拉
                    @Override
                    public void run() {
                        currentPage = 0;
                        flag = false;
                        connectToServer();
                    }
                }, 500);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> pullToRefreshBase) {
                new Handler().postDelayed(new Runnable() {//上拉
                    @Override
                    public void run() {
                        flag = false;
                        if (totalPage > currentPage + 1) {
                            currentPage++;
                            connectToServer();
                        } else {
                            prel_refresh.onRefreshComplete();
                            Utils.makeToastAndShow(getActivity(), "已经没有更多记录", Toast.LENGTH_SHORT);
                        }

                    }
                }, 500);
            }
        });
        loadRequest();
        return homeView;
    }

    private void connectToServer() {
        FoodProductListRequest request = new FoodProductListRequest(new Response.Listener<FoodProductListResponse>() {
            @Override
            public void onResponse(FoodProductListResponse response) {
                Utils.closeDialog();
                prel_refresh.onRefreshComplete();
                if (response != null && response.getStatus() == 0) {
                    if (response.getData().getInfo() != null) {
                        if (response.getData().getCurrentpage().equals("1")) {
                            dataList.clear();
                        }
                        totalPage = Integer.valueOf(response.getData().getTotalpage());
                        dataList.addAll(response.getData().getInfo());
                        expandableAdapter.notifyDataSetChanged();
                    }
                } else {
                    Utils.closeDialog();
                    prel_refresh.onRefreshComplete();
                    if (flag) {
                        dataList.clear();
                        expandableAdapter.notifyDataSetChanged();
                    }
                    Toast.makeText(mContext, response.getContent(), Toast.LENGTH_SHORT).show();
                }

            }
        }

                , mBaseActivity);
        request.setHandleCustomErr(false);
        request.setKeyword(et_search.getText().toString());
        request.setCurrentnum(Constants.CURRENTNUMBASE + "");
        request.setPageTime("");
        request.setCurrentpage(currentPage + "");
        request.setAttributeId(attribute.getValue());
//        request.setAttributeId("8aba20e84ef857e1014ef85af5940003");
        request.setProductId(mainfood.getValue());
        request.setLoginuserid(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setCategoryId("1");
        request.setCanteenId(YbbApplication.getInstance().getCanteenId());
        request.setQueryTime(time.getSpinnerText());
        Utils.showProgressDialog(mContext);
        WebUtils.doPost(request);
    }


    private void clearSelectLine() {
        mainfood.clearSelectLine();
        time.clearSelectLine();
        attribute.clearSelectLine();
    }

    private boolean isExpand = false;

    class ExpandableAdapter extends BaseExpandableListAdapter {
        Activity activity;

        LayoutInflater inflater;

        public ExpandableAdapter() {
            inflater = (LayoutInflater) mContext.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
        }

        public Object getChild(int groupPosition, int childPosition) {
            return childArray == null ? null : childArray.get(groupPosition) == null ? null :
                    childArray.get(groupPosition).get(childPosition);
        }

        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        public int getChildrenCount(int groupPosition) {

            return 1;


        }

        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {


            convertView = inflater.inflate(
                    R.layout.lv_nutritious_food_child_item, parent, false);
            ImageView iv_image_bg = (ImageView) convertView.findViewById(R.id.iv_image_bg);
            TextView tv_target_date_text = (TextView) convertView.findViewById(R.id.tv_target_date_text);
            TextView tv_several_meals_text = (TextView) convertView.findViewById(R.id.tv_several_meals_text);
            TextView tv_cooking_stove_text = (TextView) convertView.findViewById(R.id.tv_cooking_stove_text);
            tv_target_date_text.setText(time.getValue());
            tv_several_meals_text.setText(attribute.getDis());
            tv_cooking_stove_text.setText(mainfood.getDis());
            if (StringUtils.isNotEmpty(dataList.get(groupPosition).getImgList().get(0)) && !"null".equals(dataList.get(groupPosition).getImgPath())) {
                Glide.with(mContext).load(dataList.get(groupPosition).getImgList().get(0))
                        .placeholder(R.drawable.temp_item).into(iv_image_bg);
            } else {
                iv_image_bg.setImageResource(R.drawable.lv_item_image_bg);
            }
            return convertView;
        }

        // group method stub
        public Object getGroup(int groupPosition) {
            return dataList == null ? null : dataList.get(groupPosition);
        }

        public int getGroupCount() {
            return dataList == null ? 0 : dataList.size();
        }

        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        public View getGroupView(final int groupPosition, final boolean isExpanded,
                                 View convertView, ViewGroup parent) {

            final FoodProductModel model = dataList.get(groupPosition);
            convertView = inflater.inflate(
                    R.layout.lv_nutritious_food_item, parent, false);
            ImageView iv_search_more = (ImageView) convertView.findViewById(R.id.iv_search_more);
            ImageView iv_image_bg = (ImageView) convertView.findViewById(R.id.iv_image_bg);
            TextView tv_search_item = (TextView) convertView.findViewById(R.id.tv_search_item);
            ImageView coll = (ImageView) convertView.findViewById(R.id.isColl);
            if ("1".equals(model.getIsColl())) {//是否被当前用户收藏，0：未收藏，1：已收藏
                coll.setBackgroundResource(R.drawable.coll);
            } else {
                coll.setBackgroundResource(R.drawable.un_coll);
            }
            coll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("1".equals(model.getIsColl())) {//是否被当前用户收藏，0：未收藏，1：已收藏
                        mBaseActivity.setDelCollectionRequest(model.getId(), "3", refresh, groupPosition, -1);//收藏信息类型 1:店铺，2:商品，3：餐品，4：资讯

                    } else {
                        mBaseActivity.setCollectionRequest(model.getId(), "3", refresh, groupPosition, -1);
                    }
                }
            });
            tv_search_item.setText(model.getProductName());

            if (StringUtils.isNotEmpty(model.getImgPath()) && !"null".equals(model.getImgPath())) {
                Glide.with(mContext).load(model.getImgPath()).placeholder(R.drawable.temp_item).into(iv_image_bg);
            } else {
                iv_image_bg.setImageResource(R.drawable.lv_item_image_bg);
            }
            RatingBar rb_comment = (RatingBar) convertView.findViewById(R.id.rb_comment);
            if (StringUtils.isNotEmpty(model.getCommentStar())) {
                rb_comment.setRating(Float.parseFloat(model.getCommentStar()));
            } else {
                rb_comment.setRating(0);
            }

            TextView reviewCount = (TextView) convertView.findViewById(R.id.reviewCount);
            reviewCount.setText("(" + model.getCommentCount() + ")");

            TextView tv_charge = (TextView) convertView.findViewById(R.id.tv_charge);

//            final double price = Double.parseDouble(
//                    StringUtils.isEmpty(model.getComboPrice())?"0":model.getComboPrice());
            tv_charge.setText("￥" + model.getCustomerPrice());

            ImageView iv_add = (ImageView) convertView.findViewById(R.id.iv_add);
            ImageView minus = (ImageView) convertView.findViewById(R.id.minus);
            final EditText et_hint = (EditText) convertView.findViewById(R.id.et_hint);
            et_hint.setClickable(false);
            //TODO
            iv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((BaseActivity) mContext).setStartPoint(v);
                    ((BaseActivity) mContext).setAnim();
                    int num = Integer.parseInt(et_hint.getText().toString());
                    addShopCarRequest(num, model.getId(), time.getValue(), Constants.FOODS_TYPE, false,
                            ((RestaurantBookingActivity) mContext).getTv_shopcar_total(), model.getCustomerPrice(),
                            ((RestaurantBookingActivity) mContext), categoryId
                    ,model.getAttributeId());
                }
            });

            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int num = Integer.parseInt(et_hint.getText().toString());
                    addShopCarRequest(-num, model.getId(), time.getValue(), Constants.FOODS_TYPE,true,
                            ((RestaurantBookingActivity) mContext).getTv_shopcar_total(),
                            model.getCustomerPrice(), ((RestaurantBookingActivity) mContext), categoryId,
                            model.getAttributeId());

                }
            });

            iv_search_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    isExpand = !isExpand;
                    if (isExpand) {
                        prel_refresh.getRefreshableView().expandGroup(groupPosition);
                    } else {
                        prel_refresh.getRefreshableView().collapseGroup(groupPosition);
                    }

                }
            });

            return convertView;
        }

        BaseActivity.RefreshCollectListener refresh = new BaseActivity.RefreshCollectListener() {
            @Override
            public void refreshView(String flag, int indexP, int indexC) {
                dataList.get(indexP).setIsColl(flag);
                ExpandableAdapter.this.notifyDataSetChanged();
            }
        };

        class ViewHolder {

        }

        class ViewHolderChild {

        }


        public boolean hasStableIds() {
            return false;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }


    protected class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(Constants.CART_COUNT)){
//                Toast.makeText(Test.this, "处理action名字相对应的广播", 200);
                cartCounts = intent.getIntExtra("count",cartCounts);
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
