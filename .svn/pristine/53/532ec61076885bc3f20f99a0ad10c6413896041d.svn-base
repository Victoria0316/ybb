package com.bluemobi.ybb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.adapter.CommonAdapter;
import com.bluemobi.ybb.adapter.ViewHolder;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.request.InformationLisRequest;
import com.bluemobi.ybb.network.response.InformationListResponse;
import com.bluemobi.ybb.util.Constants;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.LoadingPage;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoyn on 2015/7/6.
 * Modify by liufy 2015/8/10 add debugging interface
 *
 * p11 健康资讯
 */
public class InformationListActivity extends BaseActivity implements View.OnClickListener{

    private CommonAdapter<InformationListResponse.InformationData.InformationDTo> adapter;

    private InformationLisRequest request;


    private List<InformationListResponse.InformationData.InformationDTo> dataList = new ArrayList<InformationListResponse.InformationData.InformationDTo>();

    ArrayList<InformationListResponse.InformationData.InformationDTo> info;

    private RelativeLayout rl_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(InformationListActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.information, R.drawable.common_back, true);

        showLoadingPage(true);

    }

    @Override
    protected void initBaseData() {

    }

    @Override
    protected YbbHttpJsonRequest initRequest() {
        InformationLisRequest request;
        request = new InformationLisRequest
                (
                        new Response.Listener<InformationListResponse>() {
                            @Override
                            public void onResponse(InformationListResponse response) {
                                Utils.closeDialog();
                                plv_refresh.onRefreshComplete();
                                if (response.getStatus() == 0)
                                {
                                    showListData(response.data);
                                }
                                else
                                {
                                    Toast.makeText(mContext, response.getContent(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, this);
        request.setLoginuserid(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setCurrentnum(Constants.CURRENTNUMBASE + "");
        request.setColumnId("2");
        request.setCurrentpage(0+"");
        this.request = request;
        return request;
    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.activity_information_list,null);

        plv_refresh = (PullToRefreshListView) view.findViewById(R.id.plv_refresh);
        initPullToRefresh(plv_refresh);
        plv_refresh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Utils.moveTo(InformationListActivity.this, InformationDetailsActivity.class);
                Intent intent = new Intent(mContext,InformationDetailsActivity.class);
                InformationListResponse.InformationData.InformationDTo informationDTo = dataList.get(i);
                intent.putExtra("articleId",informationDTo.id);
                startActivity(intent);
            }
        });


        rl_search = (RelativeLayout)view.findViewById(R.id.rl_search);
        rl_search.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.rl_search:
                Utils.moveTo(this,HomeSearchActivity.class);
                break;
        }
    }

    @Override
    protected boolean getPage(int type) {
        super.getPage(type);
        connectToServer();
        return true;

    }

    private void connectToServer()
    {
        InformationLisRequest request = new InformationLisRequest
                (
                        new Response.Listener<InformationListResponse>() {
                            @Override
                            public void onResponse(InformationListResponse response) {
                                Utils.closeDialog();
                                plv_refresh.onRefreshComplete();
                                if (response.getStatus() == 0)
                                {
                                    showListData(response.data);
                                }
                                else
                                {
                                    Toast.makeText(mContext, response.getContent(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, this);
        request.setLoginuserid(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setCurrentnum(Constants.CURRENTNUMBASE + "");
        request.setColumnId("2");
        request.setCurrentpage(getCurPage() + "");
        WebUtils.doPost(request);
    }





    /**
     *
     * 显示列表数据
     *
     * */
    private void showListData(InformationListResponse.InformationData list)
    {


        if (list == null)
        {
            return;
        }
        if (list.info.size() == 0)
        {
            return;
        }

        if (list.getCurrentpage().equals("0"))
        {
            dataList.clear();
        }

        for (InformationListResponse.InformationData.InformationDTo lineDto : list.info)
        {
            dataList.add(lineDto);
        }

        if (adapter == null)
        {
            plv_refresh.setAdapter(
                    adapter = new CommonAdapter<InformationListResponse.InformationData.InformationDTo>(mContext,
                            dataList, R.layout.lv__main_info_item) {

                        @Override
                        public void convert(ViewHolder helper, InformationListResponse.InformationData.InformationDTo bean) {
                            helper.setImageByUrl(R.id.iv_image_bg, bean.imgList.get(0), mContext);
                            helper.setText(R.id.tv_artTitle, bean.artTitle);
                            helper.setText(R.id.tv_artAbstract,bean.artAbstract);
                        }
                    });

        }
        else
        {
            adapter.notifyDataSetChanged();
        }
    }
}
