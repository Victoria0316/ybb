package com.bluemobi.ybb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.util.Constants;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.view.LoadingPage;
import com.bluemobi.ybb.view.WheelView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * P14-32
 * 编辑定制
 * Created by wangzhijun on 2false15/7/13.
 */
public class MyAlarmWeekActivity extends BaseActivity{
    private final static String tag ="MyAlarmWeekActivity";

    private TitleBarManager titleBarManager;

    private boolean selectArray[] = new boolean[]{false,false,false,false,false,false,false};

    private RelativeLayout week1;
    private RelativeLayout week2;
    private RelativeLayout week3;
    private RelativeLayout week4;
    private RelativeLayout week5;
    private RelativeLayout week6;
    private RelativeLayout week7;
    private List<RelativeLayout> lists = new ArrayList<RelativeLayout>();
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLoadingPage(false);
        titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showTitleTextBar(R.string.my_account_alarm_repeat, R.drawable.common_back, R.string.str_finish);
    }

    @Override
    public void clickBarRight() {
        Logger.d(tag, "你单击了完成");

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putBooleanArray("week", selectArray);
        intent.putExtras(bundle);
        setResult(Constants.MY_ALARM_WEEK_ACTIVITY_RESULT_OK, intent);
        finish();

    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.activity_my_alarm_week, null);
        loadByData(view);
        return view;
    }

    private void loadByData(View view) {

        //记录上一页传过的信息
        //               时间  周一  至 周日  标题 副标题 开关off
        //alarm 格式是  10：10 1 1 1 1 1 1 1 aa ss 1
        String[] s1;

        Intent intent = this.getIntent();
        if (intent.getExtras().getStringArray("alarm")!=null){

            s1 = intent.getExtras().getStringArray("alarm");
            for (int i=0 ; i<selectArray.length; i++){
                Logger.d(tag,i+2+" = "+s1[i+2]);
                if ("1".equals(s1[i+2])){
                    selectArray[i] = true;
                }else{
                    selectArray[i] = false;
                }
            }
        }


        week1 = (RelativeLayout)view.findViewById(R.id.week1);
        week2 = (RelativeLayout)view.findViewById(R.id.week2);
        week3 = (RelativeLayout)view.findViewById(R.id.week3);
        week4 = (RelativeLayout)view.findViewById(R.id.week4);
        week5 = (RelativeLayout)view.findViewById(R.id.week5);
        week6 = (RelativeLayout)view.findViewById(R.id.week6);
        week7 = (RelativeLayout)view.findViewById(R.id.week7);
        lists.add(week1);
        lists.add(week2);
        lists.add(week3);
        lists.add(week4);
        lists.add(week5);
        lists.add(week6);
        lists.add(week7);
        for(int i=0; i<selectArray.length; i++){
            if(selectArray[i]){
                lists.get(i).findViewWithTag("status").setVisibility(View.VISIBLE);
            }else{
                lists.get(i).findViewWithTag("status").setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }



    public void doSelect(View view){
        String tag = (String)view.getTag();
        int index = Integer.parseInt(tag) -1;
        selectArray[index] = !selectArray[index];
        if(selectArray[index]){
            view.findViewWithTag("status").setVisibility(View.VISIBLE);
        }else{
            view.findViewWithTag("status").setVisibility(View.GONE);
        }
    }
}
