package com.bluemobi.ybb.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.request.AddToShopCartRequest;
import com.bluemobi.ybb.network.request.CollectionRequest;
import com.bluemobi.ybb.network.request.DelCollectionRequest;
import com.bluemobi.ybb.network.response.CollectionResponse;
import com.bluemobi.ybb.network.response.CommonResponse;
import com.bluemobi.ybb.network.response.DelCollectionResponse;
import com.bluemobi.ybb.util.Constants;
import com.bluemobi.ybb.util.PreferencesService;
import com.bluemobi.ybb.util.StringUtils;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.ViewUtils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.LoadingPage;
import com.bluemobi.ybb.view.LoadingPage.LoadResult;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public abstract class BaseFragment extends Fragment implements PullToRefreshListView.OnRefreshListener2{

    public Context mContext = null;
    public View view = null;
    private LoadingPage loadingPage;
    public PullToRefreshListView plv_refresh = null;
    public boolean isShowLoadPage = true;
    public static final int LOAD_MORE = 1;
    public static final int LOAD_REFRESH = 2;
    public static final int NUMBER_PER_PAGE = Constants.CURRENTNUMBASE;//每页条数
    protected int curPage = -1;//当前页
    protected long pageTime = 0;
    private YbbHttpJsonRequest request;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
        show();
    }

    protected YbbHttpJsonRequest initRequest() {
        return null;
    }

    private void invokeRequest() {
        if(request == null){
            return;
        }else{
            request.setNetWorkResponseListener(loadingPage);
            pageTime = System.currentTimeMillis();
            YbbApplication.getInstance().setRequestTime(pageTime);
            YbbApplication.getInstance().setPageTime(pageTime);
            WebUtils.doPost(request);
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        request = initRequest();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        initView(inflater);
        invokeRequest();
    }
    /**
     * 初始化loadingpage
     */
    protected void initView( final LayoutInflater inflater)
    {
        if (loadingPage == null)
        {
            loadingPage = new LoadingPage(mContext)
            {
                @Override
                public View createSuccessView()
                {
                    return BaseFragment.this.createSuccessView(inflater);
                }
                @Override
                protected LoadResult load()
                {
                    return BaseFragment.this.load();
                }
            };
        }
        else
        {
            ViewUtils.removeParent(loadingPage);
        }
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (loadingPage == null) {
            loadingPage = new LoadingPage(getActivity()){

                @Override
                public View createSuccessView() {
                    return BaseFragment.this.createSuccessView(inflater);
                }

                @Override
                protected LoadResult load() {
                    return BaseFragment.this.load();
                }
            };
        }else{
            ViewUtils.removeParent(loadingPage);
        }

        return loadingPage;
    }

    public void initPullToRefresh(PullToRefreshListView pullToRefresh)
    {
        pullToRefresh.setOnRefreshListener(this);
        this.plv_refresh = pullToRefresh;
        pullToRefresh.setMode(PullToRefreshBase.Mode.BOTH);
        ILoadingLayout startLabels = pullToRefresh
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");
        startLabels.setRefreshingLabel("正在载入...");
        startLabels.setReleaseLabel("放开刷新...");
        ILoadingLayout endLabels = pullToRefresh.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("上拉刷新...");
        endLabels.setRefreshingLabel("正在载入...");
        endLabels.setReleaseLabel("放开刷新...");
    }

    public void initExpandPullToRefresh(PullToRefreshExpandableListView pullToRefresh)
    {
        pullToRefresh.setOnRefreshListener(this);
        pullToRefresh.setMode(PullToRefreshBase.Mode.BOTH);
        ILoadingLayout startLabels = pullToRefresh
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");
        startLabels.setRefreshingLabel("正在载入...");
        startLabels.setReleaseLabel("放开刷新...");
        ILoadingLayout endLabels = pullToRefresh.getLoadingLayoutProxy(
                false, true);
        endLabels.setPullLabel("上拉刷新...");
        endLabels.setRefreshingLabel("正在载入...");
        endLabels.setReleaseLabel("放开刷新...");
    }

    /**
     * 初始化数据
     * @param savedInstanceState 保存状态数据使用
     */
    public abstract void initData(Bundle savedInstanceState);

    /***
     *  创建成功的界面
     * @return
     */
    public  abstract View createSuccessView(LayoutInflater inflater);
    /**
     * 请求服务器
     * @return
     */
    protected abstract LoadResult load();

    public void show(){
        if(loadingPage!=null) {
            loadingPage.show(isShowLoadPage);
        }
    }






    /**校验数据 */
    public LoadResult checkData(List datas) {
        if(datas==null){
            return LoadResult.error;//  请求服务器失败
        }else{
            if(datas.size()==0){
                return LoadResult.empty;
            }else{
                return LoadResult.success;
            }
        }

    }





    @Override
    public void onPullDownToRefresh(PullToRefreshBase pullToRefreshBase) {
        Log.e("tag","onPullDownToRefresh");
        getPage(LOAD_REFRESH);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase pullToRefreshBase) {
        Log.e("tag","onPullUpToRefresh");
        getPage(LOAD_MORE);

    }



    protected boolean getPage(int type) {
        boolean ret = true;
        if (plv_refresh == null) {
            switch (type) {
                case LOAD_MORE:
                    curPage++;
                    break;
                case LOAD_REFRESH:
                    pageTime = System.currentTimeMillis();
                    YbbApplication.getInstance().setPageTime(pageTime);
                    curPage = 0;
                    break;
            }
        } else {
            switch (type) {
                case LOAD_MORE: {
                    int count = plv_refresh.getRefreshableView().getAdapter().getCount();
                    int i = count-2;
                    if (i % NUMBER_PER_PAGE == 0) {
                        curPage = i/ NUMBER_PER_PAGE;
                    } else {
                        ret = false;
                        plv_refresh.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                plv_refresh.onRefreshComplete();
                                Utils.makeToastAndShow(mContext, "已经没有更多记录", Toast.LENGTH_SHORT);
                            }
                        }, 2000);
                    }
                }
                break;
                case LOAD_REFRESH:
                    pageTime = System.currentTimeMillis();
                    YbbApplication.getInstance().setPageTime(pageTime);
                    curPage = 0;
                    break;
            }
        }

        return ret;
    }



    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public long getPageTime() {
        return pageTime;
    }


    public interface RefreshCollectListener {

        public void refreshView(String flag,int  indexP,int indexC);
    }
    /**
     * 收藏
     */

    public void setCollectionRequest(String id ,String CollectionType,final RefreshCollectListener listener,final int p,final int c){
        CollectionRequest request = new CollectionRequest(new Response.Listener<CollectionResponse>() {
            @Override
            public void onResponse(CollectionResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    Utils.makeToastAndShow(mContext, "收藏成功");
//                    isColl.setBackgroundResource(R.drawable.coll);
                    listener.refreshView("1",p,c);
                } else {// failed
                    Log.e("error", "error");
                }
            }
        },(Response.ErrorListener) getActivity());
        request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setCollectionId(id);
        request.setCollectionType(CollectionType);
        Utils.showProgressDialog(mContext);
        WebUtils.doPost(request);
    }

    /**
     * 取消收藏
     */
    public void setDelCollectionRequest(String id ,String CollectionType,final RefreshCollectListener listener,final int p,final int c){

        DelCollectionRequest request = new DelCollectionRequest(new Response.Listener<DelCollectionResponse>() {
            @Override
            public void onResponse(DelCollectionResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    Utils.makeToastAndShow(mContext, "取消收藏");
                    listener.refreshView("0",p,c);
                } else {// failed
                    Log.e("error", "error");
                }
            }
        },(Response.ErrorListener) getActivity());
        request.setUserid(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setCollectionId(id);
        request.setCollectionType(CollectionType);
        Utils.showProgressDialog(mContext);
        WebUtils.doPost(request);
    }



    /**
     * 添加购物车接口
     *  @param buyNumPar 数量
     * @param productID 商品ID
     * @param tv_shopcar_total
     * @param customer_price
     */
    public void addShopCarRequest(final int buyNumPar, String productID, String reserveTime,
                                  int type, final boolean isMius, final TextView tv_shopcar_total,
                                  final String customer_price, final BaseActivity baseActivity,
                                  String categoryId, String attributeId) {

        Log.e("buyNumPar",buyNumPar+"");
        AddToShopCartRequest request = new AddToShopCartRequest
                (
                        new Response.Listener<CommonResponse>() {
                            @Override
                            public void onResponse(CommonResponse response) {
                                String totalString = YbbApplication.getInstance().getCartAmount();
                                BigDecimal current = new BigDecimal(StringUtils.isEmpty(totalString) ? "0" :
                                        totalString);
                                BigDecimal target = new BigDecimal(StringUtils.isEmpty(customer_price) ? "0" :
                                        customer_price);
                                int sumAddShopCar = PreferencesService.getInstance(mContext).getPreferencesInt("cartCounts");
                                if (isMius)
                                {
                                    BigDecimal total = current.subtract(target);
                                    total = total.setScale(2, BigDecimal.ROUND_UP);
                                    YbbApplication.getInstance().setCartAmount(String.valueOf(total.floatValue()));
                                    Utils.makeToastAndShow(mContext, "成功从购物车移除");
                                    sumAddShopCar = sumAddShopCar + buyNumPar;

                                }
                                else
                                {
                                    BigDecimal total = current.add(target);
                                    total = total.setScale(2, BigDecimal.ROUND_UP);
                                    YbbApplication.getInstance().setCartAmount(String.valueOf(total.floatValue()));
                                    Utils.makeToastAndShow(mContext, "加入购物车成功");
                                    sumAddShopCar = sumAddShopCar + buyNumPar;

                                }
                                baseActivity.setShopCarNum(sumAddShopCar, true);
                                YbbApplication.getInstance().setCartCounts(sumAddShopCar);
                                tv_shopcar_total.setText("￥"+YbbApplication.getInstance().getCartAmount());

                            }
                        }, (Response.ErrorListener) getActivity());
        request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setProductId(productID);
        request.setProductNum(buyNumPar + "");
        request.setCategoryId(categoryId);
        request.setAttributeId(attributeId);
        if (type==Constants.FOODS_TYPE)
        {
            request.setReserveTime(reserveTime);
        }
        else
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            request.setReserveTime(format.format(new Date()));
        }
        WebUtils.doPost(request);

    }




}


