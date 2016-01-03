package com.bluemobi.ybb.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.model.AddressModel;
import com.bluemobi.ybb.network.model.CommentModel;
import com.bluemobi.ybb.network.request.AddressRequest;
import com.bluemobi.ybb.network.request.CommentsListRequest;
import com.bluemobi.ybb.network.request.InformationLisRequest;
import com.bluemobi.ybb.network.response.AddressResponse;
import com.bluemobi.ybb.network.response.CommentsListResponse;
import com.bluemobi.ybb.network.response.InformationListResponse;
import com.bluemobi.ybb.util.Constants;
import com.bluemobi.ybb.util.GlideCircleTransform;
import com.bluemobi.ybb.util.StringUtils;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.LoadingPage;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzhijun on 2015/7/10.
 * 餐品更多评论页面
 */
public class ReviewsActivity extends BaseActivity {

    private PullToRefreshListView mListView;

    private LinearLayout mMainLayout;

    private ReviewsAdapter mAdapter;

    private List<CommentModel> lists = new ArrayList<CommentModel>();

    private String id;
    CommentsListRequest request;
    RequestManager glideRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.Evaluation, R.drawable.common_back, true);
        showLoadingPage(true);
    }

    @Override
    protected boolean getPage(int type) {

        if(!super.getPage(type)){
            return false;
        }
        connectToServer();
        return true;

    }

    private void connectToServer()
    {
        CommentsListRequest request = new CommentsListRequest(new Response.Listener<CommentsListResponse>() {
            @Override
            public void onResponse(CommentsListResponse response) {
                plv_refresh.onRefreshComplete();
                if(response != null && response.getStatus() == 0){
                    if(response.getData().getInfo() != null){
                        if (response.getData().getCurrentpage().equals("1"))
                        {
                            lists.clear();
                        }
                        lists.addAll(response.getData().getInfo());

                        if(mAdapter!=null){
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }, this);
        request.setProductId(id);
        request.setCurrentnum(Constants.CURRENTNUMBASE + "");
        request.setCurrentpage(getCurPage() + "");
        WebUtils.doPost(request);
    }
    @Override
    protected void initBaseData() {
    }

    @Override
    protected YbbHttpJsonRequest initRequest() {
        id = getIntent().getStringExtra("id");
        glideRequest = Glide.with(this);
        CommentsListRequest request = new CommentsListRequest(new Response.Listener<CommentsListResponse>() {
            @Override
            public void onResponse(CommentsListResponse response) {
                if(response != null && response.getStatus() == 0){
                    if(response.getData().getInfo() != null){
                        lists.addAll(response.getData().getInfo());
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        }, this);
        request.setProductId(id);
        request.setCurrentnum(Constants.PAGE_NUM);
        request.setCurrentpage("0");
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
        initPullToRefresh(pullToRefreshListView);
        mAdapter = new ReviewsAdapter();
        pullToRefreshListView.getRefreshableView().setAdapter(mAdapter);
        return pullToRefreshListView;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    class ReviewsAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return lists==null ?0:lists.size();
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
            convertView = LayoutInflater.from(ReviewsActivity.this).inflate(R.layout.adapter_reviews,parent,false);
            TextView name = (TextView)convertView.findViewById(R.id.user_nickname);
            ImageView pic = (ImageView)convertView.findViewById(R.id.user_avatar);
            RatingBar score = (RatingBar)convertView.findViewById(R.id.score);
            TextView user_reviews = (TextView)convertView.findViewById(R.id.user_reviews);
            TextView time = (TextView)convertView.findViewById(R.id.time);
            CommentModel bean = lists.get(position);
            String disName="";
            if (StringUtils.isNotEmpty(bean.getUserName())){
                if (bean.getUserName().length()>1){
                    int l=bean.getUserName().length();
                    StringBuffer star=new StringBuffer();
                    for (int i=0;i<l-1;i++){
                        star.append("*");
                    }
                    disName=bean.getUserName().substring(0, 1)+star;
                }else {
                    disName=bean.getUserName();
                }
            }
            name.setText(disName);
            if (StringUtils.isNotEmpty(bean.getStarScore())){
                score.setRating(Float.parseFloat(bean.getStarScore()));
            }else {
                score.setRating(0);
            }
            user_reviews.setText(bean.getContent());
            time.setText(bean.getCreateTime());
            if (StringUtils.isNotEmpty(bean.getImg())&& !"null".equals(bean.getImg())){
                glideRequest.load(bean.getImg()).transform(new GlideCircleTransform(mContext)).into(pic);
            }else {
                pic.setImageResource(R.drawable.loading);
            }
            return convertView;
        }
    }

}
