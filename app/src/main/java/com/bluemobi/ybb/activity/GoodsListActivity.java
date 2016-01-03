package com.bluemobi.ybb.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.bluemobi.ybb.R;
import com.bluemobi.ybb.adapter.CommonAdapter;
import com.bluemobi.ybb.adapter.ViewHolder;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.view.LoadingPage;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoyn on 2015/7/1.
 *
 * P5-6 商品列表
 *
 */
public class GoodsListActivity extends BaseActivity{

    private CommonAdapter<String> adapter;

    private List<String> lv = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(GoodsListActivity.this,getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.activity_head, R.drawable.common_back,true);

        showLoadingPage(true);
    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.activity_goods_list,null);

        //下拉刷新
        plv_refresh = (PullToRefreshListView) view.findViewById(R.id.plv_refresh);
        initPullToRefresh(plv_refresh);
        plv_refresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        plv_refresh.onRefreshComplete();
                    }
                }, 500);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        plv_refresh.onRefreshComplete();

                    }
                }, 500);

            }
        });

        for (int i = 0;i<10;i++)
        {
            lv.add("ddd"+i);
        }

        plv_refresh.setAdapter(
         adapter = new CommonAdapter<String>(mContext,lv,R.layout.lv_goods_list_item) {
             @Override
             public void convert(ViewHolder helper, String item) {

             }
         });
        return view;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void clickBarleft() {
        finish();
    }
}
