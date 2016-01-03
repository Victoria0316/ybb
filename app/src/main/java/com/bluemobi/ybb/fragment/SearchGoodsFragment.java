package com.bluemobi.ybb.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bluemobi.ybb.R;
import com.bluemobi.ybb.adapter.CommonAdapter;
import com.bluemobi.ybb.adapter.ViewHolder;
import com.bluemobi.ybb.view.LoadingPage;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liufy on 2015/6/29.
 * P5-1-1商品
 */
public class SearchGoodsFragment extends BaseFragment {

    private CommonAdapter<String> adapter;


    private List<String> dataList = new ArrayList<String>();

    @Override
    public void initData(Bundle savedInstanceState)
    {  isShowLoadPage = false;

    }

    @Override
    public View createSuccessView(LayoutInflater inflater)
    {
        View homeView = inflater.inflate(R.layout.fragment_search_goods,null);
        plv_refresh = (PullToRefreshListView) homeView.findViewById(R.id.plv_refresh);
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
        plv_refresh.setAdapter(
                adapter = new CommonAdapter<String>(mContext,
                        dataList, R.layout.lv__main_search_item) {

                    @Override
                    public void convert(ViewHolder helper, String bean) {

                    }
                });

        return homeView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        InputMethodManager imm =  (InputMethodManager)getActivity().getSystemService(
                Activity.INPUT_METHOD_SERVICE);
        if(imm != null) {

            imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(),
                    0);
        }
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }


}
