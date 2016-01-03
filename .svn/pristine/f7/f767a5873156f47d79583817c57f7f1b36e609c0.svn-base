package com.bluemobi.ybb.activity;

import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bluemobi.ybb.R;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.util.PreferencesService;
import com.bluemobi.ybb.util.SharedPreferencesUtil;
import com.bluemobi.ybb.view.CustomLongSpinner;
import com.bluemobi.ybb.view.CustomSpinner;
import com.bluemobi.ybb.view.LoadingPage;
import com.bluemobi.ybb.view.SwitchView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoyn on 2015/7/7.
 * <p/>
 * p14-22 消息通知
 */
public class NewMessageNoticeActivity extends BaseActivity implements View.OnClickListener {

    private CustomLongSpinner remind_spinner;

    private SwitchView switch_button;

    private ImageView iv_switch_line;

    private List<String> tempdataList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(NewMessageNoticeActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.message_title, R.drawable.common_back, true);

        showLoadingPage(false);
    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.activity_new_message_notice, null);

        remind_spinner = (CustomLongSpinner) view.findViewById(R.id.remind_spinner);
        tempdataList.add("铃声提醒");
        tempdataList.add("震动提醒");

        remind_spinner.setDatas(tempdataList);

        remind_spinner.setSpinnerText();



        /*try {
            PreferencesService.getInstance(mContext).save("SDSD","铃声提醒");
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        switch_button = (SwitchView) view.findViewById(R.id.switch_button);

        if ("1".equals(SharedPreferencesUtil.getFromFileByDefault(this, "inform_off","1"))) {
            switch_button.setChecked(true);

        } else {
            switch_button.setChecked(false);
        }

        remind_spinner.setSpinnerText(SharedPreferencesUtil.getFromFileByDefault(this, "hint_type", "铃声提醒"));




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
        /*switch(v.getId()){
            case R.id.imageView:

                break;
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (switch_button.isChecked()){
            SharedPreferencesUtil.saveToFile(NewMessageNoticeActivity.this, "inform_off", "1");
        } else {
            SharedPreferencesUtil.saveToFile(NewMessageNoticeActivity.this, "inform_off", "0");
        }
        SharedPreferencesUtil.saveToFile(NewMessageNoticeActivity.this,"hint_type",remind_spinner.getSpinnerText());



    }
}
