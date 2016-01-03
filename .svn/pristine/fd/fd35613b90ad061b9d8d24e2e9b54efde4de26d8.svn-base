package com.bluemobi.ybb.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bluemobi.ybb.R;
import com.bluemobi.ybb.alarm.Alarm;
import com.bluemobi.ybb.app.DbManager;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.db.entity.Message;
import com.bluemobi.ybb.db.entity.MessageFoods;
import com.bluemobi.ybb.util.Constants;
import com.bluemobi.ybb.util.SharedPreferencesUtil;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.view.LoadingPage;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoyn on 2015/7/7.
 * <p/>
 * p14-35 设置
 */
public class SetUpActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout feedback;  //意见反馈
    private RelativeLayout clear_cache; //清除缓存
    private RelativeLayout service_center; //服务中心
    private RelativeLayout set_up_about; //关于医帮宝
    private TextView exit; //退出当前账号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(SetUpActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.set_up, R.drawable.common_back, true);

        showLoadingPage(false);

    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.activity_set_up, null);

        feedback = (RelativeLayout) view.findViewById(R.id.feedback);
        clear_cache = (RelativeLayout) view.findViewById(R.id.clear_cache);
        service_center = (RelativeLayout) view.findViewById(R.id.service_center);
        set_up_about = (RelativeLayout) view.findViewById(R.id.set_up_about);
        exit = (TextView) view.findViewById(R.id.exit);

        feedback.setOnClickListener(this);
        clear_cache.setOnClickListener(this);
        service_center.setOnClickListener(this);
        set_up_about.setOnClickListener(this);
        exit.setOnClickListener(this);


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
        switch (v.getId()) {
            case R.id.feedback:
                Utils.moveTo(this, FeedbackActivity.class);
                break;
            case R.id.service_center:
                Utils.moveTo(this, ServiceCenterActivity.class);
                break;
            case R.id.set_up_about:
                Utils.moveTo(this, SetUpAboutActivity.class);
                break;
            case R.id.exit:
                //退出当前账号 修改记住密码为 "0" 不记住密码  删除用户名  密码
                SharedPreferencesUtil.saveToFile(this, Constants.USERACCOUNT, "");
                SharedPreferencesUtil.saveToFile(this, Constants.USERPWD, "");
                SharedPreferencesUtil.saveToFile(this, Constants.ISREMBERPWD, "0");
                Utils.moveTo(this, LoginActivity.class);
                finishAll();
                break;
            case R.id.clear_cache:
                //清楚缓存
                DbUtils db = DbManager.getInstance(mContext).getDefaultDbUtils();
                List<Message> msgs = new ArrayList<Message>();
                List<MessageFoods> foodses = new ArrayList<MessageFoods>();
                List<Alarm> alarm = new ArrayList<Alarm>();
                try {
                    msgs = db.findAll(Selector.from(Message.class));
                    db.deleteAll(msgs);
                    foodses= db.findAll(Selector.from(MessageFoods.class));
                    db.deleteAll(foodses);
//                    alarm= db.findAll(Selector.from(Alarm.class));//不是一个db
//                    db.deleteAll(alarm);
                    Toast.makeText(mContext,"清除成功",Toast.LENGTH_SHORT).show();
                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
