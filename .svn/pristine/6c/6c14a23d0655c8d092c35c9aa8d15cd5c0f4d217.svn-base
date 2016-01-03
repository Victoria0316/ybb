package com.bluemobi.ybb.fragment.page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.adapter.FoodProductNoMoreAdapter;
import com.bluemobi.ybb.adapter.ViewHolder;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.fragment.BaseFragment;
import com.bluemobi.ybb.network.model.FoodProductModel;
import com.bluemobi.ybb.network.model.ParamModel;
import com.bluemobi.ybb.network.request.FoodProductListRequest;
import com.bluemobi.ybb.network.response.FoodProductListResponse;
import com.bluemobi.ybb.util.Constants;
import com.bluemobi.ybb.util.StringUtils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.LoadingPage;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 餐次页面
 *
 * @author wangzhijun
 *
 */
public class AttributePage extends BasePage {
    private FoodProductNoMoreAdapter adapter;

    private List<FoodProductModel> dataList = new ArrayList<FoodProductModel>();
    /**
     * 套餐类型（1营养餐2零点餐3医护套餐4医患套餐）
     */
    private String categoryId;
    /**
     *  餐次
     */
    private String attributeId;
    /**
     *  餐次名称
     */
    private String attributeName;
    /**
     * 购物车价钱
     */
    private TextView mCartAmount;

    public AttributePage(Context context, BaseFragment baseFragment, TextView mCartAmount,
                         String attributeId, String attributeName, String categoryId) {
        super(context, baseFragment);
        this.mCartAmount = mCartAmount;
        this.attributeId = attributeId;
        this.attributeName = attributeName;
        this.categoryId = categoryId;
    }

    @Override
    public View initView(LayoutInflater inflater) {

        View homeView = inflater.inflate(R.layout.page_dinner, null);
        plv_refresh = (PullToRefreshListView) homeView.findViewById(R.id.plv_refresh);
        initPullToRefresh(plv_refresh);
        adapter = new FoodProductNoMoreAdapter(pageContext,
                dataList, R.layout.lv__main_search_item){

            @Override
            public void convert(ViewHolder helper, FoodProductModel item) {
                helper.getView(R.id.iv_search_more).setVisibility(View.INVISIBLE);
            }
        };
        plv_refresh.setAdapter(adapter);

        return homeView;
    }

    @Override
    protected View initRootView() {

        LoadingPage loadingPage = new LoadingPage(pageContext) {
            @Override
            public View createSuccessView() {
                return null;
            }

            @Override
            protected LoadResult load() {
                return null;
            }
        };

        return  loadingPage;
    }

    @Override
    public void initData() {
        isShowLoadPage = true;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return null;
    }

    @Override
    public void loaded() {
        /**
         *    private String keyword;//查询关键字
         private String productId;//否	主食id
         private String queryTime;//否	查询时间（yyyy-mm-dd）
         private String attributeId;//否	餐次id
         private String canteenId;//是	食堂id
         private String categoryId;//是	套餐类型（1营养餐2零点餐3医护套餐4医患套餐）
         private String loginuserid;//是	当前登录用户id
         */
        FoodProductListRequest request = new FoodProductListRequest(new Response.Listener<FoodProductListResponse>() {
            @Override
            public void onResponse(FoodProductListResponse response) {
                if (response != null && response.getStatus() == 0) {
                    plv_refresh.onRefreshComplete();
                    if(response.getData().getInfo() !=null){
                        if (response.getData().getCurrentpage().equals("1"))
                        {
                            dataList.clear();
                        }
                        dataList.addAll(response.getData().getInfo());
                        adapter.notifyDataSetChanged();
                        hasLoaded = true;
                    }
                    adapter = new FoodProductNoMoreAdapter(pageContext, dataList, R.layout.lv__main_search_item, mCartAmount);
                    plv_refresh.setAdapter(adapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        List<ParamModel.ProductAttributeEntity> lists = YbbApplication.getInstance().getParamModel().getProductAttributeInfoList();
        request.setCurrentnum(Constants.PAGE_NUM);
        request.setCurrentpage("0");
        request.setAttributeId(attributeId);
        request.setLoginuserid(YbbApplication.getInstance().getMyUserInfo().getUserId());
        if(StringUtils.isEmpty(categoryId)){
            request.setCategoryId("3");//医护
        }else{
            request.setCategoryId(categoryId);
        }
        request.setCanteenId(YbbApplication.getInstance().getCanteenId());
        request.setNetWorkResponseListener(loadingPage);
        WebUtils.doPost(request);
    }

    @Override
    protected boolean getPage(int type) {
        if (!super.getPage(type)){
            return false;
        }
        connectToServer();
        return true;
    }
    public void connectToServer(){
        FoodProductListRequest request = new FoodProductListRequest(new Response.Listener<FoodProductListResponse>() {
            @Override
            public void onResponse(FoodProductListResponse response)
            {
                if (response != null && response.getStatus() == 0){
                    plv_refresh.onRefreshComplete();
                    if(response.getData().getInfo() !=null){
                        if (response.getData().getCurrentpage().equals("1"))
                        {
                            dataList.clear();
                        }
                        dataList.addAll(response.getData().getInfo());
                        adapter.notifyDataSetChanged();
                        hasLoaded = true;
                    }
                    adapter = new FoodProductNoMoreAdapter(pageContext, dataList, R.layout.lv__main_search_item, mCartAmount);
                    plv_refresh.setAdapter(adapter);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Logger.e("wangzhijun", "error222222222222222222222222222222");
                plv_refresh.onRefreshComplete();
            }
        });
        List<ParamModel.ProductAttributeEntity> lists = YbbApplication.getInstance().getParamModel().getProductAttributeInfoList();
        request.setCurrentnum(Constants.PAGE_NUM);
        request.setPageTime("");
        request.setCurrentpage(getCurPage() + "");
        request.setAttributeId(attributeId);
        request.setLoginuserid(YbbApplication.getInstance().getMyUserInfo().getUserId());
        if(StringUtils.isEmpty(categoryId)){
            request.setCategoryId("3");//医护
        }else{
            request.setCategoryId(categoryId);
        }
//        request.setCategoryId("1");//医护
//        request.setAttributeId("8aba20e84ef857e1014ef85af5940003");


        request.setCanteenId(YbbApplication.getInstance().getCanteenId());
        request.setNetWorkResponseListener(loadingPage);
        WebUtils.doPost(request);
    }
}
