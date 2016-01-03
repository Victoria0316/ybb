package com.bluemobi.ybb.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.adapter.OrderDetailAdapter;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.model.OrderDetailAttribute;
import com.bluemobi.ybb.network.model.OrderDetailChild;
import com.bluemobi.ybb.network.model.OrderDetailData;
import com.bluemobi.ybb.network.model.OrderMakeModel;
import com.bluemobi.ybb.network.request.OrderDetailRequest;
import com.bluemobi.ybb.network.response.OrderDetailResponse;
import com.bluemobi.ybb.util.StringUtils;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.view.CustomListView;
import com.bluemobi.ybb.view.LoadingPage;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * P14-2订单详情
 * liufy
 */
public class OrderDetailsActivity extends BaseActivity implements View.OnClickListener {

    private CustomListView commoditiesList;

    private OrderDetailAdapter mAdaprer;

    private ScrollView scrollView;

    private String orderId;

    private TextView tv_order_no;
    private TextView order_time;
    private TextView tv_order_state;
    private TextView receiver_name;
    private TextView receiver_phone;
    private TextView receiver_detail;
    private TextView total_amount;
    private TextView sender;
    private TextView time;
    private ArrayList<OrderMakeModel> dataLists = new ArrayList<OrderMakeModel>();
    private BigDecimal totalAmountDecimal;
    private ImageView right_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.order_make_title, R.drawable.common_back, true);
//        showLoadingPage(false);
    }

    @Override
    protected YbbHttpJsonRequest initRequest() {
        orderId = getIntent().getStringExtra("orderId");
        OrderDetailRequest request = new OrderDetailRequest(new Response.Listener<OrderDetailResponse>() {
            @Override
            public void onResponse(OrderDetailResponse response) {
                Utils.closeDialog();
                if(response != null && response.getStatus() ==0){
                    Logger.e("wagnzhijun", "responsegetStatus==0");
                    refreshView(response.getData());
                }
            }
        }, this);
        request.setOrderId(orderId);
        Utils.showProgressDialog(this);
        return request;
    }

    private void refreshView(OrderDetailData data) {
        tv_order_no.setText("订单号：" + data.getOrderNo());
        order_time.setText(data.getCreateTime());
        time.setText(data.getReserveTime());
        tv_order_state.setText(getOrderStatus(data.getOrderStatus()));
        receiver_name.setText(data.getCustomerName());
        receiver_phone.setText(data.getCustomerTelephone());
        StringBuffer bf = new StringBuffer();
//        if(StringUtils.isNotEmpty(data.getCustomerProvinceName())){
//            bf.append(data.getCustomerProvinceName());
//        }
//        if(StringUtils.isNotEmpty(data.getCustomerCityName())){
//            bf.append(data.getCustomerCityName());
//        }
        if(StringUtils.isNotEmpty(data.getCustomerAddress())){
            bf.append(data.getCustomerAddress());
        }
        receiver_detail.setText(bf.toString());
        sender.setText(data.getLogisticsDistributionInfoDTOList().get(0).getDeliverymanRealName());
        processDataAndLoad(data);

    }

    private void processDataAndLoad(OrderDetailData data) {
        dataLists.clear();
        ArrayList<OrderDetailAttribute> list1 = data.getLogisticsDistributionInfoDTOList();
        int size = list1.size();
        for(int i=0; i< size; i++) {
            OrderDetailAttribute item = list1.get(i);
            ArrayList<OrderDetailChild> list2 = item.getProductInfoDTOList();
            int size2 = list2.size();
            for(int j=0; j< size2; j++){
                OrderDetailChild child = list2.get(j);
                OrderMakeModel model = new OrderMakeModel();
                model.setId(child.getId());
                model.setCategoryId(child.getCombogroup_categoryId());
                model.setAttributeId(child.getAttributeId());
                model.setCount(child.getNum());
                model.setPrice(child.getCustomerPrice());
                model.setName(child.getProductName());
//                model.setReserveTime(child.get);
                model.setTitle(child.getProductName());
                model.setImg(child.getImgList() == null?"":child.getImgList().get(0));
                dataLists.add(model);
            }
        }
        mAdaprer = new OrderDetailAdapter(this, dataLists, R.layout.adapter_order_make);
        commoditiesList.setAdapter(mAdaprer);
        total_amount.setText("￥" + getTotalAmount());
    }

    private String getOrderStatus(String orderStatus) {
        if ("1".equals(orderStatus)) {
            return "待付款";
        } else if ("2".equals(orderStatus)) {
            return "待发货";
        } else if ("3".equals(orderStatus)) {
            return "待收货";
        } else if ("4".equals(orderStatus)) {
            return "待评价";
        } else if ("5".equals(orderStatus)) {
            return "已完成";
        } else if ("6".equals(orderStatus)) {
            return "已退餐";
        }
        return StringUtils.isEmpty(orderStatus) ? "未知" : orderStatus;
    }

    @Override
    protected void initBaseData() {

    }

    public String getTotalAmount() {
        totalAmountDecimal = new BigDecimal("0");
        if(dataLists != null){
            for(int i=0;i<dataLists.size();i++){
                OrderMakeModel model = dataLists.get(i);
                BigDecimal b1 = new BigDecimal(model.getPrice());
                BigDecimal b2 = new BigDecimal(model.getCount());
                BigDecimal b3 = b1.multiply(b2);
                totalAmountDecimal = totalAmountDecimal.add(b3);
            }
        }
        totalAmountDecimal = totalAmountDecimal.setScale(2, BigDecimal.ROUND_UP);
        return String.valueOf(totalAmountDecimal);
    }
    @Override
    public View createSuccessView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.activity_order_detail, null);

        tv_order_no = (TextView)view.findViewById(R.id.tv_order_no);
        total_amount = (TextView)view.findViewById(R.id.total_amount);
        time = (TextView)view.findViewById(R.id.time);
        sender = (TextView)view.findViewById(R.id.sender);
        order_time = (TextView)view.findViewById(R.id.order_time);
        receiver_detail = (TextView)view.findViewById(R.id.receiver_detail);
        tv_order_state = (TextView)view.findViewById(R.id.tv_order_state);
        receiver_phone = (TextView)view.findViewById(R.id.receiver_phone);
        receiver_name = (TextView)view.findViewById(R.id.receiver_name);
        commoditiesList = (CustomListView)view.findViewById(R.id.commodity_list);
        scrollView = (ScrollView)view.findViewById(R.id.scrollView);
        right_arrow = (ImageView)view.findViewById(R.id.right_arrow);
        right_arrow.setVisibility(View.GONE);
//        List<Commodity> lists = new ArrayList<Commodity>();
//        lists.add(new Commodity());
//        lists.add(new Commodity());
//        lists.add(new Commodity());
//        lists.add(new Commodity());
//        lists.add(new Commodity());
//        mAdaprer = new OrderMakeAdapter(this, lists, R.layout.adapter_order_make);
//        commoditiesList.setAdapter(mAdaprer);
//        view.findViewById(R.id.receiver_rl).setOnClickListener(this);
        scrollView.smoothScrollTo(0, 0);
        return view;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.receiver_rl:
                Utils.moveTo(this, ModificationAddressScanActivity.class);
                break;


        }
    }
}