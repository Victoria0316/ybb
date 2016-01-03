package com.bluemobi.ybb.activity;

import android.app.ExpandableListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluemobi.ybb.R;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.view.LoadingPage;

import java.util.ArrayList;
import java.util.List;

/**
 * P14-11 所有明细
 * Created by wangzhijun on 2015/7/11.
 */
public class AccountAllHisActivity extends BaseActivity{

    private ExpandableListView listView;

    private List<TempGroup> groups = new ArrayList<TempGroup>();
    private List<List<TempChild>> childs = new ArrayList<List<TempChild>>();
    private AllHisAdapter mAdapter;
    private List<String> spinners = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLoadingPage(false);
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        spinners.add("餐品");
        spinners.add("商品");
        titleBarManager.showSpinnerTitlerBar(spinners,R.drawable.common_back);
        titleBarManager.getCs_titlebar_spinner().getTv_label().setTextColor(Color.WHITE);
    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.activity_account_all_his, null);
        listView = (ExpandableListView)view.findViewById(R.id.all_his);
        groups.add(new TempGroup("2015/07-12 12:50"));
        groups.add(new TempGroup("2015/07-13 12:50"));
        groups.add(new TempGroup("2015/07-14 12:50"));

        List<TempChild> temp1 = new ArrayList<TempChild>();
        temp1.add(new TempChild("title1"));
        temp1.add(new TempChild("title2"));
        temp1.add(new TempChild("title3"));
        List<TempChild> temp2= new ArrayList<TempChild>();
        temp2.add(new TempChild("title1"));
        temp2.add(new TempChild("title2"));
        temp2.add(new TempChild("title3"));
        List<TempChild> temp3 = new ArrayList<TempChild>();
        temp3.add(new TempChild("title1"));
        temp3.add(new TempChild("title2"));
        temp3.add(new TempChild("title3"));
        childs.add(temp1);
        childs.add(temp2);
        childs.add(temp3);
        mAdapter = new AllHisAdapter();
        listView.setAdapter(mAdapter);
        for(int i=0; i < mAdapter.getGroupCount(); i++)
            listView.expandGroup(i);
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        return view;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }
    class AllHisAdapter extends BaseExpandableListAdapter{

        @Override
        public int getGroupCount() {
            return groups.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return childs.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groups.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childs.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            TextView textView = new TextView(getBaseContext());
            textView.setPadding(Utils.dip2px(getBaseContext(),10), Utils.dip2px(getBaseContext(),15),
                    Utils.dip2px(getBaseContext(),10),Utils.dip2px(getBaseContext(),15));
            textView.setTextAppearance(getBaseContext(), R.style.text_13_sp);
            textView.setTextColor(getResources().getColor(R.color.common_gray));
            textView.setText("2015/07/12 14:20");
            convertView = textView;
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(AccountAllHisActivity.this).inflate(R.layout.adapter_account_al_his,parent,false);
            ImageView imageView = (ImageView)convertView.findViewById(R.id.item_img);
            TextView title = (TextView)convertView.findViewById(R.id.title);
            TextView price = (TextView)convertView.findViewById(R.id.price);
            TextView score = (TextView)convertView.findViewById(R.id.score);
            TextView score_label = (TextView)convertView.findViewById(R.id.score_label);
            TextView count = (TextView)convertView.findViewById(R.id.count);
            TextView coupon = (TextView)convertView.findViewById(R.id.coupon);
            if(childPosition % 2 == 0){
                ((RelativeLayout.LayoutParams)coupon.getLayoutParams()).addRule(
                        RelativeLayout.ALIGN_BOTTOM, imageView.getId());

                ((RelativeLayout.LayoutParams)price.getLayoutParams()).addRule(
                        RelativeLayout.CENTER_VERTICAL);
                ((RelativeLayout.LayoutParams)score.getLayoutParams()).addRule(
                        RelativeLayout.CENTER_VERTICAL);
                ((RelativeLayout.LayoutParams)score_label.getLayoutParams()).addRule(
                        RelativeLayout.CENTER_VERTICAL);
            }else{
                coupon.setVisibility(View.GONE);
                ((RelativeLayout.LayoutParams)price.getLayoutParams()).addRule(
                        RelativeLayout.ALIGN_BOTTOM, imageView.getId());
                ((RelativeLayout.LayoutParams)price.getLayoutParams()).bottomMargin =
                        Utils.dip2px(AccountAllHisActivity.this, 10);
                ((RelativeLayout.LayoutParams) score.getLayoutParams()).addRule(
                        RelativeLayout.CENTER_VERTICAL, imageView.getId());
                ((RelativeLayout.LayoutParams)score.getLayoutParams()).bottomMargin =
                        Utils.dip2px(AccountAllHisActivity.this, 10);
                ((RelativeLayout.LayoutParams)score_label.getLayoutParams()).addRule(
                        RelativeLayout.CENTER_VERTICAL, imageView.getId());
                ((RelativeLayout.LayoutParams)score_label.getLayoutParams()).bottomMargin =
                        Utils.dip2px(AccountAllHisActivity.this, 10);

                ((RelativeLayout.LayoutParams)price.getLayoutParams()).addRule(
                        RelativeLayout.CENTER_VERTICAL, 0);//0 失效

                ((RelativeLayout.LayoutParams)score.getLayoutParams()).addRule(
                        RelativeLayout.CENTER_VERTICAL, 0);//0 失效

                ((RelativeLayout.LayoutParams)score_label.getLayoutParams()).addRule(
                        RelativeLayout.CENTER_VERTICAL, 0);//0 失效

            }

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }

    class TempGroup {
        String time;

        public TempGroup(String time) {
            this.time = time;
        }
    }
    class TempChild {
        String title;

        public TempChild(String title) {
            this.title = title;
        }
    }
}
