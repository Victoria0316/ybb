package com.bluemobi.ybb.ps.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.Toast;


import com.bluemobi.base.utils.Utils;
import com.bluemobi.base.utils.ViewUtils;
import com.bluemobi.base.utils.WebUtils;
import com.bluemobi.ybb.ps.app.YbbPsApplication;
import com.bluemobi.ybb.ps.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.ps.util.Constants;
import com.bluemobi.ybb.ps.view.LoadingPage;
import com.bluemobi.ybb.ps.view.LoadingPage.LoadResult;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

public abstract class BaseFragment extends Fragment implements PullToRefreshListView.OnRefreshListener2 {

    public Context mContext = null;

    public View view = null;

    private LoadingPage loadingPage;

    public PullToRefreshListView plv_refresh = null;


    public boolean isShowLoadPage = true;

    private YbbHttpJsonRequest request;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);

        show();
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

    protected YbbHttpJsonRequest initRequest() {
        return null;
    }

    private void invokeRequest() {
        if(request == null){
            return;
        }else{
            request.setNetWorkResponseListener(loadingPage);
            pageTime = System.currentTimeMillis();
            YbbPsApplication.getInstance().setRequestTime(pageTime);
            YbbPsApplication.getInstance().setPageTime(pageTime);
            WebUtils.doPost(request);
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
        plv_refresh=pullToRefresh;
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
        if(loadingPage!=null){
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
    public static final int LOAD_MORE = 1;
    public static final int LOAD_REFRESH = 2;
    public static final int NUMBER_PER_PAGE = Constants.CURRENTNUMBASE;//每页条数
    protected int curPage = 0;//当前页
    protected long pageTime = 0;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public long getPageTime() {
        return pageTime;
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase pullToRefreshBase) {
        Log.e("tag", "onPullDownToRefresh");
        getPage(LOAD_REFRESH);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase pullToRefreshBase) {
        Log.e("tag","onPullUpToRefresh");
        getPage(LOAD_MORE);

    }

    public void plvonRefreshCompleted() {
        if(plv_refresh==null)
            return;

        plv_refresh.onRefreshComplete();

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
                    YbbPsApplication.getInstance().setPageTime(pageTime);
                    curPage = 0;
                    break;
            }
        } else {
            switch (type) {
                case LOAD_MORE: {

                    ListAdapter adapter = plv_refresh.getRefreshableView().getAdapter();
                    int i = adapter.getCount()-2;
                    Log.e("adapter.getCount()--->",adapter.getCount()-2+"");
                    if (i % NUMBER_PER_PAGE == 0) {
                        curPage = i/ NUMBER_PER_PAGE;
                    } else {
                        ret = false;
                        plvonRefreshCompleted();
                        Utils.makeToastAndShow(getActivity(), "已经没有更多记录", Toast.LENGTH_SHORT);
                    }
                }
                break;
                case LOAD_REFRESH:
                    pageTime = System.currentTimeMillis();
                    YbbPsApplication.getInstance().setPageTime(pageTime);
                    curPage = 0;
                    break;
            }
        }

        return ret;
    }
}


