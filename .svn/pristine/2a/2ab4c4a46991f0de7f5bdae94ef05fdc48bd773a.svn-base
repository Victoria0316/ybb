package com.bluemobi.ybb.ps.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;

import com.bluemobi.ybb.ps.R;
import com.bluemobi.ybb.ps.app.TitleBarManager;
import com.bluemobi.ybb.ps.base.BaseActivity;
import com.bluemobi.ybb.ps.view.LoadingPage;


/**
 * Created by gaoyn on 2015/7/7.
 * <p/>
 * p14-38 关于医帮宝
 */
public class SetUpAboutActivity extends BaseActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(SetUpAboutActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.set_up_about, R.drawable.common_back, true);


        showLoadingPage(false);

    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.activity_set_up_about, null);

        mWebView = (WebView) view.findViewById(R.id.set_up_about);
//
//        //mWebView.loadDataWithBaseURL(null, "123", "text/html", "utf-8", null);
        mWebView.loadData("SetUpAboutActivity", "text/html; charset=UTF-8", null);

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
