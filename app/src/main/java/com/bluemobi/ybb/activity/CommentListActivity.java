package com.bluemobi.ybb.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.adapter.CommonAdapter;
import com.bluemobi.ybb.adapter.ViewHolder;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.model.Articlecomment;
import com.bluemobi.ybb.network.model.Articlecommentinfo;
import com.bluemobi.ybb.network.model.IntegralBillInfo;
import com.bluemobi.ybb.network.request.ArticlecommentinfoRequest;
import com.bluemobi.ybb.network.request.InformationLisRequest;
import com.bluemobi.ybb.network.request.OrderinfoRequest;
import com.bluemobi.ybb.network.response.ArticlecommentinfoResponse;
import com.bluemobi.ybb.network.response.InformationListResponse;
import com.bluemobi.ybb.network.response.OrderinfoResponse;
import com.bluemobi.ybb.util.Constants;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.LoadingPage;
import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoyn on 2015/7/6.
 * <p/>
 * P11-2评论列表
 */
public class CommentListActivity extends BaseActivity {
    private final static String tag = "CommentListActivity";

    private CommonAdapter<String> adapter;
    private List<String> lv = new ArrayList<String>();

    private String articleId;

    private List<Articlecommentinfo> info = new ArrayList<Articlecommentinfo>();
    private int currentPage =  0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(CommentListActivity.this, getSupportActionBar());
        titleBarManager.showTitleTextBar(R.string.evaluation, R.drawable.common_back, R.string.MyEvaluation);

        showLoadingPage(false);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected YbbHttpJsonRequest initRequest() {

        ArticlecommentinfoRequest request = new ArticlecommentinfoRequest(new Response.Listener<ArticlecommentinfoResponse>() {
            @Override
            public void onResponse(ArticlecommentinfoResponse response) {
                plv_refresh.onRefreshComplete();
                if (response != null && response.getStatus() == 0) {

                    info.clear();
                    Logger.d(tag, response.getData().getCurrentpage());
                    Logger.d(tag, response.getData().getInfo().get(0).getArticleId());

                    showListData(response.getData());
                    CommentListActivity.super.getPage(LOAD_REFRESH);

                } else {
                    Toast.makeText(mContext, response.getContent(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }, CommentListActivity.this);

        request.setArticleid(articleId);
        request.setCurrentnum(Constants.CURRENTNUMBASE + "");
        request.setCurrentpage(0 + "");
        request.setHandleCustomErr(false);
        return request;


    }

    @Override
    protected void initBaseData() {
        articleId = getIntent().getStringExtra("articleId");
        Logger.d(tag, "articleId:" + articleId);

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.activity_comment_list, null);


        plv_refresh = (PullToRefreshListView) view.findViewById(R.id.plv_refresh);
        initPullToRefresh(plv_refresh);

//        plv_refresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        plv_refresh.onRefreshComplete();
//                    }
//                }, 500);
//            }
//
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        plv_refresh.onRefreshComplete();
//
//                    }
//                }, 500);
//
//            }
//        });
//        for (int i = 0; i < 10; i++) {
//            lv.add("ddd" + i);
//        }



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
    public void clickBarRight() {
        Intent intent = new Intent();
        intent.putExtra("articleId", articleId);
        intent.setClass(this, MyEvaluationActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == 0){
                getPage(LOAD_REFRESH);
            }
        }
    }

    @Override
    protected boolean getPage(int type) {
        super.getPage(type);
        if(type == LOAD_REFRESH){
            info.clear();
            currentPage = 0;
        }else{
            currentPage ++;
        }
        connectToServer();
        return true;

    }

    private void connectToServer() {
        ArticlecommentinfoRequest request = new ArticlecommentinfoRequest(new Response.Listener<ArticlecommentinfoResponse>() {
            @Override
            public void onResponse(ArticlecommentinfoResponse response) {
                plv_refresh.onRefreshComplete();

                if (response != null && response.getStatus() == 0) {


                    Logger.d(tag, response.getData().getCurrentpage());
                    Logger.d(tag, response.getData().getInfo().get(0).getArticleId());


                    showListData(response.getData());

                } else {
                    Toast.makeText(mContext, response.getContent(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }, CommentListActivity.this);

        request.setArticleid(articleId);
        request.setCurrentnum(Constants.CURRENTNUMBASE + "");
        request.setCurrentpage(currentPage + "");
        request.setHandleCustomErr(false);
        WebUtils.doPost(request);

    }


    /**
     * 显示列表数据
     */
    private void showListData(Articlecomment list) {


        if (list == null) {
            return;
        }
        if (list.getInfo().size() == 0) {
            return;
        }

        if (list.getCurrentpage().equals("0")) {
            info.clear();
        } else {
            info.addAll(list.getInfo());
        }

        if (adapter == null) {
            plv_refresh.setAdapter(
                    new CommonAdapter<Articlecommentinfo>(mContext,
                            info, R.layout.lv_comment_list) {

                        @Override
                        public void convert(ViewHolder helper, Articlecommentinfo item) {
                            TextView nickname = helper.getView(R.id.nickname);
                            TextView content = helper.getView(R.id.content);
                            ImageView head = helper.getView(R.id.head);

                            nickname.setText(item.getCommentNickName());
                            content.setText(item.getCommentInfo());

                            Glide.with(CommentListActivity.this)
                                    .load(item.getImg())
                                    .placeholder(R.drawable.mine_phote_bg)
                                    .into(head);
                        }
                    });

        } else {
            adapter.notifyDataSetChanged();
        }
    }
}
