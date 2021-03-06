package com.bluemobi.ybb.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.network.model.CommentBatchChild;
import com.bluemobi.ybb.util.StringUtils;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by wangzhijun on 2015/7/6.
 */
public class CommentBatchAdapter1 extends BaseAdapter {

    private List<CommentBatchChild> list;

    private Context mContext;

    public CommentBatchAdapter1(Context context, List mDatas) {
        list = mDatas;
        this.mContext = context;
    }


    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CommentBatchChild item = list.get(position);
        convertView = LayoutInflater.from(mContext).inflate(
                R.layout.lv_comment_item, parent, false
        );
        ImageView iv_image_bg = (ImageView) convertView.findViewById(R.id.iv_image_bg);
        TextView tv_search_item = (TextView) convertView.findViewById(R.id.tv_search_item);
        RatingBar adapter_ratingBar = (RatingBar) convertView.findViewById(R.id.adapter_ratingBar);
        EditText content = (EditText) convertView.findViewById(R.id.content);
        Glide.with(mContext).load(item.getImg()).placeholder(R.drawable.temp_item).into(iv_image_bg);
        tv_search_item.setText(item.getProductName());
        adapter_ratingBar.setRating(Float.parseFloat(StringUtils.isEmpty(
                item.getStarScore()) ? "0" : item.getStarScore()));
        content.setText(StringUtils.isEmpty(item.getContent()) ? "" :
                item.getContent());
        item.setContent(content.getText().toString());
        adapter_ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
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
                item.setContent(StringUtils.isEmpty(s.toString())?"":s.toString());
            }
        });
        return  convertView;
    }

}