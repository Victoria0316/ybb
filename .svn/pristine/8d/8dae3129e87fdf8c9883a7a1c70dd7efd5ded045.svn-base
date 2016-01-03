package com.bluemobi.ybb.adapter;

import android.content.Context;
import android.view.View;

import com.bluemobi.ybb.R;
import com.bluemobi.ybb.network.model.Commodity;
import com.bluemobi.ybb.network.model.CommodityModel;

import java.util.List;

/**
 * Created by wangzhijun on 2015/8/11.
 */
public class CommodityListAdapter extends CommonAdapter<CommodityModel>{

    public CommodityListAdapter(Context context, List<CommodityModel> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, CommodityModel item) {
        helper.getView(R.id.iv_search_more).setVisibility(View.INVISIBLE);
    }
}
