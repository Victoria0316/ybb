package com.bluemobi.ybb.ps.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.bluemobi.base.utils.Utils;
import com.bluemobi.ybb.ps.R;
import com.bluemobi.ybb.ps.app.DbManager;
import com.bluemobi.ybb.ps.app.TitleBarManager;
import com.bluemobi.ybb.ps.base.BaseActivity;
import com.bluemobi.ybb.ps.db.entity.PSDisplayMessage;
import com.bluemobi.ybb.ps.db.entity.PSMessage;
import com.bluemobi.ybb.ps.db.entity.PSMessageFoods;
import com.bluemobi.ybb.ps.view.LoadingPage;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * P27 消息
 * Created by wangzhijun on 2015/7/14.
 */
public class MessageActivity extends BaseActivity{

    private TempAdapter mAdapter;

    private PullToRefreshListView listView;

    List<PSDisplayMessage>  data = new ArrayList<PSDisplayMessage>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        showLoadingPage(false);
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.str_message, R.drawable.common_back, false);
//        prepareListData();
    }

    @Override
    protected void initBaseData() {

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
                }
                data.add(bean);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.activity_message, null);
        listView = (PullToRefreshListView)view.findViewById(R.id.listview);
        listView.getRefreshableView().setDivider(new ColorDrawable(Color.rgb(229, 229, 299)));
        listView.getRefreshableView().setDividerHeight(Utils.dip2px(this, 1));
        listView.getRefreshableView().setCacheColorHint(getResources().getColor(R.color.transparent));
        listView.getRefreshableView().setSelector(R.color.transparent);
        mAdapter = new TempAdapter();
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Utils.moveTo(MessageActivity.this, PSOrderRemindActivity.class);
            }
        });
        return view;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    class TempAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(MessageActivity.this).inflate(R.layout.adapter_message,
                    parent, false);
            return convertView;
        }
    }
}
