package com.bluemobi.ybb.ps.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bluemobi.ybb.ps.R;
import com.bluemobi.ybb.ps.app.TitleBarManager;
import com.bluemobi.ybb.ps.base.BaseActivity;
import com.bluemobi.ybb.ps.view.CustomListView;
import com.bluemobi.ybb.ps.view.LoadingPage;

/**
 * 订单提醒详情
 * Created by wangzhijun on 2015/7/14.
 */
public class OrderRemindDetailActivity extends BaseActivity{

    private CustomListView listview;

    private TempAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLoadingPage(false);
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this,getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.order_remind_detail, R.drawable.common_back,false);
    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.activity_order_remind_detail, null);
        listview = (CustomListView)view.findViewById(R.id.listview);
        mAdapter = new TempAdapter();
        listview.setAdapter(mAdapter);
        return view;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    class TempAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(OrderRemindDetailActivity.this)
                    .inflate(R.layout.adapter_order_remind, parent, false);
            TextView line_up = (TextView)convertView.findViewById(R.id.line_up);
            TextView line_down = (TextView)convertView.findViewById(R.id.line_down);
            if(position == 0){
                line_up.setVisibility(View.GONE);
            }
            if(position == getCount()-1){
                line_down.setVisibility(View.GONE);
            }
            return convertView;
        }
    }
}
