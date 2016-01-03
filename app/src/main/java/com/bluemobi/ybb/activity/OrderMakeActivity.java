package com.bluemobi.ybb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.adapter.OrderMakeAdapter;
import com.bluemobi.ybb.app.BackGroundTask;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.model.Commodity;
import com.bluemobi.ybb.network.model.EditShopCarModel;
import com.bluemobi.ybb.network.model.OrderMakeModel;
import com.bluemobi.ybb.network.request.OrderAgentCommitRequest;
import com.bluemobi.ybb.network.request.OrderCartCommitRequest;
import com.bluemobi.ybb.network.request.OrderCommitRequest;
import com.bluemobi.ybb.network.request.ShippingAddressRequest;
import com.bluemobi.ybb.network.response.OrderCommitResponse;
import com.bluemobi.ybb.network.response.ShippingAddressResponse;
import com.bluemobi.ybb.network.response.ShopCartResponse;
import com.bluemobi.ybb.util.StringUtils;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.CustomListView;
import com.bluemobi.ybb.view.LoadingPage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * P6-5填写订单
 * Created by wangzhijun on 2015/7/10.
 */
public class OrderMakeActivity extends BaseActivity implements View.OnClickListener {

    private CustomListView commoditiesList;

    private OrderMakeAdapter mAdaprer;

    private ScrollView scrollView;

    private OrderMakeModel model;

    private List<OrderMakeModel> dataList = new ArrayList<OrderMakeModel>();
    private TextView receiver_name;
    private TextView receiver_phone;
    private TextView receiver_detail;
    private TextView sender;
    private TextView totalAmountTv;
    private String address;
    private String totalAmount;
    private BigDecimal totalAmountDecimal;


    private String orderNo;

    private boolean fromCart;

    private boolean oneDay = true;

    private int totalDays;

    private int currentCounts;

    private int PAY_ = 1;

    private ArrayList<ArrayList<OrderMakeModel>> allDays = new ArrayList<ArrayList<OrderMakeModel>>() ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.order_make_title, R.drawable.common_back, true);
        showLoadingPage(false);
    }

    @Override
    protected void initBaseData() {
        model = (OrderMakeModel)getIntent().getSerializableExtra("item");
//        OrderMakeModel a = new OrderMakeModel();
//        a.setReserveTime(model.getReserveTime());
//        a.setPrice(model.getPrice());
//        a.setCategoryId("2");
//        a.setCount("5");
//        a.setCategory("goubu");
//        a.setImg(model.getImg());
//        a.setTitle(model.getTitle());
        if(model != null){
            dataList.add(model);
        }else{
            fromCart = true;
            getCartData();
        }
        Collections.sort(dataList, new SortByCategory());
    }

    private void getCartData() {
        List<ShopCartResponse.ShopCartData.ShopCartDTo> list =
                YbbApplication.getInstance().getSelectDataList();
        if(list != null){
            for(ShopCartResponse.ShopCartData.ShopCartDTo
                     item : list){
//                Logger.e("wangzhijun", "list.size()-->" + list.size());
//                Logger.e("wangzhijun", "item.productNum--> "+item.productNum);
                OrderMakeModel model = new OrderMakeModel();
                model.setCount(item.productNum);
                model.setTitle(item.productName);
                model.setImg(item.imgPath);
                model.setCategoryId(item.categoryId);
                model.setId(item.productId);
                model.setReserveTime(item.reserveTime);
                model.setPrice(item.price);
                model.setCartId(item.id);
                model.setAttributeId(item.attributeId);
//                Logger.e("wangzhijun", "item.price-->" + item.price);
//                model.setCategory(item.);
                dataList.add(model);
            }
        }
    }

    @Override
    protected YbbHttpJsonRequest initRequest() {
        /**
         * 获取地址
         */
        ShippingAddressRequest request = new ShippingAddressRequest(new Response.Listener<ShippingAddressResponse>() {
            @Override
            public void onResponse(ShippingAddressResponse response) {
                if(response != null && response.getStatus() == 0){
                    StringBuffer bf = new StringBuffer();
                    if(StringUtils.isNotEmpty(response.getData().getProvinceName())){
                        bf.append(response.getData().getProvinceName());
                    }if(StringUtils.isNotEmpty(response.getData().getCityName())){
                        bf.append(response.getData().getCityName());
                    }if(StringUtils.isNotEmpty(response.getData().getDistrictName())){
                        bf.append(response.getData().getDistrictName());
                    }
                    address = bf.toString();
                    receiver_detail.setText(address);
                }
            }
        }, this);
        request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
        return request;
    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.activity_order_make, null);
        commoditiesList = (CustomListView)view.findViewById(R.id.commodity_list);
        scrollView = (ScrollView)view.findViewById(R.id.scrollView);
        receiver_name = (TextView)view.findViewById(R.id.receiver_name);
        receiver_phone = (TextView)view.findViewById(R.id.receiver_phone);
        receiver_detail = (TextView)view.findViewById(R.id.receiver_detail);
        totalAmountTv = (TextView)view.findViewById(R.id.total_amount);
        sender = (TextView)view.findViewById(R.id.sender);
        mAdaprer = new OrderMakeAdapter(this, dataList, R.layout.adapter_order_make);
        commoditiesList.setAdapter(mAdaprer);
        view.findViewById(R.id.receiver_rl).setOnClickListener(this);
        view.findViewById(R.id.order_make_sure).setOnClickListener(this);
        scrollView.smoothScrollTo(0, 0);
        receiver_name.setText(YbbApplication.getInstance().getMyUserInfo().getNickName());
        receiver_phone.setText(YbbApplication.getInstance().getMyUserInfo().getUserName());
        sender.setText(YbbApplication.getInstance().getParamModel().getUserInfoDTO().getRealName());
        totalAmountTv.setText("￥" + getTotalAmount());
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
            case R.id.order_make_sure:
                createOrder();
                break;

        }
    }

    private void createOrder() {
        if(!fromCart) {//不是来自购物车
            if(YbbApplication.getInstance().getExt()){  //代点餐
                OrderAgentCommitRequest request = new OrderAgentCommitRequest(new Response.Listener<OrderCommitResponse>() {
                    @Override
                    public void onResponse(OrderCommitResponse response) {
                        Utils.closeDialog();
                        if (response != null && response.getStatus() == 0) {
                            Utils.makeToastAndShow(getBaseContext(), "提交完成");
                            orderNo = response.getData().getValue();
                            finish();
                            if(!YbbApplication.role_yh.equals(
                                    YbbApplication.getInstance().getMyUserInfo().getTypeId())){
                                Intent intent = new Intent();
                                intent.setClass(getBaseContext(), PaymentActivity.class);
                                intent.putExtra("orderNo", orderNo);
                                intent.putExtra("totalAmount", getTotalAmount());
                                intent.putExtra("from", "ordermake");
                                startActivity(intent);
                                finish();
                                getParams();
                            }

                        }
                    }
                }, this);
                request.setTotalAmount(getTotalAmount());
                request.setDataBody(dataList);
                request.setReserveTime(dataList.get(0).getReserveTime());
                Utils.showProgressDialog(this);
                WebUtils.doPost(request);
            }else{//非代点餐
                OrderCommitRequest request = new OrderCommitRequest(new Response.Listener<OrderCommitResponse>() {
                    @Override
                    public void onResponse(OrderCommitResponse response) {
                        Utils.closeDialog();
                        if (response != null && response.getStatus() == 0) {
                            Utils.makeToastAndShow(getBaseContext(), "提交完成");
                            orderNo = response.getData().getValue();
                            finish();
                            if(!YbbApplication.role_yh.equals(
                                    YbbApplication.getInstance().getMyUserInfo().getTypeId())){
                                Intent intent = new Intent();
                                intent.setClass(getBaseContext(), PaymentActivity.class);
                                intent.putExtra("orderNo", orderNo);
                                intent.putExtra("totalAmount", getTotalAmount());
                                intent.putExtra("from", "ordermake");
                                startActivity(intent);
                            }else{
                                Intent intent = new Intent();
                                intent.setClass(getBaseContext(), MineOrderActivcity.class);
                                intent.putExtra("from", "oneDay_yh");
//                            intent.putExtra("orderNo", orderNo);
//                            intent.putExtra("totalAmount", getTotalAmount());
                                startActivity(intent);
                            }
                            getParams();

                        }
                    }
                }, this);
                request.setTotalAmount(getTotalAmount());
                request.setDataBody(dataList);
                request.setReserveTime(dataList.get(0).getReserveTime());
                Utils.showProgressDialog(this);
                WebUtils.doPost(request);
            }

        }else{//来自购物车
            HashMap<String, ArrayList<OrderMakeModel>> split = new HashMap<String, ArrayList<OrderMakeModel>>();
            if(dataList!=null && dataList.size() > 0){
                int size = dataList.size();
                for(int i=0; i<size; i++){
                    OrderMakeModel model = dataList.get(i);
                    ArrayList<OrderMakeModel> tempList = split.get(model.getReserveTime());
                    if(tempList == null){
                        tempList = new ArrayList<OrderMakeModel>();
                        split.put(model.getReserveTime(), tempList);
                    }
                    tempList.add(model);
                }
            }

            allDays.clear();
            Iterator iter = split.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String reserveTime = (String)entry.getKey();
                ArrayList<OrderMakeModel> data = (ArrayList<OrderMakeModel>)entry.getValue();
//                createCartOrder(reserveTime, data);
                allDays.add(data);
            }
            if(allDays.size()>1){
                oneDay = false;
            }
            currentCounts = 0;
            totalDays = allDays.size();
            for(ArrayList<OrderMakeModel> items : allDays){
                createCartOrder(items.get(0).getReserveTime(), items);
            }

        }
    }

    /**
     * 批次
     * @param reserveTime
     * @param data
     */
    private void createCartOrder(String reserveTime, final List<OrderMakeModel> data) {
        if(YbbApplication.getInstance().getExt()){//代点餐
            OrderAgentCommitRequest request = new OrderAgentCommitRequest(new Response.Listener<OrderCommitResponse>() {
                @Override
                public void onResponse(OrderCommitResponse response) {
                    Utils.closeDialog();
                    currentCounts++;
                    if (response != null && response.getStatus() == 0) {
                        if(oneDay){
                            Utils.makeToastAndShow(getBaseContext(), "提交完成");
                            orderNo = response.getData().getValue();
                            finish();
                            Intent intent = new Intent();
                            intent.setClass(getBaseContext(), PaymentActivity.class);
                            intent.putExtra("orderNo", orderNo);
                            intent.putExtra("totalAmount", getTotalAmount(data));
                            intent.putExtra("from", "ordermake");

                            startActivity(intent);
                            getParams();
                        }else{
                            if(currentCounts == totalDays){
                                Utils.makeToastAndShow(getBaseContext(), "提交完成");
                                finish();
                                Intent intent = new Intent();
                                intent.setClass(getBaseContext(), MineOrderActivcity.class);
                                intent.putExtra("from", "mulDays");
//                            intent.putExtra("orderNo", orderNo);
//                            intent.putExtra("totalAmount", getTotalAmount());
                                startActivity(intent);
                                getParams();
                            }
                        }

                    }else{
                        Utils.makeToastAndShow(getBaseContext(), "提交失败");
                    }
                }
            }, this);
            request.setHandleCustomErr(false);
            request.setTotalAmount(getTotalAmount(data));
            request.setDataBody(data);
            request.setReserveTime(reserveTime);
            Utils.showProgressDialog(this);
            WebUtils.doPost(request);
        }else{//非代点餐
            OrderCartCommitRequest request = new OrderCartCommitRequest(new Response.Listener<OrderCommitResponse>() {
                @Override
                public void onResponse(OrderCommitResponse response) {
                    Utils.closeDialog();
                    currentCounts++;
                    if (response != null && response.getStatus() == 0) {
                        if(oneDay){
                            Utils.makeToastAndShow(getBaseContext(), "提交完成");
                            orderNo = response.getData().getValue();
                            finish();
                            if(!YbbApplication.role_yh.equals(
                                    YbbApplication.getInstance().getMyUserInfo().getTypeId())){
                                Intent intent = new Intent();
                                intent.setClass(getBaseContext(), PaymentActivity.class);
                                intent.putExtra("orderNo", orderNo);
                                intent.putExtra("totalAmount", getTotalAmount(data));
                                intent.putExtra("from", "ordermake");
                                startActivity(intent);
                            }else{
                                Intent intent = new Intent();
                                intent.setClass(getBaseContext(), MineOrderActivcity.class);
                                intent.putExtra("from", "oneDay_yh");
//                            intent.putExtra("orderNo", orderNo);
//                            intent.putExtra("totalAmount", getTotalAmount());
                                startActivity(intent);
                            }

                            getParams();
                        }else{
                            if(currentCounts == totalDays){
                                Utils.makeToastAndShow(getBaseContext(), "提交完成");
                                finish();
                                if(!YbbApplication.role_yh.equals(
                                        YbbApplication.getInstance().getMyUserInfo().getTypeId())){
                                    Intent intent = new Intent();
                                    intent.setClass(getBaseContext(), MineOrderActivcity.class);
                                    intent.putExtra("from", "mulDays_bh");
//                            intent.putExtra("orderNo", orderNo);
//                            intent.putExtra("totalAmount", getTotalAmount());
                                    startActivity(intent);
                                }else{
                                    Intent intent = new Intent();
                                    intent.setClass(getBaseContext(), MineOrderActivcity.class);
                                    intent.putExtra("from", "mulDays_yh");
//                            intent.putExtra("orderNo", orderNo);
//                            intent.putExtra("totalAmount", getTotalAmount());
                                    startActivity(intent);
                                }

                                getParams();
                            }
                        }

                    }else{
                        Utils.makeToastAndShow(getBaseContext(), "提交失败");
                    }
                }
            }, this);
            request.setHandleCustomErr(false);
            request.setTotalAmount(getTotalAmount(data));
            request.setDataBody(data);
            request.setReserveTime(reserveTime);
            Utils.showProgressDialog(this);
            WebUtils.doPost(request);
        }

    }

    private String getTotalAmount(List<OrderMakeModel> data) {
        double d = 0;
        BigDecimal totalAmountDecimal = new BigDecimal(d);
        if(data != null){
            for(int i=0;i<data.size();i++){
                OrderMakeModel model = data.get(i);
                BigDecimal b1 = new BigDecimal(model.getPrice());
                BigDecimal b2 = new BigDecimal(model.getCount());
                BigDecimal b3 = b1.multiply(b2);
                totalAmountDecimal = totalAmountDecimal.add(b3);
            }
        }
        double value = totalAmountDecimal.setScale(2, BigDecimal.ROUND_UP).floatValue();
        return String.valueOf(value);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        super.onErrorResponse(error);
        currentCounts++;
    }

    private void getParams(){
        BackGroundTask task = new BackGroundTask();
        task.execute();
    }

    public String getTotalAmount() {
        totalAmountDecimal = new BigDecimal("0");
        if(dataList != null){
            for(int i=0;i<dataList.size();i++){
                OrderMakeModel model = dataList.get(i);
                BigDecimal b1 = new BigDecimal(model.getPrice());
                BigDecimal b2 = new BigDecimal(model.getCount());
                BigDecimal b3 = b1.multiply(b2);
                totalAmountDecimal = totalAmountDecimal.add(b3);
            }
        }
        totalAmountDecimal = totalAmountDecimal.setScale(2, BigDecimal.ROUND_UP);
        return String.valueOf(totalAmountDecimal);
    }

    

    private class SortByCategory implements java.util.Comparator<OrderMakeModel> {

        @Override
        public int compare(OrderMakeModel lhs, OrderMakeModel rhs) {
            if(Integer.parseInt(rhs.getCategoryId()) <
                Integer.parseInt(lhs.getCategoryId())){
                return 1;
            }
            return 0;
        }
    }
}
