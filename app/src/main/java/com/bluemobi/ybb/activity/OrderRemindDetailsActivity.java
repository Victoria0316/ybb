package com.bluemobi.ybb.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.bluemobi.ybb.R;
import com.bluemobi.ybb.adapter.CommonAdapter;
import com.bluemobi.ybb.util.DateComparator;
import com.bluemobi.ybb.adapter.TimeLineAdapter;
import com.bluemobi.ybb.adapter.ViewHolder;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.db.entity.DateText;
import com.bluemobi.ybb.util.TimeComparator;
import com.bluemobi.ybb.view.LoadingPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by gaoyn on 2015/7/2.
 *
 * p5-8-1 商品提醒详情
 */
public class OrderRemindDetailsActivity extends BaseActivity{

    private CommonAdapter<String> adapter;
    private List<String> lv = new ArrayList<String>();

    // 时间轴列表
    private ListView lvList;
    // 数据list
    private List<DateText> list;
    // 列表适配器
    private TimeLineAdapter timeadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(OrderRemindDetailsActivity.this,getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.Order_remind_details, R.drawable.common_back, true);

        showLoadingPage(false);
    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.activity_order_remind_details,null);

        ListView order_goods = (ListView) view.findViewById(R.id.order_goods);

        for (int i = 0;i<2;i++)
        {
            lv.add("ddd"+i);
        }
        order_goods.setAdapter(
                adapter = new CommonAdapter<String>(mContext,lv,R.layout.lv_order_details_goods) {
                    @Override
                    public void convert(ViewHolder helper, String item) {

                    }
                }
        );

//        lvList = (ListView)view.findViewById(R.id.order_tracking);
//        list = new ArrayList<DateText>();
//        // 添加测试数据
//        addData();
//        // 将数据按照时间排序
//        DateComparator comparator = new DateComparator();
//        Collections.sort(list, comparator);
//
//        TimeComparator timeComparator = new TimeComparator();
//        Collections.sort(list,timeComparator);
//
//        // listview绑定适配器
//        timeadapter = new TimeLineAdapter(this, list);
//        lvList.setAdapter(timeadapter);

        return view;
    }

    private void addData() {

        DateText date1 = new DateText("0605", "13:50","已评价");
        DateText date2 = new DateText("0604", "12:00","抢单成功");
        DateText date3 = new DateText("0605", "12:45","已收货，等待评价");
        DateText date4 = new DateText("0605", "12:45","配餐员（王志伟）已取货");

        list.add(date1);
        list.add(date2);
        list.add(date3);
        list.add(date4);
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
