package com.bluemobi.ybb.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzhijun on 2015/7/9.
 */
public class ExtRecommendView extends LinearLayout{

    private int count = 3;
    private Context mContext;
    private LinearLayout mainLayout;

    private List<RelativeLayout> items = new ArrayList<RelativeLayout>();

    public ExtRecommendView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public ExtRecommendView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public ExtRecommendView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init(){
        this.setOrientation(LinearLayout.VERTICAL);
        createViews();
    }

    private void createViews() {
        TextView lineTop = new TextView(mContext);
        lineTop.setWidth(Utils.getDeviceWidth());
        lineTop.setHeight(Utils.dip2px(mContext, 1));
        lineTop.setBackgroundResource(R.color.line);
        this.addView(lineTop, 0);

        mainLayout = new LinearLayout(mContext);
        mainLayout.setOrientation(LinearLayout.HORIZONTAL);
        int temp = Utils.dip2px(mContext, 10);
        mainLayout.setPadding(temp, temp, temp, temp);
        this.addView(mainLayout, 1);

        int itemWidth = (Utils.getDeviceWidth((Activity)mContext)-2*temp-2*Utils.dip2px(mContext,15))/3;
        Logger.dl("temp",itemWidth + "");
        LinearLayout.LayoutParams linearParam = new LinearLayout.LayoutParams(
                itemWidth, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        linearParam.rightMargin = Utils.dip2px(mContext, 15);

        for(int i=0; i< count; i++){
            RelativeLayout rl = new RelativeLayout(mContext);
            ImageView imageView = new ImageView(mContext);
            imageView.setId(i*3 + 1);
            imageView.setBackgroundResource(R.drawable.temp_item);

            TextView title = new TextView(mContext);
            title.setId(i*3 + 2);
            title.setTextAppearance(mContext, R.style.text_13_sp);
            title.setTextColor(getResources().getColor(R.color.light_black));
            title.setMaxLines(2);
            title.setText("紫薯庄园");

            TextView price = new TextView(mContext);
            price.setId(i*3 + 3);
            price.setTextAppearance(mContext, R.style.text_13_sp);
            price.setTextColor(getResources().getColor(R.color.common_red));
            price.setText("￥50.00");

            RelativeLayout.LayoutParams imageParam = new RelativeLayout.LayoutParams(
                    itemWidth, itemWidth
            );
            rl.addView(imageView, imageParam);

            RelativeLayout.LayoutParams titleParam = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            titleParam.addRule(RelativeLayout.BELOW, imageView.getId());
            titleParam.topMargin = Utils.dip2px(mContext, 10);
            rl.addView(title, titleParam);

            RelativeLayout.LayoutParams priceParam = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            priceParam.addRule(RelativeLayout.BELOW, title.getId());
            rl.addView(price, priceParam);

            mainLayout.addView(rl, i, linearParam);
            rl.getLayoutParams().width = itemWidth;

        }

        TextView lineBottom = new TextView(mContext);
        lineBottom.setWidth(Utils.getDeviceWidth());
        lineBottom.setHeight(Utils.dip2px(mContext, 1));
        lineBottom.setBackgroundResource(R.color.line);
        this.addView(lineBottom, 2);
    }
}
