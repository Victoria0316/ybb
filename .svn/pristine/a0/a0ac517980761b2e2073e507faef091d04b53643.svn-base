package com.bluemobi.ybb.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.adapter.CommonAdapter;
import com.bluemobi.ybb.adapter.ViewHolder;
import com.bluemobi.ybb.view.LoadingPage;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liufy on 2015/6/29.
 *
 */
public class MineOrderGoodsFragment extends BaseFragment {

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

    @Override
    public void initData(Bundle savedInstanceState)
    {
        isShowLoadPage = false;
    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {

        TextView textView = new TextView(mContext);
        textView.setText("敬请期待~");
        return  textView;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }


}
