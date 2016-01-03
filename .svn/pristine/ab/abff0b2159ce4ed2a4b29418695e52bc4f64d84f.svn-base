package com.bluemobi.ybb.ps.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.bluemobi.ybb.ps.R;
import com.bluemobi.ybb.ps.adapter.CommonAdapter;
import com.bluemobi.ybb.ps.adapter.ViewHolder;
import com.bluemobi.ybb.ps.app.TitleBarManager;
import com.bluemobi.ybb.ps.base.BaseActivity;
import com.bluemobi.ybb.ps.view.LoadingPage;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liufy on 2015/7/15.
 * P25-4代点餐订单
 */
public class GenerationOfOrderActivity extends BaseActivity{

    private CommonAdapter<String> adapter;


    private List<String> dataList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this,getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.my, R.drawable.common_back,false);

        showLoadingPage(false);
    }
    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater)
    {
        View mineView = inflater.inflate(R.layout.activity_dilivery_meals,null);
        plv_refresh = (PullToRefreshListView) mineView.findViewById(R.id.plv_refresh);
        initPullToRefresh(plv_refresh);
        plv_refresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>()
        {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        plv_refresh.onRefreshComplete();
                    }
                },500);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        plv_refresh.onRefreshComplete();

                    }
                },500);

            }
        });
        for (int i = 0;i<10;i++)
        {
            dataList.add("ddd"+i);
        }
        plv_refresh.setAdapter(
                adapter = new CommonAdapter<String>(mContext,
                        dataList, R.layout.lv_meals) {

                    @Override
                    public void convert(ViewHolder helper, String bean) {

                    }
                });

        return mineView;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

}
