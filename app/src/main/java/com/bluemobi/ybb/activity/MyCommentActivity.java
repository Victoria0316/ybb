package com.bluemobi.ybb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.adapter.CommentBatchAdapter1;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.network.model.CommentBatchChild;
import com.bluemobi.ybb.network.model.OrderAttribute;
import com.bluemobi.ybb.network.model.OrderAttributeChild;
import com.bluemobi.ybb.network.model.OrderItem;
import com.bluemobi.ybb.network.request.CommentBatchRequest;
import com.bluemobi.ybb.network.response.CommentBatchResponse;
import com.bluemobi.ybb.util.Constants;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.LoadingPage;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by liufy on 2015/6/29.
 * P14-3添加评价
 */
public class MyCommentActivity extends BaseActivity implements View.OnClickListener
{

    private TitleBarManager titleBarManager;

    private TextView tv_submit;

    private EditText content;
    private RatingBar ratingBar;

    private OrderItem orderItem;

    private ListView mListView;

    private ArrayList<CommentBatchChild> dataLists = new ArrayList<CommentBatchChild>();

    private CommentBatchAdapter1 mAdapter;

    private float score = 0;

    private String deliverymanId;
    private int index;
    private boolean isComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        titleBarManager = new TitleBarManager();
        titleBarManager.init(MyCommentActivity.this,getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.str_mine_order_want_comment,R.drawable.common_back,true);
        showLoadingPage(false);
        orderItem = (OrderItem)getIntent().getSerializableExtra("item");
        index = getIntent().getIntExtra("index", -1);
        isComment = getIntent().getBooleanExtra("isComment", false);
        Logger.e("wangzhijun", "MyCommentActivity--> " + isComment);

        prepare();
        refreshView();
        Logger.e("wangzhijun", orderItem.getCreateTime());
    }

    private void refreshView() {
        mAdapter = new CommentBatchAdapter1(this, dataLists);
        mListView.setAdapter(mAdapter);
    }

    private void prepare() {
        dataLists.clear();
        List<OrderAttribute> list1 = orderItem.getLogisticsDistributionInfoDTOList();
        int size = list1.size();
        for(int i=0; i< size; i++) {
            OrderAttribute item = list1.get(i);
            List<OrderAttributeChild> list2 = item.getProductComboGroupDTOList();
            int size2 = list2.size();
            for(int j=0; j< size2; j++){
                OrderAttributeChild child = list2.get(j);
                CommentBatchChild model = new CommentBatchChild();
                model.setOrderId(orderItem.getId());
                model.setUserId(YbbApplication.getInstance().getMyUserInfo()
                        .getUserId());
                model.setProductId(child.getId());
                model.setUserName(YbbApplication.getInstance().getMyUserInfo()
                .getNickName());
                model.setProductName(child.getComboName());
                model.setSourceType("1");
                model.setUserIp("");
//                model.setStarScore(dataLists.size()+1 +"");
//                model.setContent(dataLists.size()+1 +"");
                deliverymanId = item.getDeliverymanId();
                model.setLogisticsId(item.getDeliverymanId());//配餐员ID
                model.setCustomerServiceId("");//被评价客服ID
                model.setImg(child.getImgList() ==null?"":
                        child.getImgList().get(0));
                dataLists.add(model);
            }
        }
    }

    public TitleBarManager getTitleBarManager() {
        return titleBarManager;
    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.activity_comment, null);

        ratingBar = (RatingBar)view.findViewById(R.id.ratingBar);
        mListView = (ListView)view.findViewById(R.id.listView);
        tv_submit = (TextView)view.findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                score = rating;
            }
        });

        return view;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tv_submit:
//                Request();
                doComment();
                break;
        }
    }

    private void doComment() {
        CommentBatchRequest request = new CommentBatchRequest(new Response.Listener<CommentBatchResponse>() {
            @Override
            public void onResponse(CommentBatchResponse response) {
                if(response != null && response.getStatus() ==0){
                    Utils.makeToastAndShow(getBaseContext(), "评价成功");
                    Intent intent = new Intent();
                  /*  if(isComment){
                        intent.setAction(Constants.RECEIVER_COMMENT_COMMENT_PAGE);
                    }else{
                        intent.setAction(Constants.RECEIVER_COMMENT_ALL_PAGE);
                    }*/
                    intent.setAction(Constants.RECEIVER_ORDER_DATA_CHANGE);
                    intent.putExtra("index", index);
                    intent.putExtra("type", MineOrderActivcity.COMMENT);
                    sendBroadcast(intent);
                    finish();
                }else{
                    Utils.makeToastAndShow(getBaseContext(), response.getContent());

                }
            }
        },this);
        request.setOrderId(orderItem.getId());
        request.setStarScore(String.valueOf(score));
        request.setContent("");
        request.setUserId(YbbApplication.getInstance().getMyUserInfo()
                .getUserId());
        request.setUserName(YbbApplication.getInstance().getMyUserInfo()
                .getNickName());
        request.setUserIp("");
//                model.setStarScore(dataLists.size()+1 +"");
//                model.setContent(dataLists.size()+1 +"");
        request.setLogisticsId(deliverymanId);//配餐员ID
        request.setCustomerServiceId("");//被评价客服ID
        request.setCommentInfoList(dataLists);
        WebUtils.doPost(request);
    }

}
