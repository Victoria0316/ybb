package com.bluemobi.ybb.fragment.page;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.fragment.BaseFragment;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.util.Constants;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.ViewUtils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.LoadingPage;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * liufengyu
 * @author Administrator
 *
 */
public abstract class BasePage implements LoadListener,PullToRefreshListView.OnRefreshListener2
{
    public Context pageContext = null;

    public View view = null;
    protected boolean hasLoaded;

    public BaseFragment baseFragment;

    protected LoadingPage loadingPage;

    public boolean isShowLoadPage = true;

    public LayoutInflater inflater;

    public PullToRefreshListView plv_refresh = null;
    public static final int LOAD_MORE = 1;
    public static final int LOAD_REFRESH = 2;
    public int NUMBER_PER_PAGE = Constants.CURRENTNUMBASE;// 每页条数
    protected int curPage = -1;// 当前页
    protected long pageTime = 0;

    protected int currentPage=0;
    private boolean needReload;

    public BasePage(Context context,BaseFragment baseFragment)
    {
        this.pageContext = context;
        this.baseFragment = baseFragment;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initData();
        loadingPage = (LoadingPage) initBaseView(inflater);
        pageTime = System.currentTimeMillis();
    }


    protected  View initRootView()
    {
        return null;
    }

    public void show(){
        if(loadingPage!=null) {
            loadingPage.show(isShowLoadPage);
        }
    }

    protected View initBaseView( final LayoutInflater inflater)
    {
        if (loadingPage == null) {
            loadingPage = new LoadingPage(pageContext){


                @Override
                public View createSuccessView() {
                    return initView(inflater);
                }

                @Override
                protected LoadResult load() {
                    return load();
                }
            };
        }else{
            ViewUtils.removeParent(loadingPage);
        }

        return loadingPage;
    }

    public void initPullToRefresh(PullToRefreshListView pullToRefresh)
    {
        this.plv_refresh = pullToRefresh;
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

    public View getRootView()
    {
        return loadingPage;
    }

    public abstract View initView(LayoutInflater inflater);

    public abstract void initData();

    protected abstract LoadingPage.LoadResult load();

    @Override
    public void loaded() {
        show();
        if(isNeedReload()){
            getPage(LOAD_REFRESH);
        }
    }
    @Override
    public void onPullDownToRefresh(PullToRefreshBase pullToRefreshBase)
    {
        Log.e("111111111111", "onPullDownToRefresh");
        getPage(LOAD_REFRESH);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase pullToRefreshBase) {
        Log.e("222222222222222","onPullUpToRefresh");
        getPage(LOAD_MORE);
    }


    protected boolean getPage(int type) {
        boolean ret = true;

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
                            Utils.makeToastAndShow(pageContext, "已经没有更多记录", Toast.LENGTH_SHORT);
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

    public void reload(){
        getPage(LOAD_REFRESH);
    }

    public boolean isNeedReload() {
        return needReload;
    }

    public void setNeedReload(boolean needReload) {
        this.needReload = needReload;
    }

}
