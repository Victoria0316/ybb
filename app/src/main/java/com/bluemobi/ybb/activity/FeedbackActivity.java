package com.bluemobi.ybb.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.network.request.FeedbackRequest;
import com.bluemobi.ybb.network.response.FeedbackResponse;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.LoadingPage;

/**
 * Created by gaoyn on 2015/7/7.
 *
 * p14-36 意见反馈
 */
public class FeedbackActivity extends BaseActivity implements View.OnClickListener{

    private EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(FeedbackActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.feedback, R.drawable.common_back, true);

        showLoadingPage(false);
    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.activity_feedback,null);

        Button str_submit = (Button)view.findViewById(R.id.str_submit);
        str_submit.setOnClickListener(this);
        content = (EditText)view.findViewById(R.id.content);

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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.str_submit:
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
        request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setContent(content.getText().toString());
        Utils.showProgressDialog(this);
        WebUtils.doPost(request);

    }
}
