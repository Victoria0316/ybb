package com.bluemobi.ybb.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.activity.CommodityDetailActivity;
import com.bluemobi.ybb.activity.HomeActivity;
import com.bluemobi.ybb.activity.RestaurantBookingActivity;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.fragment.BaseFragment;
import com.bluemobi.ybb.network.model.CommodityModel;
import com.bluemobi.ybb.network.response.CommodtiesResponse;
import com.bluemobi.ybb.util.Constants;
import com.bluemobi.ybb.util.PreferencesService;
import com.bluemobi.ybb.util.StringUtils;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by wangzhijun on 2015/7/6.
 */
public class CommodityNoMoreAdapter extends CommonAdapter<CommodityModel> {

    private List<CommodityModel> list;
    private Context mContext;
    private int sumAddShopCar = 0;
    private TextView mCartAmount;
    private String categoryId;
    private String attributeId;


    public CommodityNoMoreAdapter(Context context, List mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        list = mDatas;
        mContext = context;
    }
    public CommodityNoMoreAdapter(Context context, List mDatas, int itemLayoutId,  TextView mCartAmount) {
        super(context, mDatas, itemLayoutId);
        list = mDatas;
        mContext = context;
        this.mCartAmount = mCartAmount;
    }




    @Override
    public void convert(ViewHolder helper, final CommodityModel item) {
        helper.getView(R.id.iv_search_more).setVisibility(View.INVISIBLE);
//        ImageView coll = helper.getView(R.id.isColl);
        TextView title = helper.getView(R.id.tv_search_item);
        TextView evaluate_count = helper.getView(R.id.reviewCount);
        TextView commodity_price = helper.getView(R.id.tv_charge);
        RatingBar commodity_score = helper.getView(R.id.rb_comment);
        ImageView item_img = helper.getView(R.id.iv_image_bg);
        ImageView iv_add = helper.getView(R.id.iv_add);
        ImageView minus = helper.getView(R.id.minus);
        final EditText et_hint = helper.getView(R.id.et_hint);
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseActivity) mContext).setStartPoint(v);
                ((BaseActivity) mContext).setAnim();
                int num = Integer.parseInt(et_hint.getText().toString());
                ((BaseActivity) mContext).addShopCarRequest(num, item.getId(),"0",
                        Constants.FOODS_TYPE_OTHER,false,mCartAmount, item.getCustomer_price());
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 int num = Integer.parseInt(et_hint.getText().toString());
                 ((BaseActivity) mContext).addShopCarRequest(-num, item.getId(), "0",
                         Constants.FOODS_TYPE_OTHER, true, mCartAmount, item.getCustomer_price());
             }
         }
        );



        title.setText(item.getShopName() + " " + item.getComboName());
        evaluate_count.setText("(" + item.getCommentCount() + "人评价)");
        if (item.getCommentStar() != null) {
            commodity_score.setRating(Float.parseFloat(item.getCommentStar()));
        } else {
            commodity_score.setRating(0);
        }
        if (StringUtils.isNotEmpty(item.getCustomer_price())) {
//            commodity_price.setText("￥" + item.getComboPrice());
            commodity_price.setText("￥" + item.getCustomer_price());
        } else {
            commodity_price.setText("￥0");
        }
        if (StringUtils.isNotEmpty(item.getImgPath())&& !"null".equals(item.getImgPath())){
            Glide.with(mContext).load(item.getImgPath()).into(item_img);
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
                intent.putExtra("attributeId", 1);
                intent.putExtra("categoryId", 1);
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

    BaseActivity.RefreshCollectListener refresh = new BaseActivity.RefreshCollectListener() {
        @Override
        public void refreshView(String flag, int indexP, int indexC) {
            list.get(indexP).setIsColl(flag);
            CommodityNoMoreAdapter.this.notifyDataSetChanged();
        }
    };
}