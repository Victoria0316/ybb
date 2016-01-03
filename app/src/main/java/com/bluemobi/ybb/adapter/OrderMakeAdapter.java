package com.bluemobi.ybb.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.network.model.Commodity;
import com.bluemobi.ybb.network.model.OrderMakeModel;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by wangzhijun on 2015/7/6.
 */
public class OrderMakeAdapter extends CommonAdapter<OrderMakeModel> implements SectionIndexer{
    private List<OrderMakeModel> lists ;
    public OrderMakeAdapter(Context context, List mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        lists = mDatas;
    }

    @Override
    public void convert(ViewHolder helper, OrderMakeModel item) {

    }

    @Override
    public void convert(ViewHolder helper, final OrderMakeModel item, int position) {
        RelativeLayout titleRl = helper.getView(R.id.title_rl);
        TextView split = helper.getView(R.id.split);
        TextView type = helper.getView(R.id.type);
        TextView type_ext = helper.getView(R.id.type_ext);
        TextView title = helper.getView(R.id.title);
        TextView price = helper.getView(R.id.price);
        TextView count = helper.getView(R.id.count);
        TextView pre_time = helper.getView(R.id.pre_time);
        ImageView img = helper.getView(R.id.img);
        type_ext.setVisibility(View.GONE);
        title.setText(item.getTitle());
        price.setText("￥"+item.getPrice());
        count.setText("X " + item.getCount());
        pre_time.setText(item.getReserveTime());
        Glide.with(mContext)
                .load(item.getImg())
                .placeholder(R.drawable.temp_item)
                .into(img);
                //根据position获取分类的首字母的char ascii值
        int section = getSectionForPosition(position);
//        Logger.e("wangzhijun", "section--> "+section +" getPositionForSection(section)==》 " + getPositionForSection(section) +
//                "position--> " + position);
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if(position == getPositionForSection(section)){
            titleRl.setVisibility(View.VISIBLE);
            type.setText(item.getCategory());
            split.setVisibility(View.VISIBLE);
        }else{
            titleRl.setVisibility(View.GONE);
            split.setVisibility(View.GONE);
        }
        if(position == 0){
            split.setVisibility(View.GONE);
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for(int i=0; i<getCount(); i++){
            if(sectionIndex == Integer.parseInt(lists.get(i).getCategoryId())){
                return  i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return Integer.parseInt(lists.get(position).getCategoryId());
    }
}