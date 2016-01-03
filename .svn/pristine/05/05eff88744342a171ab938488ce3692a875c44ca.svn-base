package com.bluemobi.ybb.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluemobi.ybb.R;
import com.bluemobi.ybb.util.Utils;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomLongSpinnerBase extends RelativeLayout implements
        View.OnClickListener, OnItemClickListener {

    public View inflate;

    public PopupWindow pw;

    public Context mContext = null;

    /**
     * 下拉数据
     */
    public List<String> datas;

    /**
     * spinner 上显示的数据
     */
    public String spinnerText;

    /**
     * 显示城市的ID
     */
    public String spinnerID;

    private int leftMargin;

    public CustomLongSpinnerBase(Context context) {
        this(context, null);
    }

    public CustomLongSpinnerBase(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomLongSpinnerBase(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initViews(context, attrs, defStyle);
    }

    /**
     * 初始化 View
     *
     * @param context  上下文
     * @param attrs    属性
     * @param defStyle 默认
     */
    public abstract void initViews(Context context, AttributeSet attrs,
                                   int defStyle);

    /**
     * 下拉选择ListView 单击事件
     *
     * @param postion
     */
    public abstract void onLvItemClick(int postion, View view);

    /**
     * 显示下拉数据
     *
     * @param
     */
    public void showDataListDialog(List<String> datas, View view,int width,int leftMargin) {
        this.leftMargin = leftMargin;
        ListView lv = initListView();
        initPopWindow(datas, view, lv,width);

    }

    /**
     * 初始化下拉ListView
     *
     * @return
     */
    private ListView initListView() {
        ListView lv = new ListView(mContext);
        lv.setBackgroundResource(R.drawable.listview_background);
        lv.setVerticalScrollBarEnabled(false);
        lv.setDivider(null);
        lv.setDividerHeight(0);
        lv.setCacheColorHint(000000);
        lv.setOnItemClickListener(this);
        MySpinnerApapter mAdapter = new MySpinnerApapter(datas);
        lv.setAdapter(mAdapter);

        return lv;
    }


    /**
     * 初始化PopWindow
     *
     * @param datas 下拉列表显示数据
     * @param view
     * @param lv
     */
    public void initPopWindow(List<String> datas, View view, ListView lv, int viewWidth) {

        pw = new PopupWindow(lv, viewWidth, Utils.dip2px(mContext, 100));
        pw.setFocusable(true);
        pw.setOutsideTouchable(true);
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.showAsDropDown(view, 0, 0);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int postion,
                            long id) {
        onLvItemClick(postion, view);
        if (pw != null && pw.isShowing()) {
            pw.dismiss();
        }
    }


    /**
     * 获取下拉选择框显示数据
     *
     * @return
     */
    public String getSpinnerText() {
        return spinnerText != null ? spinnerText : "";
    }

    /**
     * 获取下拉选择框显示数据
     *
     * @return
     */
    public String getSpinnerID() {
        return spinnerID != null ? spinnerID : "";
    }

    public void setDatas(List<String> datas) {
        if(datas!=null&&datas.size()>0){
            spinnerText = datas.get(0);
        }
        this.datas = datas;
    }

    public List<String> getDatas() {
        return datas;
    }

    /**
     * 下拉选择框适配器
     *
     * @author liufy
     */
    public class MySpinnerApapter extends BaseAdapter {
        private List<String> datas = new ArrayList<String>();

        public MySpinnerApapter(List<String> datas) {
            this.datas = datas;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            View view = null;
            if (convertView == null) {
                view = View.inflate(mContext, R.layout.spinner_long_option_item,
                        null);
            } else {
                view = convertView;
            }
            TextView tv_number_item = (TextView) view
                    .findViewById(R.id.tv_number_item);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tv_number_item.getLayoutParams();
            params.leftMargin = leftMargin;
            tv_number_item.setLayoutParams(params);
            tv_number_item.setText(datas.get(position));

            return view;
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }

}
