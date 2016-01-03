package com.bluemobi.ybb.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.db.entity.DisplayMessage;
import com.bluemobi.ybb.db.entity.Message;
import com.bluemobi.ybb.db.entity.MessageFoods;
import com.bluemobi.ybb.util.StringUtils;
import com.bluemobi.ybb.util.Utils;
import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;

/**
 * Created by gaoyn on 2015/7/2.
 *
 */
public class OrderExpandableAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<DisplayMessage>  listData;

    public OrderExpandableAdapter(Context context, List<DisplayMessage> d) {
        this._context = context;
        this.listData = d;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listData.get(groupPosition).getChildBean().get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        MessageFoods child= listData.get(groupPosition).getChildBean().get(childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandablelistview_child, null);
        }
        ImageView iv_image_bg=(ImageView)convertView.findViewById(R.id.iv_image_bg);
        TextView tv_search_item = (TextView) convertView.findViewById(R.id.tv_search_item);
        TextView tv_charge = (TextView) convertView.findViewById(R.id.tv_charge);
        TextView et_hint = (TextView) convertView.findViewById(R.id.et_hint);

        if(StringUtils.isNotEmpty(child.getImgPath())){
            String[] strings= child.getImgPath().split(",");
            Glide.with(_context).load(strings[0]).into(iv_image_bg);
        }
        tv_search_item.setText(child.getShopAndName());
        tv_charge.setText("¥"+child.getPrice());
        et_hint.setText("×"+child.getCount());

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listData.get(groupPosition).getChildBean().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listData.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listData.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        DisplayMessage partent= listData.get(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandablelistview_group, null);
        }
        TextView head = (TextView) convertView
                .findViewById(R.id.content_head);
        TextView time = (TextView) convertView
                .findViewById(R.id.time);
        TextView status = (TextView) convertView
                .findViewById(R.id.status);
        head.setText("订单号："+partent.getOrderNo());
        time.setText(partent.getCreateTime());
        status.setText(Utils.getStatusName(Integer.valueOf(partent.getOrderStatus())));
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
