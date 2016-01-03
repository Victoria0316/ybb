package com.bluemobi.ybb.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.activity.HomeActivity;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.network.model.FoodProductModel;
import com.bluemobi.ybb.util.StringUtils;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by wangzhijun on 2015/7/6.
 */
public class CommodityAdapter extends CommonAdapter<FoodProductModel> {

    private List<FoodProductModel> list;
    private HomeActivity m;

    public CommodityAdapter(HomeActivity home, List mDatas, int itemLayoutId) {
        super(home, mDatas, itemLayoutId);
        list = mDatas;
        m = home;
    }

    @Override
    public void convert(ViewHolder helper, final FoodProductModel item) {
        ImageView coll = helper.getView(R.id.isColl);
        TextView title = helper.getView(R.id.title);
        TextView evaluate_count = helper.getView(R.id.evaluate_count);
        TextView commodity_price = helper.getView(R.id.commodity_price);
        RatingBar commodity_score = helper.getView(R.id.commodity_score);
        ImageView item_img = helper.getView(R.id.item_img);
        title.setText(item.getShopName() + " " + item.getProductName());
        evaluate_count.setText("(" + item.getCommentCount() + "人评价)");
        if (item.getCommentStar() != null) {
            commodity_score.setRating(Float.parseFloat(item.getCommentStar()));
        } else {
            commodity_score.setRating(0);
        }
        if (StringUtils.isNotEmpty(item.getCustomerPrice())) {
            commodity_price.setText("￥" + item.getCustomerPrice());
        } else {
            commodity_price.setText("￥0");
        }
        if ("1".equals(item.getIsColl())) {//是否被当前用户收藏，0：未收藏，1：已收藏
            coll.setBackgroundResource(R.drawable.coll);
        } else {
            coll.setBackgroundResource(R.drawable.un_coll);
        }
        String imgPath = StringUtils.isEmpty(item.getImgStr())?"":item.getImgStr().split(",")[0];
        Glide.with(mContext)
                .load(imgPath)
                .placeholder(R.drawable.temp_item)
                .into(item_img);
        coll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(item.getIsColl())) {//是否被当前用户收藏，0：未收藏，1：已收藏
                    m.setDelCollectionRequest(item.getId(), "3", refresh, list.indexOf(item), -1);//收藏信息类型 1:店铺，2:商品，3：餐品，4：资讯

                } else {
                    m.setCollectionRequest(item.getId(), "3", refresh, list.indexOf(item), -1);
                }
            }
        });
    }

    BaseActivity.RefreshCollectListener refresh = new BaseActivity.RefreshCollectListener() {
        @Override
        public void refreshView(String flag, int indexP, int indexC) {
            list.get(indexP).setIsColl(flag);
            CommodityAdapter.this.notifyDataSetChanged();
        }
    };
}