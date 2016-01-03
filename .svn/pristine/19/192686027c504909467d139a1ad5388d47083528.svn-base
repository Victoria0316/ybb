package com.bluemobi.ybb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;

import com.bluemobi.ybb.R;
import com.bluemobi.ybb.adapter.OrderExpandableAdapter;
import com.bluemobi.ybb.app.DbManager;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.db.entity.DisplayMessage;
import com.bluemobi.ybb.db.entity.Message;
import com.bluemobi.ybb.db.entity.MessageFoods;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.view.LoadingPage;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoyn on 2015/7/2.
 *p5-8 订单提醒
 */
public class OrderRemindActivity extends BaseActivity {

    OrderExpandableAdapter listAdapter;
    ExpandableListView expListView;
    List<DisplayMessage>  data = new ArrayList<DisplayMessage>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(OrderRemindActivity.this,getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.order_remind,R.drawable.common_back,true);

        showLoadingPage(false);


    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.activity_order_remind,null);

        expListView = (ExpandableListView)view.findViewById(R.id.expandable);
        prepareListData();
        listAdapter = new OrderExpandableAdapter(this, data);
        expListView.setAdapter(listAdapter);
        expListView.setGroupIndicator(null);
        listAdapter.notifyDataSetChanged();

        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                return true;
            }
        });

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int i1, long l) {
                Intent intent = new Intent(OrderRemindActivity.this, OrderRemindDetailsFoodActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("id", data.get(groupPosition).getId());
                intent.putExtras(bundle);
                startActivity(intent);


                return false;
            }
        });
        for(int i=0; i < listAdapter.getGroupCount(); i++)
            expListView.expandGroup(i);

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

    /*
         * Preparing the list data
         */
    private void prepareListData() {
    List<Message> listDataHeader=new ArrayList<Message>();
    DbUtils db = DbManager.getInstance(mContext).getDefaultDbUtils();
    try {
        listDataHeader = db.findAll(Selector.from(Message.class).where("isread", "=", "0"));
        if (listDataHeader.size()!=0) {
            DisplayMessage bean= new  DisplayMessage();
            List<MessageFoods>  listDataChild =new ArrayList<MessageFoods>() ;
            for (int i=0;i<listDataHeader.size();i++){
                listDataChild=db.findAll(Selector.from(MessageFoods.class).where("orderid", "=", listDataHeader.get(i).getOrderId()));
                bean.setChildBean(listDataChild);
                bean.setCreateTime(listDataHeader.get(i).getCreateTime());
                bean.setOrderId(listDataHeader.get(i).getOrderId());
                bean.setOrderStatus(listDataHeader.get(i).getOrderStatus());
                bean.setId(String.valueOf(listDataHeader.get(i).getId()));
                bean.setOrderNo(listDataHeader.get(i).getOrderNo());
                data.add(bean);
            }
        }
    } catch (DbException e) {
        e.printStackTrace();
    }
    }
}