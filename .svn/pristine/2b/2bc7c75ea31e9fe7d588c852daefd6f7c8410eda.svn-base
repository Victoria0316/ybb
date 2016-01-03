package com.bluemobi.ybb.fragment.page;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bluemobi.ybb.R;
import com.bluemobi.ybb.adapter.CommonAdapter;
import com.bluemobi.ybb.adapter.ViewHolder;
import com.bluemobi.ybb.fragment.BaseFragment;
import com.bluemobi.ybb.view.LoadingPage;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * liufengyu
 * @author Administrator
 * P13购物车-餐品
 */
public  class MealsPage extends BasePage implements View.OnClickListener
{
    private CommonAdapter<String> adapter;

    private List<String> dataList ;

    private boolean isShowEdit = false;

    private ImageView iv_shop_select;

    private boolean isClick =false;
    /**
     *
     *  单个item是否可以点击
     */
    private boolean isSingleClick =false;

    public MealsPage(Context context,BaseFragment baseFragment) {
        super(context, baseFragment);
    }

    @Override
    public View initView(LayoutInflater inflater) {

        View addShopcarView = inflater.inflate(R.layout.activity_shopcar,null);
        dataList = new ArrayList<String>();
        plv_refresh = (PullToRefreshListView) addShopcarView.findViewById(R.id.plv_refresh);
        iv_shop_select = (ImageView)addShopcarView.findViewById(R.id.iv_shop_select);
        iv_shop_select.setOnClickListener(this);
        initPullToRefresh(plv_refresh);
        for (int i = 0;i<4;i++)
        {
            dataList.add("ddd"+i);
        }
        plv_refresh.setAdapter(
                adapter = new CommonAdapter<String>(pageContext,
                        dataList, R.layout.lv__shop_item) {

                    @Override
                    public void convert(ViewHolder helper, String bean) {
                        TextView tv_num = (TextView) helper.getView(R.id.tv_num);
                        Button btn_del = (Button) helper.getView(R.id.btn_del);
                        ImageView iv_minus = (ImageView) helper.getView(R.id.iv_minus);
                        EditText et_hint = (EditText) helper.getView(R.id.et_hint);
                        ImageView iv_add = (ImageView) helper.getView(R.id.iv_add);
                        final ImageView iv_select = (ImageView) helper.getView(R.id.iv_select);
                        iv_select.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                isSingleClick = !isSingleClick;
                                if (isSingleClick) {
                                    iv_select.setImageResource(R.drawable.shop_select);
                                } else {
                                    iv_select.setImageResource(R.drawable.shop_unselect);
                                }
                            }
                        });

                        if (isShowEdit) {
                            iv_minus.setVisibility(View.VISIBLE);
                            et_hint.setVisibility(View.VISIBLE);
                            iv_add.setVisibility(View.VISIBLE);
                            btn_del.setVisibility(View.VISIBLE);
                        } else {
                            iv_minus.setVisibility(View.GONE);
                            et_hint.setVisibility(View.GONE);
                            iv_add.setVisibility(View.GONE);
                            btn_del.setVisibility(View.GONE);
                        }

                        if (isClick) {
                            iv_select.setImageResource(R.drawable.shop_select);
                        } else {
                            iv_select.setImageResource(R.drawable.shop_unselect);
                        }


                    }
                });
        return addShopcarView;
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id. iv_shop_select:
                isClick = !isClick;
                if(isClick)
                {
                    iv_shop_select.setImageResource(R.drawable.shop_select);
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    iv_shop_select.setImageResource(R.drawable.shop_unselect);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }



    @Override
    public void initData() {

    }

    @Override
    protected LoadingPage.LoadResult load() {
        return null;
    }
}
