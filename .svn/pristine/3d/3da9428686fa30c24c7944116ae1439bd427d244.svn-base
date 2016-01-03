package com.bluemobi.ybb.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.model.AddressModel;
import com.bluemobi.ybb.network.request.AddressRequest;
import com.bluemobi.ybb.network.response.AddressResponse;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.LoadingPage;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzhijun on 2015/8/9.
 */
public class AddressListActivity extends BaseActivity{

    private int selection = 0;

    private List<AddressModel> lists = new ArrayList<AddressModel>();

    private AddressAdapter mAdapter;

    private String category;

    private String hospitalId;
    private String departmentId;
    AddressRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.category = getIntent().getStringExtra("category");
        this.hospitalId = getIntent().getStringExtra("hospitalId");
        this.departmentId = getIntent().getStringExtra("departmentId");
    }

    @Override
    protected void initBaseData() {

    }

    @Override
    protected YbbHttpJsonRequest initRequest() {
        this.category = getIntent().getStringExtra("category");
        this.hospitalId = getIntent().getStringExtra("hospitalId");
        this.departmentId = getIntent().getStringExtra("departmentId");
        AddressRequest request = new AddressRequest(new Response.Listener<AddressResponse>() {
            @Override
            public void onResponse(AddressResponse response) {
                if(response != null && response.getStatus() == 0){
                    if(response.getData().getInfo() != null){
                        lists.addAll(response.getData().getInfo());
                        mAdapter.notifyDataSetChanged();
                    }
                }else{
                }
            }
        }, this);
        request.setCategory(category);
        request.setHospitalId(hospitalId);
        request.setDepartmentId(departmentId);
        request.setCurrentnum(10+"");
        request.setCurrentpage(0+"");
        request.setPageTime("");
        this.request = request;
        return request;
    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {
        PullToRefreshListView pullToRefreshListView = new PullToRefreshListView(this);
        pullToRefreshListView.getRefreshableView().setCacheColorHint(getResources().getColor(R.color.transparent));
        pullToRefreshListView.getRefreshableView().setDivider(new ColorDrawable(Color.rgb(229, 229, 229)));
        pullToRefreshListView.getRefreshableView().setDividerHeight(1);
        pullToRefreshListView.getRefreshableView().setSelector(R.color.transparent);
        mAdapter = new AddressAdapter();
        pullToRefreshListView.getRefreshableView().setAdapter(mAdapter);
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showTitleTextBar(R.string.str_addr_select, R.drawable.common_back, R.string.str_finish);
        pullToRefreshListView.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Logger.e("wangzhijun",position+"");
                if(selection != (position-1)){
                    selection = position-1;
                    mAdapter.notifyDataSetChanged();
                }

            }
        });

        return pullToRefreshListView;
    }

    @Override
    public void clickBarRight() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", lists.get(selection));
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    protected LoadingPage.LoadResult load() {
//        AddressRequest request = new AddressRequest(new Response.Listener<AddressResponse>() {
//            @Override
//            public void onResponse(AddressResponse response) {
//                if(response != null && response.getStatus() == 0){
////                    return LoadingPage.LoadResult.success;
//                    if(response.getInfo() != null){
//                        lists.addAll(response.getInfo());
//                        mAdapter.notifyDataSetChanged();
//                    }
//                }else{
////                    return LoadingPage.LoadResult.success;
//                }
//            }
//        }, this);
//        request.setCategory(category);
//        request.setCategory(hospitalId);
//        request.setCategory(departmentId);
//        request.setCurrentnum(10+"");
//        request.setCurrentpage(0+"");
//        request.setPageTime("");
        return LoadingPage.LoadResult.success;
    }



    class AddressAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return lists == null?0:lists.size();
        }

        @Override
        public Object getItem(int position) {
            return lists==null?null:lists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(AddressListActivity.this).inflate(
                    R.layout.adapter_address, parent, false
            );
            ImageView select = (ImageView)convertView.findViewById(R.id.selected);
            TextView name = (TextView)convertView.findViewById(R.id.name);
            AddressModel model = lists.get(position);
            if("1".equals(category)){
                name.setText(model.getHospitalName());
            }else if("2".equals(category)){
                name.setText(model.getDepartmentName());
            }else if("3".equals(category)){
                name.setText(model.getBedName());
            }
            if (selection == position){
                select.setVisibility(View.VISIBLE);
            }else{
                select.setVisibility(View.GONE);
            }
            return convertView;
        }
    }

}
