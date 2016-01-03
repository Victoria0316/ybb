package com.bluemobi.ybb.ps.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.bluemobi.base.utils.Utils;
import com.bluemobi.ybb.ps.R;
import com.bluemobi.ybb.ps.app.DbManager;
import com.bluemobi.ybb.ps.app.TitleBarManager;
import com.bluemobi.ybb.ps.base.BaseActivity;
import com.bluemobi.ybb.ps.db.entity.PSMessage;
import com.bluemobi.ybb.ps.db.entity.PSMessageFoods;
import com.bluemobi.ybb.ps.view.LoadingPage;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzhijun on 2015/7/14.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLoadingPage(false);
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.str_setting, R.drawable.common_back, true);

    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.activity_setting, null);

        view.findViewById(R.id.setting_clear).setOnClickListener(this);
        view.findViewById(R.id.rl_feedback).setOnClickListener(this);
        view.findViewById(R.id.rl_msg_set).setOnClickListener(this);
        view.findViewById(R.id.logout).setOnClickListener(this);
        view.findViewById(R.id.rl_about).setOnClickListener(this);//关于医帮宝
        return view;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_feedback:
                Utils.moveTo(this, FeedBackActivity.class);
                break;
            case R.id.setting_clear:
                //清楚缓存
                DbUtils db = DbManager.getInstance(mContext).getDefaultDbUtils();
                List<PSMessage> msgs = new ArrayList<PSMessage>();
                List<PSMessageFoods> foodses = new ArrayList<PSMessageFoods>();
                try {
                    msgs = db.findAll(Selector.from(PSMessage.class));
                    db.deleteAll(msgs);
                    foodses= db.findAll(Selector.from(PSMessageFoods.class));
                    db.deleteAll(foodses);
//                    alarm= db.findAll(Selector.from(Alarm.class));//不是一个db
//                    db.deleteAll(alarm);
                    Toast.makeText(mContext,"清除成功",Toast.LENGTH_SHORT).show();
                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rl_msg_set:
                Utils.moveTo(this, RemindSettingActivity.class);
                break;
            case R.id.rl_service:
                break;
            case R.id.rl_about:
                Utils.moveTo(this, SetUpAboutActivity.class);
                break;
            case R.id.logout:
                Utils.moveTo(this, LoginActivity.class);
                finishAll();
                break;

        }
    }
}
