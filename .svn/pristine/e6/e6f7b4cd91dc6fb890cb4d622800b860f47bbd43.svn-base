package com.bluemobi.ybb.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.network.model.Articleinfo;
import com.bluemobi.ybb.network.request.ArticleinfoRequest;
import com.bluemobi.ybb.network.request.CollectionRequest;
import com.bluemobi.ybb.network.request.DelCollectionRequest;
import com.bluemobi.ybb.network.request.PraiseDelRequest;
import com.bluemobi.ybb.network.request.PraiseRequest;
import com.bluemobi.ybb.network.response.ArticleinfoResponse;
import com.bluemobi.ybb.network.response.CollectionResponse;
import com.bluemobi.ybb.network.response.DelCollectionResponse;
import com.bluemobi.ybb.network.response.PraiseDelResponse;
import com.bluemobi.ybb.network.response.PraiseResponse;
import com.bluemobi.ybb.util.StringUtils;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.LoadingPage;

/**
 * Created by gaoyn on 2015/7/6.
 * <p>
 * p11-1
 */
public class InformationDetailsActivity extends BaseActivity implements View.OnClickListener {
    private final static String tag = "InformationDetailsActivity";

    private WebView mWebView;

    private TextView collection;
    private TextView praise;
    private TextView comments;
    private TextView comments_list;

    private Drawable bottom_like_bright;
    private Drawable bottom_like;

    private Drawable bottom_praise_bright;
    private Drawable bottom_praise;

    private String articleId;

    private Articleinfo articleinfo;


    /**
     * 是否收藏
     */
    private boolean isColl = true;
    /**
     * 是否赞
     */
    private boolean isPraise = true;
    /**
     * 赞 数量
     */
    private int praiseCount;
    /**
     * 赞 数量
     */
    private int collCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(InformationDetailsActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.information_details, R.drawable.common_back, true);

        showLoadingPage(false);
    }

    @Override
    protected void initBaseData() {
        articleId = getIntent().getStringExtra("articleId");
    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.activity_information_details, null);


        mWebView = (WebView) view.findViewById(R.id.information_details);

        //mWebView.loadDataWithBaseURL(null, "123", "text/html", "utf-8", null);
//        mWebView.loadData("InformationDetailsActivity", "text/html; charset=UTF-8", null);


        collection = (TextView) view.findViewById(R.id.collection);
        bottom_like_bright = getResources().getDrawable(R.drawable.bottom_like_bright);
        bottom_like = getResources().getDrawable(R.drawable.bottom_like);

        praise = (TextView) view.findViewById(R.id.praise);
        bottom_praise_bright = getResources().getDrawable(R.drawable.bottom_praise_bright);
        bottom_praise = getResources().getDrawable(R.drawable.bottom_praise);

        comments = (TextView) view.findViewById(R.id.comments);
        comments.setOnClickListener(this);

        comments_list = (TextView) view.findViewById(R.id.comments_list);
        comments_list.setOnClickListener(this);

        ArticleinfoRequest();


        return view;
    }

    private void ArticleinfoRequest() {
        ArticleinfoRequest request = new ArticleinfoRequest(new Response.Listener<ArticleinfoResponse>() {
            @Override
            public void onResponse(ArticleinfoResponse response) {

                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {

                    Logger.d(tag, "根据id获取资讯详情--P11-1__ ok");
                    articleinfo = response.getData();
                    Logger.d(tag, "\"artContent\": \"文章内容\":" + articleinfo.getArtContent());

                    mWebView.loadData(articleinfo.getArtContent(), "text/html; charset=UTF-8", null);

                    comments_list.setText(articleinfo.getCommentCount());
                    collection.setText(articleinfo.getCollectCount());
                    praise.setText(articleinfo.getPraiseCount());
                    praiseCount = Integer.parseInt(StringUtils.isEmpty(articleinfo.getPraiseCount())
                            ? "0" :articleinfo.getPraiseCount());
                    collCount = Integer.parseInt(StringUtils.isEmpty(articleinfo.getCollectCount())
                            ? "0" :articleinfo.getCollectCount());

                    /*
                    * 点赞
                    * */

                    bottom_praise_bright.setBounds(0, 0, bottom_praise_bright.getMinimumWidth(), bottom_praise_bright.getMinimumHeight());
                    bottom_praise.setBounds(0, 0, bottom_praise.getMinimumWidth(), bottom_praise.getMinimumHeight());

                    if ("1".equals(articleinfo.getIsPraise())) {  //"是否被当前用户点赞，0未点赞，1已点赞"
                        praise.setCompoundDrawables(bottom_praise_bright, null, null, null);
                        isPraise = true;
                    } else {
                        praise.setCompoundDrawables(bottom_praise, null, null, null);
                        isPraise = false;
                    }
                    praise.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (isPraise) {
                                PraiseDel();
                            } else {
                                Praise();
                            }
                        }
                    });

                    /*
                    * 收藏
                    * */

                    bottom_like_bright.setBounds(0, 0, bottom_like_bright.getMinimumWidth(), bottom_like_bright.getMinimumHeight());
                    bottom_like.setBounds(0, 0, bottom_like.getMinimumWidth(), bottom_like.getMinimumHeight());

                    if ("1".equals(articleinfo.getIsColl())) { //是否被当前用户收藏，0：未收藏，1：已收藏
                        collection.setCompoundDrawables(bottom_like_bright, null, null, null);
                        isColl = true;
                    } else {
                        collection.setCompoundDrawables(bottom_like, null, null, null);
                        isColl = false;
                    }
                    collection.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (isColl) {
                                setDelCollectionRequest();
                            } else {
                                setCollectionRequest();
                            }
                        }
                    });


                } else {
                    Toast.makeText(InformationDetailsActivity.this, "", Toast.LENGTH_LONG).show();
                }


            }
        }, InformationDetailsActivity.this);

        request.setId(articleId);
        request.setLoginuserid(YbbApplication.getInstance().getMyUserInfo().getUserId());

        WebUtils.doPost(request);

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
        switch (v.getId()) {
            case R.id.comments_list:

                Intent intent_1 = new Intent();
                intent_1.setClass(InformationDetailsActivity.this, CommentListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("articleId", articleId);
                intent_1.putExtras(bundle);
                startActivity(intent_1);

//                Utils.moveTo(this, CommentListActivity.class);
                break;
            case R.id.comments:
                Intent intent = new Intent(mContext, MyEvaluationActivity.class);
                intent.putExtra("articleId", articleId);
                startActivity(intent);
                break;
        }
    }

    /*
    * 取消点赞
    * */

    private void PraiseDel() {

        PraiseDelRequest request = new PraiseDelRequest(new Response.Listener<PraiseDelResponse>() {
            @Override
            public void onResponse(PraiseDelResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    Utils.makeToastAndShow(mContext, "取消点赞");
                    praise.setCompoundDrawables(bottom_praise, null, null, null);
                    praiseCount--;
                    praise.setText(String.valueOf(praiseCount));
                    isPraise = false;
                } else {// failed
                    Log.e("error", "error");
                }
            }
        }, this);
        request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setPraiseId(articleId);
        request.setPraiseType("3");
        Utils.showProgressDialog(mContext);
        WebUtils.doPost(request);

    }

    /*
    * 点赞成功
    * */
    private void Praise() {

        PraiseRequest request = new PraiseRequest(new Response.Listener<PraiseResponse>() {
            @Override
            public void onResponse(PraiseResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    Utils.makeToastAndShow(mContext, "点赞成功");
                    praise.setCompoundDrawables(bottom_praise_bright, null, null, null);
                    praiseCount++;
                    isPraise = true;
                    praise.setText(String.valueOf(praiseCount));                } else {// failed
                    Log.e("error", "error");
                }
            }
        }, this);
        request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setPraiseId(articleId);
        request.setPraiseType("3");
        Utils.showProgressDialog(mContext);
        WebUtils.doPost(request);

    }

   /*
   * 收藏
   * */

    private void setCollectionRequest() {

        CollectionRequest request = new CollectionRequest(new Response.Listener<CollectionResponse>() {
            @Override
            public void onResponse(CollectionResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    Utils.makeToastAndShow(mContext, "收藏成功");
                    collection.setCompoundDrawables(bottom_like_bright, null, null, null);
                    collection.setText(Integer.parseInt(articleinfo.getCollectCount()) + 1 + "");
                    collCount++;
                    collection.setText(String.valueOf(collCount));
                    isColl = true;

                } else {// failed
                    Log.e("error", "error");
                }
            }
        },this);
        request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setCollectionId(articleId);
        request.setCollectionType("4");
        Utils.showProgressDialog(mContext);
        WebUtils.doPost(request);

    }

    /*
    * 取消收藏
    * */

    private void setDelCollectionRequest() {

        DelCollectionRequest request = new DelCollectionRequest(new Response.Listener<DelCollectionResponse>() {
            @Override
            public void onResponse(DelCollectionResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    Utils.makeToastAndShow(mContext, "取消收藏");
                    collection.setCompoundDrawables(bottom_like, null, null, null);
                    collCount--;
                    collection.setText(String.valueOf(collCount));
                    isColl = false;

                } else {// failed
                    Log.e("error", "error");
                }
            }
        },this);
        request.setUserid(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setCollectionId(articleId);
        request.setCollectionType("4");
        Utils.showProgressDialog(mContext);
        WebUtils.doPost(request);

    }
}
