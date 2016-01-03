package com.bluemobi.ybb.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.activity.CommodityDetailActivity;
import com.bluemobi.ybb.activity.MedicalPlanMealsActivity;
import com.bluemobi.ybb.activity.RestaurantBookingActivity;
import com.bluemobi.ybb.adapter.FoodProductNoMoreAdapter;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.model.FoodProductModel;
import com.bluemobi.ybb.network.request.FoodProductListRequest;
import com.bluemobi.ybb.network.response.FoodProductListResponse;
import com.bluemobi.ybb.util.Constants;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.LoadingPage;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liufy on 2015/6/29.
 * P9零点餐
 */
public class ZeroPointmealFragment extends BaseFragment implements View.OnClickListener{

    private FoodProductNoMoreAdapter adapter;

    private List<FoodProductModel> dataList = new ArrayList<FoodProductModel>();

    private TextView tv_zero;

    private RelativeLayout  rl_zero;

    private ImageView iv_add;
    private ImageView minus;
    private EditText et_hint;
    public BaseFragment baseFragment;
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

    }

    @Override
    protected boolean getPage(int type) {
        if(!super.getPage(type)){
            return false;
        }
        connectToServer();
        return true;

    }

    private void connectToServer()
    {
        FoodProductListRequest request = new FoodProductListRequest(new Response.Listener<FoodProductListResponse>() {
            @Override
            public void onResponse(FoodProductListResponse response) {
                plv_refresh.onRefreshComplete();
                if (response != null && response.getStatus() ==0){
                    if(response.getData().getInfo() !=null){
                        dataList.clear();
                        dataList.addAll(response.getData().getInfo());
                        adapter = new FoodProductNoMoreAdapter(getActivity(),
                                dataList, R.layout.lv__main_search_item, cartAmountTv);
                        plv_refresh.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                plv_refresh.onRefreshComplete();

            }
        });
        request.setCurrentnum(Constants.PAGE_NUM);
        request.setPageTime("");
        request.setCurrentpage(getCurPage()+"");
        request.setAttributeId("");
        request.setLoginuserid(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setCategoryId("2");//零点餐
        request.setCanteenId(YbbApplication.getInstance().getCanteenId());

        WebUtils.doPost(request);
    }
    @Override
    public View createSuccessView(LayoutInflater inflater)
    {
        View zeroMealView = inflater.inflate(R.layout.fragment_zero_meals,null);

        et_hint = (EditText) zeroMealView.findViewById(R.id.et_hint);
        tv_zero = (TextView) zeroMealView.findViewById(R.id.tv_zero);
        rl_zero = (RelativeLayout) zeroMealView.findViewById(R.id.rl_zero);

        if (getActivity() instanceof RestaurantBookingActivity)
        {
            rl_zero.setVisibility(View.VISIBLE);
        }
        else
        {
            rl_zero.setVisibility(View.GONE);
        }
        plv_refresh = (PullToRefreshListView) zeroMealView.findViewById(R.id.plv_refresh);
        initPullToRefresh(plv_refresh);
        plv_refresh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("id", dataList.get(position-1).getId());
                intent.putExtra("imgPath", dataList.get(position-1).getImgPath());
                intent.putExtra("categoryId", dataList.get(position-1).getCombogroup_categoryId());
                intent.putExtra("attributeId", dataList.get(position-1).getAttributeId());
                intent.setClass(getActivity(), CommodityDetailActivity.class);
                getActivity().startActivity(intent);
            }
        });


        return zeroMealView;
    }

    @Override
    protected YbbHttpJsonRequest initRequest() {
      /*  keyword	string	否	查询关键字
        productId	string	否	主食id
        queryTime	string	否	查询时间（yyyy-mm-dd）
        attributeId	string	否	餐次id
        canteenId	string	是	食堂id
        categoryId	string	是	套餐类型（1营养餐2零点餐3医护套餐4医患套餐）
        loginuserid	string	是	当前登录用户id*/
        FoodProductListRequest request = new FoodProductListRequest(new Response.Listener<FoodProductListResponse>() {
            @Override
            public void onResponse(FoodProductListResponse response) {
                if (response != null && response.getStatus() ==0){
                    if(response.getData().getInfo() !=null){
                        dataList.clear();
                        dataList.addAll(response.getData().getInfo());
                        adapter = new FoodProductNoMoreAdapter(getActivity(),
                                dataList, R.layout.lv__main_search_item, cartAmountTv);
                        plv_refresh.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        request.setCurrentnum(Constants.PAGE_NUM);
        request.setPageTime("");
        request.setCurrentpage("0");
        request.setAttributeId("");
        request.setLoginuserid(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setCategoryId("2");//零点餐
        request.setCanteenId(YbbApplication.getInstance().getCanteenId());
        return request;

    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    private int sumAddShopCar = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_add:

                break;
            case R.id.minus:

                break;
        }
    }
}
