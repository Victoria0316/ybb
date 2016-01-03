package com.bluemobi.ybb.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.adapter.CommonAdapter;
import com.bluemobi.ybb.adapter.ViewHolder;
import com.bluemobi.ybb.alarm.Alarm;
import com.bluemobi.ybb.alarm.DBManager;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.view.LoadingPage;

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
 * p5-7 定时提醒
 */
public class RegularlyRemindActivity extends BaseActivity {
    private final static String tag = "RegularlyRemindActivity";


    private ListView lv_refresh;

    private SimpleAdapter adapter;

    private DBManager mgr;

    Calendar calendar;

    private final ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(RegularlyRemindActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.regularly_remind, R.drawable.common_back, true);

        showLoadingPage(false);

    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {


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

            String[] s1 = todayOpenAlarms.get(i).time.split(":");// 以":"为分隔符，截取上面的字符串。结果为三段
            s1[0] = Utils.timeFormat(s1[0]);
            s1[1] = Utils.timeFormat(s1[1]);

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("content_head", todayOpenAlarms.get(i).subheading);
            map.put("time", mYear + "/" + mMonth + "/" + mDay + "  " + s1[0] + ":" + s1[1]);

            list.add(map);
        }

        //添加明天要响的闹钟
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        for (int i = 0; i < tomorrowOpenAlarms.size(); i++) {

            String[] s1 = tomorrowOpenAlarms.get(i).time.split(":");// 以":"为分隔符，截取上面的字符串。结果为三段
            s1[0] = Utils.timeFormat(s1[0]);
            s1[1] = Utils.timeFormat(s1[1]);

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("content_head", tomorrowOpenAlarms.get(i).subheading);
            map.put("time", calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH)+1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "  " + s1[0] + ":" + s1[1]);

            list.add(map);
        }


        View view = inflater.inflate(R.layout.activity_regularly_remind, null);

        lv_refresh = (ListView) view.findViewById(R.id.lv_refresh);

        adapter = new SimpleAdapter(this, list, R.layout.lv_regularly_remind, new String[]{"content_head", "time"}, new int[]{R.id.content_head, R.id.tv_time});
        lv_refresh.setAdapter(adapter);


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
