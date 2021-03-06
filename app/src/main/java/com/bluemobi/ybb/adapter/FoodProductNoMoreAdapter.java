package com.bluemobi.ybb.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.activity.CommodityDetailActivity;
import com.bluemobi.ybb.activity.LoginActivity;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.network.exception.CustomResponseError;
import com.bluemobi.ybb.network.exception.TokenInvalid;
import com.bluemobi.ybb.network.model.FoodProductModel;
import com.bluemobi.ybb.network.request.CollectionRequest;
import com.bluemobi.ybb.network.request.DelCollectionRequest;
import com.bluemobi.ybb.network.response.CollectionResponse;
import com.bluemobi.ybb.network.response.DelCollectionResponse;
import com.bluemobi.ybb.util.Constants;
import com.bluemobi.ybb.util.StringUtils;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.YbbAlertDialog;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by wangzhijun on 2015/7/6.
 */
public class FoodProductNoMoreAdapter extends CommonAdapter<FoodProductModel> {

    private List<FoodProductModel> list;
    private Context mContext;
    private int sumAddShopCar = 0;
    private TextView mCartAmount;
    private String categoryId;
    private String attributeId;

    public FoodProductNoMoreAdapter(Context context, List mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        list = mDatas;
        mContext = context;
    }

    public FoodProductNoMoreAdapter(Context context, List mDatas, int itemLayoutId
            , String categoryId, String attributeId) {
        super(context, mDatas, itemLayoutId);
        this.list = mDatas;
        this.mContext = context;
        this.categoryId = categoryId;
        this.attributeId = attributeId;
    }

    public FoodProductNoMoreAdapter(Context context, List mDatas, int itemLayoutId, TextView mCartAmount) {
        super(context, mDatas, itemLayoutId);
        list = mDatas;
        mContext = context;
        this.mCartAmount = mCartAmount;
    }

    @Override
    public void convert(ViewHolder helper, final FoodProductModel item) {
        helper.getView(R.id.iv_search_more).setVisibility(View.INVISIBLE);
        final ImageView coll = helper.getView(R.id.isColl);
        TextView title = helper.getView(R.id.tv_search_item);
        TextView evaluate_count = helper.getView(R.id.reviewCount);
        TextView commodity_price = helper.getView(R.id.tv_charge);
        RatingBar commodity_score = helper.getView(R.id.rb_comment);
        ImageView item_img = helper.getView(R.id.iv_image_bg);
        ImageView iv_add = helper.getView(R.id.iv_add);
        ImageView minus = helper.getView(R.id.minus);
        final EditText et_hint = helper.getView(R.id.et_hint);
        if ("1".equals(item.getIsColl())) {//是否被当前用户收藏，0：未收藏，1：已收藏
            coll.setBackgroundResource(R.drawable.coll);
        } else {
            coll.setBackgroundResource(R.drawable.un_coll);
        }
        coll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(item.getIsColl())) {//是否被当前用户收藏，0：未收藏，1：已收藏
                    doCancelColl(item);
                } else {
                    doColl(item);
                }
            }
        });

        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseActivity) mContext).setStartPoint(v);
                ((BaseActivity) mContext).setAnim();
                int num = Integer.parseInt(et_hint.getText().toString());
                ((BaseActivity) mContext).addShopCarRequest(num, item.getId(),"0",
                        Constants.FOODS_TYPE_OTHER,false,mCartAmount, item.getCustomerPrice(),
                        StringUtils.isEmpty(categoryId)?item.getCombogroup_categoryId():
                                categoryId, item.getAttributeId());
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 int num = Integer.parseInt(et_hint.getText().toString());
                 ((BaseActivity) mContext).addShopCarRequest(-num, item.getId(), "0",
                         Constants.FOODS_TYPE_OTHER, true, mCartAmount, item.getCustomerPrice(),
                         StringUtils.isEmpty(categoryId)?item.getCombogroup_categoryId():
                                 categoryId, item.getAttributeId());
             }
         }
        );



        title.setText(item.getShopName() + " " + item.getProductName());
        evaluate_count.setText("(" + item.getCommentCount() + "人评价)");
        if (item.getCommentStar() != null) {
            commodity_score.setRating(Float.parseFloat(item.getCommentStar()));
        } else {
            commodity_score.setRating(0);
        }
        if (StringUtils.isNotEmpty(item.getCustomerPrice())) {
//            commodity_price.setText("￥" + item.getComboPrice());
            commodity_price.setText("￥" + item.getCustomerPrice());
        } else {
            commodity_price.setText("￥0");
        }
        if (StringUtils.isNotEmpty(item.getImgPath())&& !"null".equals(item.getImgPath())){
            Glide.with(mContext).load(item.getImgPath()).placeholder(R.drawable.temp_item).into(item_img);
        } else {
            item_img.setImageResource(R.drawable.lv_item_image_bg);
        }
//        if ("1".equals(item.getIsColl())) {//是否被当前用户收藏，0：未收藏，1：已收藏
//            coll.setBackgroundResource(R.drawable.coll);
//        } else {
//            coll.setBackgroundResource(R.drawable.un_coll);
//        }
        helper.getView(R.id.main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("id", list.get(list.indexOf(item)).getId());
                intent.putExtra("imgPath", list.get(list.indexOf(item)).getImgPath());
                intent.putExtra("sumAddShopCar", sumAddShopCar);
                intent.putExtra("type", 1);
                if(StringUtils.isEmpty(categoryId)){
                    intent.putExtra("categoryId", item.getCombogroup_categoryId());
                }else{
                    intent.putExtra("categoryId", categoryId);
                }

                if(StringUtils.isEmpty(attributeId)){
                    intent.putExtra("attributeId", item.getAttributeId());
                }else{
                    intent.putExtra("attributeId", attributeId);
                }
                intent.setClass(mContext, CommodityDetailActivity.class);
                mContext.startActivity(intent);
            }
        });
//        coll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if ("1".equals(item.getIsColl())) {//是否被当前用户收藏，0：未收藏，1：已收藏
//                    m.setDelCollectionRequest(item.getId(), "3", refresh, list.indexOf(item), -1);//收藏信息类型 1:店铺，2:商品，3：餐品，4：资讯
//
//                } else {
//                    m.setCollectionRequest(item.getId(), "3", refresh, list.indexOf(item), -1);
//                }
//            }
//        });

    }

    /**
     * 收藏
     *
     * @param item
     */
    private void doColl(final FoodProductModel item) {
        CollectionRequest request = new CollectionRequest(new Response.Listener<CollectionResponse>() {
            @Override
            public void onResponse(CollectionResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    Utils.makeToastAndShow(mContext, "收藏成功");
                    item.setIsColl("1");
                    FoodProductNoMoreAdapter.this.notifyDataSetChanged();
                } else {// failed
                    Log.e("error", "error");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.closeDialog();
                if (error instanceof TimeoutError) {
                    Utils.makeToastAndShow(mContext, "网络异常,请检查您的网络", Toast.LENGTH_SHORT);
                } else if (error instanceof ParseError) {
                    Utils.makeToastAndShow(mContext, "解析出错", Toast.LENGTH_SHORT);
                } else if (error instanceof TokenInvalid) {
                    //TODO
                } else if (error instanceof CustomResponseError) {
                    CustomResponseError crs = (CustomResponseError) error;
                    if (crs.isToast()) {
                        Utils.makeToastAndShow(mContext, crs.getErrMsg(), Toast.LENGTH_SHORT);
                    }
                } else if(error instanceof NoConnectionError){//无网络连接
                    Utils.makeToastAndShow(mContext, "网络异常,请检查您的网络");
                }
                error.printStackTrace();
            }
        });
        request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setCollectionId(item.getId());
        request.setCollectionType("3");
        Utils.showProgressDialog(mContext);
        WebUtils.doPost(request);
    }

    /**
     * 取消收藏
     * @param item
     */
    private void doCancelColl(final FoodProductModel item) {
        DelCollectionRequest request = new DelCollectionRequest(new Response.Listener<DelCollectionResponse>() {
            @Override
            public void onResponse(DelCollectionResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    Utils.makeToastAndShow(mContext, "取消收藏");
                    item.setIsColl("0");
                    FoodProductNoMoreAdapter.this.notifyDataSetChanged();
                } else {// failed
                    Log.e("error", "error");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.closeDialog();
                if (error instanceof TimeoutError) {
                    Utils.makeToastAndShow(mContext, "网络异常,请检查您的网络", Toast.LENGTH_SHORT);
                } else if (error instanceof ParseError) {
                    Utils.makeToastAndShow(mContext, "解析出错", Toast.LENGTH_SHORT);
                } else if (error instanceof TokenInvalid) {
                    //TODO
                } else if (error instanceof CustomResponseError) {
                    CustomResponseError crs = (CustomResponseError) error;
                    if (crs.isToast()) {
                        Utils.makeToastAndShow(mContext, crs.getErrMsg(), Toast.LENGTH_SHORT);
                    }
                } else if(error instanceof NoConnectionError){//无网络连接
                    Utils.makeToastAndShow(mContext, "网络异常,请检查您的网络");
                }
                error.printStackTrace();
            }
        });
        request.setUserid(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setCollectionId(item.getId());
        request.setCollectionType("3");
        Utils.showProgressDialog(mContext);
        WebUtils.doPost(request);
    }

    BaseActivity.RefreshCollectListener refresh = new BaseActivity.RefreshCollectListener() {
        @Override
        public void refreshView(String flag, int indexP, int indexC) {
            list.get(indexP).setIsColl(flag);
            FoodProductNoMoreAdapter.this.notifyDataSetChanged();
        }
    };
}