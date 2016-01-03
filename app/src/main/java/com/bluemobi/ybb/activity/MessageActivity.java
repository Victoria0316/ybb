package com.bluemobi.ybb.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.alarm.Alarm;
import com.bluemobi.ybb.alarm.DBManager;
import com.bluemobi.ybb.app.DbManager;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.db.entity.DisplayMessage;
import com.bluemobi.ybb.db.entity.Message;
import com.bluemobi.ybb.db.entity.MessageFoods;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.view.LoadingPage;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gaoyn on 2015/7/1.
 * <p/>
 * p5-4 消息
 */
public class MessageActivity extends BaseActivity implements View.OnClickListener {
    private final static String tag ="MessageActivity";

    private RelativeLayout TimingRemind;//定时提醒
    private RelativeLayout OrderRemind;//订单提醒
    private RelativeLayout ActivityRemind;//活动提醒
    private RelativeLayout SystemRemind;//系统提醒

    private TextView order_remind_content;
    private TextView order_remind_time;
    private Message bean;


    private TextView timing_remind_time;
    private TextView timing_remind_content;


    private DBManager mgr;

    Calendar calendar;

    private final ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(MessageActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.message_title, R.drawable.common_back, true);


        showLoadingPage(false);
    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.activity_message, null);

        TimingRemind = (RelativeLayout) view.findViewById(R.id.timing_remind_item);
        OrderRemind = (RelativeLayout) view.findViewById(R.id.order_remind_item);
        ActivityRemind = (RelativeLayout) view.findViewById(R.id.activity_remind_item);
        SystemRemind = (RelativeLayout) view.findViewById(R.id.system_remind_item);

        TimingRemind.setOnClickListener(this);
        OrderRemind.setOnClickListener(this);
        ActivityRemind.setOnClickListener(this);
        SystemRemind.setOnClickListener(this);

        order_remind_content = (TextView) view.findViewById(R.id.order_remind_content);
        order_remind_time = (TextView) view.findViewById(R.id.order_remind_time);


        DbUtils db = DbManager.getInstance(mContext).getDefaultDbUtils();
        try {
            bean = db.findFirst(Selector.from(Message.class).where("isread", "=", "0").orderBy("createTime"));
            if (bean != null) {
                order_remind_content.setText(bean.getMsg());
                order_remind_time.setText(bean.getCreateTime());
            } else {
                order_remind_content.setText("暂无");
                order_remind_time.setVisibility(View.INVISIBLE);

            }
        } catch (DbException e) {
            e.printStackTrace();
        }







        mgr = new DBManager(this); //初始化数据库


        // 读闹钟数据库 开起状态的闹钟==========================================
        List<Alarm> alarms = wipeOff();

        // 保存在当前时间以后的闹钟==========================================
        List<Alarm> alarmLater = new ArrayList<Alarm>();

        // 获得系统时间
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int mYear = calendar.get(Calendar.YEAR);//得到年
        int mMonth = calendar.get(Calendar.MONTH) + 1;//得到月，因为从0开始的，所以要加1
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);//得到天
        int mHour = calendar.get(Calendar.HOUR_OF_DAY);
        int mMinute = calendar.get(Calendar.MINUTE);

        // 设定时间的模板
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        // 排除系统时间之间的时间
        for (Alarm alarm : alarms) {
            Logger.d(tag, "alarm._id" + alarm._id + " alarm.of = " + alarm.off);
            try {
                Date d1 = sdf.parse(mHour + ":" + mMinute);
                Date d2 = sdf.parse(alarm.time);
                if (d1.getTime() >= d2.getTime()) {

                    System.out.println("d1 在" + alarm.time + "前");

                } else if (d1.getTime() <= d2.getTime()) {

                    System.out.println("d1在" + alarm.time + "后");
                    alarmLater.add(alarm);
                }

            } catch (ParseException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
        }

        calendar = Calendar.getInstance();
        int n = calendar.get(Calendar.DAY_OF_WEEK);
        Log.d(tag, "获得今天在本周的第几天: " + n);

        //今天开起的闹钟
        List<Alarm> todayOpenAlarms = filtrateAlarm(alarmLater, n);
        sortAlarm(todayOpenAlarms);
        //明天开起的闹钟
        List<Alarm> tomorrowOpenAlarms = filtrateAlarm(alarms, n + 1);
        sortAlarm(tomorrowOpenAlarms);

        //添加今天要响的闹钟
        for (int i = 0; i < todayOpenAlarms.size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("content_head", todayOpenAlarms.get(i).subheading);
            map.put("time", mYear + "/" + mMonth + "/" + mDay);

            list.add(map);
        }

        //添加明天要响的闹钟
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        for (int i = 0; i < tomorrowOpenAlarms.size(); i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("content_head", tomorrowOpenAlarms.get(i).subheading);
            map.put("time", calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH)+1) + "/" + calendar.get(Calendar.DAY_OF_MONTH));

            list.add(map);
        }

        timing_remind_time = (TextView) view.findViewById(R.id.timing_remind_time);
        timing_remind_content = (TextView) view.findViewById(R.id.timing_remind_content);

        if (list.size() != 0){
            timing_remind_time.setText(list.get(0).get("time"));
            timing_remind_content.setText(list.get(0).get("content_head"));
        }else{
            timing_remind_time.setText("");
            timing_remind_content.setText("");
        }


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
            case R.id.timing_remind_item:
                Utils.moveTo(this, RegularlyRemindActivity.class);
                break;
            case R.id.order_remind_item:
                Utils.moveTo(this, OrderRemindActivity.class);
                break;
            case R.id.activity_remind_item:
                Utils.moveTo(this, ActivitiesRemindActivity.class);
                break;
            case R.id.system_remind_item:
                Utils.moveTo(this, SystemRemindActivity.class);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mgr.closeDB();
    }

    /**
     * 开起状态的闹钟
     *
     * @return
     */
    private List<Alarm> wipeOff() {
        List<Alarm> alarms = mgr.query();
        List<Alarm> alarms_1 = new ArrayList<Alarm>();
        for (Alarm alarm : alarms) {

            // "1"代表闹钟开
            if ("1".equals(alarm.off)) {

                Alarm a = new Alarm();
                a = alarm;
                alarms_1.add(a);
                Logger.d(tag, "开起状态的闹钟：" + a._id + " " + a.time + " " + a.mon + " " + a.tue + " " + a.wed + " " + a.thu + " " + a.fri + " " + a.sat + " " + a.sun + " " + a.title + " " + a.subheading
                        + " " + a.off);

            }
        }
        return alarms_1;

    }

    /**
     * 判断周几的闹钟是否开起
     * @return
     */
    private List<Alarm> filtrateAlarm(List<Alarm> alarms_a, int n) {
        if (n > 7) n = 1;

        List<Alarm> alarms = alarms_a;
        List<Alarm> alarms_1 = new ArrayList<Alarm>();
        for (Alarm alarm : alarms) {

            switch (n) {
                case 1:
                    // 周日
                    // "1"代表闹钟开
                    if ("1".equals(alarm.sun)) {

                        Alarm a = new Alarm();
                        a = alarm;
                        alarms_1.add(a);

                    }
                    break;
                case 2:
                    // 周一
                    // "1"代表闹钟开
                    if ("1".equals(alarm.mon)) {

                        Alarm a = new Alarm();
                        a = alarm;
                        alarms_1.add(a);

                    }
                    break;
                case 3:
                    // 周二
                    if ("1".equals(alarm.tue)) {

                        Alarm a = new Alarm();
                        a = alarm;
                        alarms_1.add(a);

                    }
                    break;
                case 4:
                    // 周三
                    if ("1".equals(alarm.wed)) {

                        Alarm a = new Alarm();
                        a = alarm;
                        alarms_1.add(a);

                    }
                    break;
                case 5:
                    // 周四
                    if ("1".equals(alarm.thu)) {

                        Alarm a = new Alarm();
                        a = alarm;
                        alarms_1.add(a);

                    }
                    break;
                case 6:
                    // 周五
                    if ("1".equals(alarm.fri)) {

                        Alarm a = new Alarm();
                        a = alarm;
                        alarms_1.add(a);

                    }
                    break;
                case 7:
                    // 周六
                    if ("1".equals(alarm.sat)) {

                        Alarm a = new Alarm();
                        a = alarm;
                        alarms_1.add(a);

                    }
                    break;

                default:
                    break;
            }

        }
        return alarms_1;

    }


    /**
     * 闹钟排序
     *
     * @param alarms
     */
    public void sortAlarm(List<Alarm> alarms) {

        // 设定时间的模板
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        for (int i = 0; i < alarms.size() - 1; i++) {

            for (int j = i + 1; j < alarms.size(); j++) {

                Alarm temp;
                try {

                    Date d1 = sdf.parse(alarms.get(i).time);
                    Date d2 = sdf.parse(alarms.get(j).time);

                    if (d1.getTime() > d2.getTime()) {
                        temp = alarms.get(j);
                        alarms.remove(j);
                        alarms.add(j, alarms.get(i));
                        alarms.remove(i);
                        alarms.add(i, temp);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        }
    }
}
