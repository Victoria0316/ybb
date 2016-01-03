package com.bluemobi.ybb.ps.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.bluemobi.ybb.ps.R;
import com.bluemobi.ybb.ps.fragment.BaseFragment;
import com.bluemobi.ybb.ps.view.LoadingPage;


/**
 * Created by gaoyn on 2015/7/9.
 *
 * p24 商品
 */
public class DiliverymanGoodsFragment extends BaseFragment {


    @Override
    public void initData(Bundle savedInstanceState) {
       isShowLoadPage = false;
    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {

        View GoodsView = inflater.inflate(R.layout.fragment_goods,null);

        return GoodsView;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }
}
