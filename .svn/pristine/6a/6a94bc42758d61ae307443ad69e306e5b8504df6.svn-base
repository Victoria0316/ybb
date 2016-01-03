package com.bluemobi.ybb.ps.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;

import com.bluemobi.ybb.ps.R;
import com.bluemobi.ybb.ps.adapter.PSOrderExpandableAdapter;
import com.bluemobi.ybb.ps.app.DbManager;
import com.bluemobi.ybb.ps.app.TitleBarManager;
import com.bluemobi.ybb.ps.base.BaseActivity;
import com.bluemobi.ybb.ps.db.entity.PSDisplayMessage;
import com.bluemobi.ybb.ps.db.entity.PSMessage;
import com.bluemobi.ybb.ps.db.entity.PSMessageFoods;
import com.bluemobi.ybb.ps.view.LoadingPage;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoyn on 2015/7/2.
 *p5-8 订单提醒
 */
public class PSOrderRemindActivity extends BaseActivity {

    PSOrderExpandableAdapter listAdapter;
    ExpandableListView expListView;
    List<PSDisplayMessage>  data = new ArrayList<PSDisplayMessage>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(PSOrderRemindActivity.this,getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.order_remind, R.drawable.common_back,true);

        showLoadingPage(false);


    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.activity_psorder_remind,null);

        expListView = (ExpandableListView)view.findViewById(R.id.expandable);
        prepareListData();
        listAdapter = new PSOrderExpandableAdapter(this, data);
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
                Intent intent = new Intent(PSOrderRemindActivity.this, PSOrderRemindDetailsFoodActivity.class);
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
    List<PSMessage> listDataHeader=new ArrayList<PSMessage>();
    DbUtils db = DbManager.getInstance(mContext).getDefaultDbUtils();
    try {
        listDataHeader = db.findAll(Selector.from(PSMessage.class).where("isread", "=", "0"));
        if (listDataHeader.size()!=0) {
            PSDisplayMessage bean= new  PSDisplayMessage();
            List<PSMessageFoods>  listDataChild =new ArrayList<PSMessageFoods>() ;
            for (int i=0;i<listDataHeader.size();i++){
                listDataChild=db.findAll(Selector.from(PSMessageFoods.class).where("orderid", "=", listDataHeader.get(i).getOrderId()));
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