package com.bluemobi.ybb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.db.entity.DateText;
import com.bluemobi.ybb.util.TimeFormat;

import java.util.List;

/**
 * Created by gaoyn on 2015/7/10.
 *
 * 物流跟踪  时间轴
 */
public class TimeLineAdapter extends BaseAdapter {

    private Context context;
    private List<DateText> list;

    public TimeLineAdapter(Context context, List<DateText> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        if (list == null) {
            return null;
        }
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.lv_order_tracking, parent, false);
            holder.date = (TextView) convertView
                    .findViewById(R.id.dataleft);
            holder.time = (TextView) convertView
                    .findViewById(R.id.timeright);
            holder.content = (TextView) convertView
                    .findViewById(R.id.state);
            holder.line = (ImageView) convertView.findViewById(R.id.tracking_vertical);
            holder.round = (ImageView) convertView.findViewById(R.id.round);

            holder.title = (RelativeLayout) convertView
                    .findViewById(R.id.rl_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //时间轴竖线的layout
        //LayoutParams params = (LayoutParams) holder.line.getLayoutParams();

        if(position == getCount()-1 || getCount()-1<1 ){

            holder.title.setVisibility(View.VISIBLE);
            holder.date.setText(TimeFormat.format("MM/dd",
                    list.get(position).getDate()));
            holder.line.setVisibility(View.GONE);

            //params.addRule(RelativeLayout.ALIGN_TOP, R.id.rl_title);
        }else
        //第一条数据，肯定显示时间标题
        if (position == 0) {
            holder.title.setVisibility(View.VISIBLE);
            holder.date.setText(TimeFormat.format("MM/dd",
                    list.get(position).getDate()));
            holder.date.setTextColor(context.getResources().getColor(R.color.common_blue));
            holder.time.setTextColor(context.getResources().getColor(R.color.common_blue));
            holder.content.setTextColor(context.getResources().getColor(R.color.common_blue));
            holder.round.setImageDrawable(context.getResources().getDrawable(R.drawable.order_tracking_blue));

            //params.addRule(RelativeLayout.ALIGN_TOP, R.id.rl_title);
        }
        else {
                //本条数据和上一条的数据的时间戳不同的时候，显示数据
                holder.title.setVisibility(View.VISIBLE);
                holder.date.setText(TimeFormat.format("MM/dd",
                        list.get(position).getDate()));
                //params.addRule(RelativeLayout.ALIGN_TOP, R.id.rl_title);

        }
        //holder.line.setLayoutParams(params);
        holder.time.setText(list.get(position).getTime());
        holder.content.setText(list.get(position).getText());
        return convertView;
    }

    public static class ViewHolder {
        RelativeLayout title;
        ImageView line;
        TextView date;
        TextView time;
        TextView content;
        ImageView round;
    }
}
