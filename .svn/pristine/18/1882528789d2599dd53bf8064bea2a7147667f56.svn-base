package com.bluemobi.ybb.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluemobi.ybb.R;
import com.bluemobi.ybb.activity.HomeActivity;
import com.bluemobi.ybb.adapter.CommonAdapter;
import com.bluemobi.ybb.adapter.ViewHolder;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.view.CustomSpinner;
import com.bluemobi.ybb.view.LoadingPage;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liufy on 2015/6/29.
 * P6营养餐-病患
 */
public class ShopCarGoodsFragment extends BaseFragment {


    private TextView tv_right;

    private RelativeLayout rightView;


    @Override
    public void initData(Bundle savedInstanceState)
    {
        isShowLoadPage = false;
    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {

        TitleBarManager titleBarManager = ((HomeActivity) getActivity()).getTitleBarManager();
        rightView = (RelativeLayout) titleBarManager.getTwoTabRightView();
        tv_right = (TextView) rightView.getChildAt(0);
        TextView textView = new TextView(mContext);
        textView.setText("敬请期待~~~");
        return  textView;

    }

    private boolean isChange = true;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
       /* if (isChange)
        {
            tv_right.setTextColor(getResources().getColor(R.color.common_blue));
            rightView.setBackgroundResource(R.drawable.two_tab_right);
            isChange = false;
        }else
        {
            tv_right.setTextColor(getResources().getColor(R.color.white));
            rightView.setBackgroundResource(R.color.trans);
            isChange = true;
        }*/

    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }




}
