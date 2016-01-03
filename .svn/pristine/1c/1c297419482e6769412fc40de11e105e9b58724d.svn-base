package com.bluemobi.ybb.ps.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.base.utils.Utils;
import com.bluemobi.base.utils.WebUtils;
import com.bluemobi.ybb.ps.R;
import com.bluemobi.ybb.ps.adapter.CommonAdapter;
import com.bluemobi.ybb.ps.adapter.ViewHolder;
import com.bluemobi.ybb.ps.app.TitleBarManager;
import com.bluemobi.ybb.ps.app.YbbPsApplication;
import com.bluemobi.ybb.ps.base.BaseActivity;
import com.bluemobi.ybb.ps.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.ps.network.model.PeiSongListModel;
import com.bluemobi.ybb.ps.network.model.PeiSongModel;
import com.bluemobi.ybb.ps.network.request.PeiSongDanRequest;
import com.bluemobi.ybb.ps.network.response.PeiSongDanResponse;
import com.bluemobi.ybb.ps.util.Constants;
import com.bluemobi.ybb.ps.view.LoadingPage;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liufy on 2015/7/15.
 * P25-4 代点餐
 */
public class InsteadDeliveryActivity extends BaseActivity{


    private CommonAdapter<PeiSongModel> adapter;


    private List<PeiSongModel> info =new ArrayList<PeiSongModel>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this,getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.generation_of_order, R.drawable.common_back,true);
        showLoadingPage(false);
    }
    @Override
    protected void initBaseData() {
        curPage=0;

    }

    @Override
    protected boolean getPage(int type) {
        if (!super.getPage(type)) {
            return false;
        }
        connectToServer();
        return true;

    }

    private void connectToServer() {
        PeiSongDanRequest request;
        request = new PeiSongDanRequest
                (
                        new Response.Listener<PeiSongDanResponse>() {
                            @Override
                            public void onResponse(PeiSongDanResponse response) {
                                Utils.closeDialog();
                                plvonRefreshCompleted();
                                if (response != null && response.getStatus() == 0) {
                                    showListData(response.getData());
                                } else {
                                    Toast.makeText(mContext, response.getContent(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, this);
        request.setCurrentnum(Constants.CURRENTNUMBASE + "");
        request.setCurrentpage(curPage+"");
        request.setAttributeId(null);//餐次id
        request.setCategoryId(1);//套餐类型（1营养餐2零点餐3医护套餐4医患套餐）
        request.setCanteenId(YbbPsApplication.getInstance().getParamModel().getCanteen_id());//食堂id
        request.setAdminTypeId(null);//用户类型id
        request.setDeliverymanId(YbbPsApplication.getInstance().getParamModel().getUserInfoDTO().getUserId());//送货员id
        request.setIsAgent(1);//是否是代点餐（1是代点餐,2是非代点餐）
        request.setDistributionType(3);//配送状态（0未接单，1接单，2送货中，3已送到，4已退餐）
        WebUtils.doPost(request);
    }
    @Override
    protected YbbHttpJsonRequest initRequest() {

        PeiSongDanRequest request;
        request = new PeiSongDanRequest
                (
                        new Response.Listener<PeiSongDanResponse>() {
                            @Override
                            public void onResponse(PeiSongDanResponse response) {
                                Utils.closeDialog();
                                if (response != null && response.getStatus() == 0) {
                                    showListData(response.getData());
                                } else {
                                    Toast.makeText(mContext, response.getContent(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, this);
        request.setCurrentnum(Constants.CURRENTNUMBASE + "");
        request.setCurrentpage(0 + "");
        request.setAttributeId(null);//餐次id
        request.setCategoryId(1);//套餐类型（1营养餐2零点餐3医护套餐4医患套餐）
        request.setCanteenId(YbbPsApplication.getInstance().getParamModel().getCanteen_id());//食堂id
        request.setAdminTypeId(null);//用户类型id
        request.setDeliverymanId(YbbPsApplication.getInstance().getParamModel().getUserInfoDTO().getUserId());//送货员id
        request.setIsAgent(1);//是否是代点餐（1是代点餐,2是非代点餐）
        request.setDistributionType(3);//配送状态（0未接单，1接单，2送货中，3已送到，4已退餐）
        return request;
    }

    @Override
    public View createSuccessView(LayoutInflater inflater)
    {
        View mineView = inflater.inflate(R.layout.activity_dilivery_meals,null);
        plv_refresh = (PullToRefreshListView) mineView.findViewById(R.id.plv_refresh);
        initPullToRefresh(plv_refresh);

        return mineView;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    /**
     * 显示列表数据
     */
    private void showListData(PeiSongListModel list) {


        if (list == null) {
            return;
        }
        if (list.getInfo().size() == 0) {
            return;
        }
        if (list.getCurrentpage().equals("1")) {
            info.clear();
            info.addAll(list.getInfo());
        } else {
            info.addAll(list.getInfo());
        }

        if (adapter == null) {
            plv_refresh.setAdapter(
                    adapter = new CommonAdapter<PeiSongModel>(mContext,
                            info, R.layout.lv_meals) {

                        @Override
                        public void convert(ViewHolder helper, PeiSongModel item) {
                            TextView order_id = helper.getView(R.id.order_id);
                            TextView order_time = helper.getView(R.id.order_time);
                            TextView ID_dress = helper.getView(R.id.ID_dress);
                            TextView type = helper.getView(R.id.type);
                            TextView time = helper.getView(R.id.time);
                            TextView productAndNum = helper.getView(R.id.productAndNum);

                            order_id.setText("订单号：" + item.getOrderNo());
                            order_time.setText(item.getCreateTime());
                            ID_dress.setText(item.getCityName() + item.getDistrictName());
                            if (item.getAttributeNameList() != null && item.getAttributeNameList().size() > 0) {
                                type.setText(item.getAttributeNameList().get(0));
                            }
                            time.setText(item.getReserveTime());
                            productAndNum.setText(item.getProductAndNum());
                        }
                    });

        } else {
            adapter.notifyDataSetChanged();
        }
    }

}
