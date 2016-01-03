package com.bluemobi.ybb.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bluemobi.ybb.R;
import com.bluemobi.ybb.activity.HomeActivity;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.network.model.CommentBatchChild;
import com.bluemobi.ybb.network.model.CommodityModel;
import com.bluemobi.ybb.util.StringUtils;
import com.bluemobi.ybb.view.CustomExpandableListView;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by wangzhijun on 2015/7/6.
 */
public class CommentBatchAdapter extends CommonAdapter<CommentBatchChild> {

    private List<CommentBatchChild> list;

    private Context mContext;
    public CommentBatchAdapter(Context context, List mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        list = mDatas;
        this.mContext = context;
    }

    @Override
    public void convert(ViewHolder helper, final CommentBatchChild item) {
        ImageView iv_image_bg = helper.getView(R.id.iv_image_bg);
        TextView tv_search_item = helper.getView(R.id.tv_search_item);
        RatingBar adapter_ratingBar = helper.getView(R.id.adapter_ratingBar);
        EditText content = helper.getView(R.id.content);
        Glide.with(mContext).load(item.getImg()).placeholder(R.drawable.temp_item).into(iv_image_bg);
        tv_search_item.setText(item.getProductName());
        adapter_ratingBar.setRating(Float.parseFloat(StringUtils.isEmpty(
                item.getStarScore())?"0":item.getStarScore()));
        content.setText(StringUtils.isEmpty(item.getContent())?"":
                item.getContent());
        /*adapter_ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                item.setStarScore(String.valueOf(rating));
            }
        });
        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setContent(StringUtils.isEmpty(s.toString())?"":
                s.toString());
            }
        });*/

    }

}