package com.bluemobi.ybb.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.network.request.MyEvaluationRequest;
import com.bluemobi.ybb.network.response.MyEvaluationResponse;
import com.bluemobi.ybb.util.StringUtils;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.LoadingPage;

/**
 * Created by gaoyn on 2015/7/6.
 *
 * P11-3评论
 */
public class MyEvaluationActivity extends BaseActivity implements View.OnClickListener{

    private  String articleId;

    private  EditText et_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(MyEvaluationActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.MyEvaluation, R.drawable.common_back, true);

        showLoadingPage(false);
    }

    @Override
    protected void initBaseData() {
        articleId = getIntent().getStringExtra("articleId");
    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.activity_my_evaluation,null);
        Button btn_comment = (Button) view.findViewById(R.id.btn_comment);
        et_comment = (EditText) view.findViewById(R.id.et_comment);
        btn_comment.setOnClickListener(this);
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
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_comment:
                String content = et_comment.getText().toString().trim();
                if (StringUtils.isEmpty(content))
                {
                    Utils.makeToastAndShow(mContext, "请先添加评论，再提交");
                    return;
                }
                connectToServer(content);
                break;
        }

    }

    private void connectToServer(String commentInfo)
    {
        Utils.showProgressDialog(mContext);
        MyEvaluationRequest request = new MyEvaluationRequest
                (
                        new Response.Listener<MyEvaluationResponse>() {
                            @Override
                            public void onResponse(MyEvaluationResponse response) {
                                Utils.closeDialog();
                                if (response.getStatus() == 0)
                                {
                                    Toast.makeText(mContext, "提交成功",
                                            Toast.LENGTH_SHORT).show();
                                    setResult(Activity.RESULT_OK);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(mContext, response.getContent(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, this);
        request.setCommentUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setCommentNickName(YbbApplication.getInstance().getMyUserInfo().getNickName());
        request.setCommentInfo(commentInfo);
        request.setArticleId(articleId);
        Utils.showProgressDialog(this);
        WebUtils.doPost(request);
    }
}
