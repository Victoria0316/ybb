package com.bluemobi.ybb.ps.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.bluemobi.base.utils.Utils;
import com.bluemobi.base.utils.WebUtils;
import com.bluemobi.ybb.ps.R;
import com.bluemobi.ybb.ps.app.TitleBarManager;
import com.bluemobi.ybb.ps.app.YbbPsApplication;
import com.bluemobi.ybb.ps.base.BaseActivity;
import com.bluemobi.ybb.ps.network.request.FeedbackRequest;
import com.bluemobi.ybb.ps.network.response.FeedbackResponse;
import com.bluemobi.ybb.ps.view.LoadingPage;

/**
 * Created by wangzhijun on 2015/7/14.
 */
public class FeedBackActivity extends BaseActivity implements View.OnClickListener{

    private EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.title_feedback, R.drawable.common_back, false);

        showLoadingPage(false);
    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.activity_feedback, null);

        TextView submit = (TextView)view.findViewById(R.id.submit);
        submit.setOnClickListener(this);
        content = (EditText)view.findViewById(R.id.content);

        return view;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.submit:
                Request();
                break;
        }

    }

    private void Request() {

        FeedbackRequest request = new FeedbackRequest(new Response.Listener<FeedbackResponse>() {
            @Override
            public void onResponse(FeedbackResponse response) {
                if (response != null && response.getStatus() == 0) {// success
                    Utils.makeToastAndShow(getBaseContext(),"已提交");
                    finish();
                } else {// failed
                    Log.e("error", "error");
                }
            }
        },this);
        request.setUserId(YbbPsApplication.getInstance().getMyUserInfo().getUserId());
        request.setContent(content.getText().toString());
        Utils.showProgressDialog(this);
        WebUtils.doPost(request);
    }
}
