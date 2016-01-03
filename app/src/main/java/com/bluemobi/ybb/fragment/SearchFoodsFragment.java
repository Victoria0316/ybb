package com.bluemobi.ybb.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.activity.CommodityDetailActivity;
import com.bluemobi.ybb.activity.HomeSearchActivity;
import com.bluemobi.ybb.adapter.FoodProductNoMoreAdapter;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.network.model.FoodProductModel;
import com.bluemobi.ybb.network.request.FoodProductListRequest;
import com.bluemobi.ybb.network.response.FoodProductListResponse;
import com.bluemobi.ybb.util.Constants;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.LoadingPage;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liufy on 2015/6/29.
 * P5-1搜索-餐品
 */
public class SearchFoodsFragment extends BaseFragment {

    private FoodProductNoMoreAdapter adapter;

    private List<FoodProductModel> dataList = new ArrayList<FoodProductModel>();
    private HomeSearchActivity mHomeSearchActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mHomeSearchActivity = (HomeSearchActivity)activity;
    }
    @Override
    public void initData(Bundle savedInstanceState)
    {
        isShowLoadPage = false;
    }

    @Override
    public View createSuccessView(LayoutInflater inflater)
    {

        View homeView = inflater.inflate(R.layout.fragment_search_foods,null);
        plv_refresh = (PullToRefreshListView) homeView.findViewById(R.id.plv_refresh);
        initPullToRefresh(plv_refresh);
        plv_refresh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("id", dataList.get(position-1).getId());
                intent.putExtra("imgPath", dataList.get(position-1).getImgPath());
                intent.putExtra("categoryId", dataList.get(position-1).getCombogroup_categoryId());
                intent.putExtra("attributeId", dataList.get(position-1).getAttributeId());

                intent.setClass(getActivity(), CommodityDetailActivity.class);
                getActivity().startActivity(intent);
            }
        });
        adapter = new FoodProductNoMoreAdapter(getActivity(),
                dataList, R.layout.lv__main_search_item);
        plv_refresh.setAdapter(adapter);
        return homeView;
    }

    @Override
    public boolean getPage(int type) {
        if(!super.getPage(type)){
            return false;
        }
        connectToServer();
        return true;

    }

    private void connectToServer()
    {
        FoodProductListRequest request = new FoodProductListRequest(new Response.Listener<FoodProductListResponse>() {
            @Override
            public void onResponse(FoodProductListResponse response) {
                Utils.closeDialog();
                plv_refresh.onRefreshComplete();
                if (response != null && response.getStatus() == 0) {
                    if (response.getData().getInfo() != null) {
                        if (response.getData().getCurrentpage().equals("1")) {
                            dataList.clear();
                        }
                        dataList.addAll(response.getData().getInfo());
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    dataList.clear();
                    adapter.notifyDataSetChanged();
                    Utils.closeDialog();
                    plv_refresh.onRefreshComplete();
                    Toast.makeText(mContext, response.getContent(), Toast.LENGTH_SHORT).show();
                }

            }
        }
                ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                plv_refresh.onRefreshComplete();
                Utils.closeDialog();

            }
        });

        if(YbbApplication.role_bh.equals(YbbApplication.getInstance().getMyUserInfo().getTypeId())){
            request.setCategoryIdList("1,2,4");
        }else if(YbbApplication.role_yh.equals(YbbApplication.getInstance().getMyUserInfo().getTypeId())){
            request.setCategoryIdList("2,3");
        }
        request.setHandleCustomErr(false);
        request.setKeyword(mHomeSearchActivity.et_search.getText().toString());
        request.setCurrentnum(Constants.CURRENTNUMBASE + "");
        request.setPageTime("");
        request.setCurrentpage(getCurPage() + "");
        request.setLoginuserid(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setCanteenId(YbbApplication.getInstance().getCanteenId());
        Utils.showProgressDialog(mContext);
        WebUtils.doPost(request);

    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        InputMethodManager imm =  (InputMethodManager)getActivity().getSystemService(
                Activity.INPUT_METHOD_SERVICE);
        if(imm != null) {

            imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(),
                    0);
        }
    }
}
