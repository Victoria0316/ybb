package com.bluemobi.ybb.ps.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.bluemobi.base.utils.SharedPreferencesUtil;
import com.bluemobi.ybb.ps.R;
import com.bluemobi.ybb.ps.app.TitleBarManager;
import com.bluemobi.ybb.ps.base.BaseActivity;
import com.bluemobi.ybb.ps.view.CustomLongSpinner;
import com.bluemobi.ybb.ps.view.LoadingPage;
import com.bluemobi.ybb.ps.view.SwitchView;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息提醒设置
 * Created by wangzhijun on 2015/7/14.
 */
public class RemindSettingActivity extends BaseActivity{

    private CustomLongSpinner remind_spinner;


    private List<String> tempdataList = new ArrayList<String>();

    private SwitchView switch_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        showLoadingPage(false);
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this,getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.setting_message, R.drawable.common_back,true);
    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.activity_remind_setting, null);


        remind_spinner = (CustomLongSpinner) view.findViewById(R.id.remind_spinner);
        tempdataList.add("铃声提醒");
        tempdataList.add("震动提醒");
//
        remind_spinner.setDatas(tempdataList);
//
        remind_spinner.setSpinnerText();




        switch_button = (SwitchView) view.findViewById(R.id.switchView);

        if ("1".equals(SharedPreferencesUtil.getFromFileByDefault(this, "inform_off", "1"))) {
            switch_button.setChecked(true);

        } else {
            switch_button.setChecked(false);
        }

        remind_spinner.setSpinnerText(SharedPreferencesUtil.getFromFileByDefault(RemindSettingActivity.this, "hint_type", "铃声提醒"));



        return view;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (switch_button.isChecked()){
            SharedPreferencesUtil.saveToFile(RemindSettingActivity.this, "inform_off", "1");
        } else {
            SharedPreferencesUtil.saveToFile(RemindSettingActivity.this, "inform_off", "0");
        }

        SharedPreferencesUtil.saveToFile(this,"hint_type",remind_spinner.getSpinnerText());



    }
}
