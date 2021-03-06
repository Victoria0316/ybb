package com.bluemobi.ybb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.network.model.ProductInfoDTOs;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by gaozq on 2015/8/13.
 */
public class HorizontalAdapter extends BaseAdapter {

    protected List<ProductInfoDTOs> mListData;
    protected LayoutInflater inflater;
    private Context context;

    public HorizontalAdapter(Context c,List<ProductInfoDTOs> data) {
        mListData=data;
        inflater = LayoutInflater.from(c);
        context=c;
    }

    @Override
    public int getCount() {
        return mListData==null?0:mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mListData == null ? null : mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductInfoDTOs bean=mListData.get(position);
        HolderView holder = null;
        if (convertView == null) {
            holder = new HolderView();
            convertView = inflater.inflate(R.layout.commodity_detail_list_itme,
                    null);
            holder.pic=(ImageView)convertView.findViewById(R.id.pic);
            holder.title=(TextView)convertView.findViewById(R.id.title);
            holder.money=(TextView)convertView.findViewById(R.id.money);
            convertView.setTag(holder);
        } else {
            holder = (HolderView) convertView.getTag();
        }

        Logger.d("HorizontalAdapter", bean.getProductName());
        holder.title.setText(bean.getProductName());
        holder.money.setText("￥"+bean.getCustomerPrice());

        Glide.with(context)
                .load(bean.getImgList().get(0).toString()).placeholder(R.drawable.temp_item)
                .into(holder.pic);



        return convertView;
    }

    class HolderView{
        ImageView pic;
        TextView title,money;
    }
}
