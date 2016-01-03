package com.bluemobi.ybb.fragment.page;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.activity.MyCommentActivity;
import com.bluemobi.ybb.adapter.CommonAdapter;
import com.bluemobi.ybb.adapter.MineOrderBackPageAdapter;
import com.bluemobi.ybb.adapter.MineOrderToEvaluatePageAdapter;
import com.bluemobi.ybb.adapter.OrderAdapter;
import com.bluemobi.ybb.adapter.ViewHolder;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.fragment.BaseFragment;
import com.bluemobi.ybb.network.model.OrderItem;
import com.bluemobi.ybb.network.model.OrderinfoResponseModel;
import com.bluemobi.ybb.network.model.Orderinfoinfo;
import com.bluemobi.ybb.network.request.OrderinfoRequest;
import com.bluemobi.ybb.network.response.OrderinfoResponse;
import com.bluemobi.ybb.util.Constants;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.LoadingPage;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * liufengyu
 * @author Administrator
 * P14-1我的订单-餐品 待评价
 */
public  class MineOrderToEvaluatePage extends BasePage
{
private final static String tag ="MineOrderToEvaluatePage";

    private Context mContext;

    private OrderAdapter adapter;

    private List<OrderItem> dataList = new ArrayList<OrderItem>();

    public MineOrderToEvaluatePage(Context context,BaseFragment baseFragment) {
        super(context,baseFragment);
        this.mContext = context;
        NUMBER_PER_PAGE = Constants.MINPAGESIZE;
    }

    @Override
    public View initView(LayoutInflater inflater) {

        View homeView = inflater.inflate(R.layout.page_order_to_evaluate,null);
        plv_refresh = (PullToRefreshListView) homeView.findViewById(R.id.plv_refresh);
        initPullToRefresh(plv_refresh);
        adapter = new OrderAdapter(pageContext,dataList, true);
        plv_refresh.setAdapter(adapter);
        getPage(LOAD_REFRESH);
        return homeView;
    }
    @Override
    protected boolean getPage(int type) {
        if(!super.getPage(type)){
            return false;
        }
        OrderinfoRequest(type);
        return true;
    }

    private void OrderinfoRequest(int type) {
        if(type == LOAD_REFRESH){
            currentPage = 0;
            dataList.clear();
        }else if(type == LOAD_MORE){
            currentPage ++;
        }
        OrderinfoRequest request = new OrderinfoRequest
                (
                        new Response.Listener<OrderinfoResponse>() {
                            @Override
                            public void onResponse(OrderinfoResponse response) {
                                plv_refresh.onRefreshComplete();
                                Utils.closeDialog();
                                if (response != null && response.getStatus() == 0)
                                {
                                    showListData(response.getData());
                                }
                                else
                                {
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(mContext, response.getContent(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utils.closeDialog();
                        Toast.makeText(mContext, "服务器繁忙",
                                Toast.LENGTH_SHORT).show();

                    }
                });

//        userId 	string 	是 	用户id
//        orderStatus 	string 	是 	需要查询的订单类型【1：未付款 2：已付款(待发货) 3：已付款（已发货，待收货） 4：已收货(待评价) 5：已完成 6：已退单】
//        currentnum 	string 	是 	每页条数
//        currentpage 	string 	是 	当前页数（起始0）
        request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setOrderStatus("4");
        request.setCurrentnum(Constants.MINPAGESIZE + "");
        request.setCurrentpage(currentPage+"");
        request.setHandleCustomErr(false);
        Utils.showProgressDialog(pageContext);
        WebUtils.doPost(request);
    }


    @Override
    public void initData() {
        isShowLoadPage = false;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    /**
     *
     * 显示列表数据
     *
     * */
    private void showListData(OrderinfoResponseModel model)
    {
        if (model == null)
        {
            return;
        }
        if (model.getInfo()==null )
        {
            return;
        }
        if (model.getInfo().size() == 0){
            return;
        }
        if (model.getCurrentpage().equals("0"))
        {
            dataList.clear();
        }
        for ( OrderItem  bean: model.getInfo())
        {
            dataList.add(bean);
        }
        adapter.notifyDataSetChanged();
        adapter.reload();
    }

    protected class OrderCommentReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Logger.e("wangzhijun", "EVALUATE OrderCommentReceiver");
            if(Constants.RECEIVER_COMMENT_COMMENT_PAGE.equals(intent.getAction())){
                int index = intent.getIntExtra("index", -2);
                Logger.e("wangzhijun", "intent.getIntExtra(\"index\", -2);--> " + index);
                if(index != -1 && index != -2){
                    dataList.remove(index);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}
