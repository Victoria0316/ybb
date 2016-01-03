package com.bluemobi.ybb.ps.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluemobi.ybb.ps.R;


/**
 *
 * @author liufy
 *
 */
public class CustomSpinner extends CustomSpinnerBase
{

    /**
     *  输入项标签
     */
    public CharSequence lableText;

    /**
     * 标签控件
     */
    public TextView tv_label;
    /**
     * 下拉的选择按钮
     */
    private ImageView iv_arrow;

    private Drawable background;

    private CharSequence hintText;

    private LinearLayout ll_wrap_spinner;

    private int leftMargin;

    private ImageView iv_switch_line;

    public CustomSpinner(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public CustomSpinner(Context context)
    {
        this(context, null);
    }

    public CustomSpinner(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);

    }

    @Override
    public void initViews(Context context, AttributeSet attrs, int defStyle)
    {

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.SpinnerText, 0, defStyle);
        hintText = a.getText(R.styleable.SpinnerText_android_hint);
        lableText = a.getText(R.styleable.SpinnerText_android_text);
        background = a.getDrawable(R.styleable.SpinnerText_android_background);
        a.recycle();
        inflate = LayoutInflater.from(context).inflate(
                R.layout.spinner_text_item, this);
        tv_label = (TextView) findViewById(R.id.tv_label);
        iv_arrow = (ImageView) findViewById(R.id.iv_arrow);
        iv_switch_line = (ImageView) findViewById(R.id.iv_switch_line);
        ll_wrap_spinner = (LinearLayout) findViewById(R.id.ll_wrap_spinner);

        if(lableText!=null)
        {
            tv_label.setText(lableText);
        }
        this.setOnClickListener(this);

    }

    /**
     * 添加下划线
     */
    public void setSelectLine()
   {
       iv_switch_line.setImageResource(R.drawable.search_bar_tab_line);
   }
    /**
     * 清除下划线
     */
    public void clearSelectLine()
    {
        iv_switch_line.setImageResource(R.color.transparent);
    }


    @Override
    public void onClick(View v)
    {

        if (mListener!=null)
            mListener.OnSwitchTabClick();
        leftMargin = ll_wrap_spinner.getWidth() -(tv_label.getWidth()+iv_arrow.getWidth()-iv_arrow.getPaddingLeft()-iv_arrow.getPaddingRight()-tv_label.getPaddingLeft()-tv_label.getPaddingRight())-ll_wrap_spinner.getPaddingLeft()-ll_wrap_spinner.getPaddingRight();
        showDataListDialog(datas, ll_wrap_spinner,getWidth(),leftMargin/2);


    }

    @Override
    public void onLvItemClick(int postion, View view)
    {
        spinnerText = datas.get(postion);
        tv_label.setText(spinnerText);
        if (mSpinnerListener!=null)
        {
            mSpinnerListener.OnSnipperClick(postion);
        }

    }

    public CharSequence getLableText() {
        return lableText;
    }

    public TextView getTv_label() {
        return tv_label;
    }

    public void setSpinnerText()
    {
        if (datas!=null&&datas.size()>0)
            tv_label.setText(datas.get(0));
    }


    public OnSwitchTabClickListener mListener;

    public interface  OnSwitchTabClickListener
    {
        public void OnSwitchTabClick();
    }

    public void setOnSwitchTabClickListener(
            OnSwitchTabClickListener listener)
    {
        mListener = listener;
    }

    public OnSnipperClickListener mSpinnerListener;

    public interface  OnSnipperClickListener
    {
        public void OnSnipperClick(int position);
    }

    public void setSnipperClickListener(
            OnSnipperClickListener listener)
    {
        mSpinnerListener = listener;
    }
}
