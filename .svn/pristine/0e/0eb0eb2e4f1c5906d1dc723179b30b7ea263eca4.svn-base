package com.bluemobi.ybb.ps.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluemobi.base.utils.StringUtils;
import com.bluemobi.base.utils.Utils;
import com.bluemobi.ybb.ps.R;
import com.bluemobi.ybb.ps.db.entity.PSDisplayMessage;
import com.bluemobi.ybb.ps.db.entity.PSMessageFoods;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by gaoyn on 2015/7/2.
 *
 */
public class PSOrderExpandableAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<PSDisplayMessage>  listData;

    public PSOrderExpandableAdapter(Context context, List<PSDisplayMessage> d) {
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
        PSMessageFoods child= listData.get(groupPosition).getChildBean().get(childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.psexpandablelistview_child, null);
        }
        ImageView iv_image_bg=(ImageView)convertView.findViewById(R.id.iv_image_bg);
        TextView tv_search_item = (TextView) convertView.findViewById(R.id.tv_search_item);
        TextView tv_charge = (TextView) convertView.findViewById(R.id.tv_charge);
        TextView et_hint = (TextView) convertView.findViewById(R.id.et_hint);

        if(StringUtils.isNotEmpty(child.getImgPath())){
            String[] strings= child.getImgPath().split(",");
            Glide.with(_context).load(strings[0]).placeholder(R.drawable.temp_item).into(iv_image_bg);
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
        PSDisplayMessage partent= listData.get(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.psexpandablelistview_group, null);
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
