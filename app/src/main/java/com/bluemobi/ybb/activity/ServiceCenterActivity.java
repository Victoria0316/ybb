package com.bluemobi.ybb.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;

import com.bluemobi.ybb.R;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.view.LoadingPage;

/**
 * Created by gaoyn on 2015/7/7.
 *
 * p14-37 服务中心
 */
public class ServiceCenterActivity extends BaseActivity{

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(ServiceCenterActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.service_center, R.drawable.common_back, true);

        showLoadingPage(false);

    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.activity_service_center,null);

        mWebView = (WebView)view.findViewById(R.id.service_center);

        //mWebView.loadDataWithBaseURL(null, "123", "text/html", "utf-8", null);
        mWebView.loadData("ServiceCenterActivity","text/html; charset=UTF-8", null);

        return view;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void clickBarleft() {
        finish();
    }
}
