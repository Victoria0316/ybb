package com.bluemobi.ybb.activity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.alarm.Alarm;
import com.bluemobi.ybb.alarm.AlarmReceiver;
import com.bluemobi.ybb.alarm.DBManager;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.util.Constants;
import com.bluemobi.ybb.util.SharedPreferencesUtil;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.view.CustomLongSpinner;
import com.bluemobi.ybb.view.LoadingPage;
import com.bluemobi.ybb.view.SwitchView;
import com.bluemobi.ybb.view.WheelView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * P14-32
 * 编辑定制
 * Created by wangzhijun on 2015/7/13.
 */
public class MyAlarmEditActivity extends BaseActivity implements View.OnClickListener {
    private final static String tag = "MyAlarmEditActivity";

    private TitleBarManager titleBarManager;

    private CustomLongSpinner remind_spinner;
    private List<String> tempdataList = new ArrayList<String>();

    private static final String[] PLANETS = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24"};

    private static final String[] minutes = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
            "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
            "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
            "51", "52", "53", "54", "55", "56", "57", "58", "59", "60"
    };

    private WheelView hour;

    private WheelView minute;

    private int hour_index;

    private int minute_index;

    //小时
    private String AlarmHour = "1";

    //分钟
    private String AlarmMinute = "1";

    //重复  周一、周二、周三、周四、周五、周六、周日
    private TextView content;


    //保存  重复按钮  选择 出来的 周几
    private boolean selectArray[] = new boolean[]{false, false, false, false, false, false, false};

    private String week[] = new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日"};

    //副标题  晚餐定制
    private EditText subheading;

    //记录上一页传过的信息
    //               时间  周一  至 周日  标题 副标题 开关off
    //alarm 格式是   10：10 1 1 1 1 1 1 1 aa ss 1
    private String[] s1;

    private Calendar calendar;  //日历

    private Intent intent;

    private Bundle bundle;

    private DBManager mgr;

    private int id;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLoadingPage(false);
        titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showTitleTextBar(R.string.my_account_alarm_remind_edit, R.drawable.common_back, R.string.str_finish);
    }

    @Override
    public void clickBarRight() {
        //标题栏右边的完成按钮 获取闹钟的信息，设置闹钟

        if (checkInput()) {


            if ("ModifyAlarm".equals(bundle.getString("status"))) {//修改闹钟
                Logger.d(tag, "修改闹钟");
                //=====================================================
                Logger.d(tag, "时间：" + AlarmHour + ":" + AlarmMinute);

                //=====================================================
                //重复  周一、周二、周三、周四、周五、周六、周日
                String str = "";
                for (int i = 0; i < selectArray.length; i++) {
                    if (selectArray[i]) {
                        str += "1 ";
                    } else {
                        str += "0 ";
                    }
                }
                Logger.d(tag, "重复：" + str);

                //=====================================================
                Logger.d(tag, "标题：" + remind_spinner.getSpinnerText());

                Logger.d(tag, "副标题：" + subheading.getText().toString());

                //全部的数据字符串 闹钟保存到数据库用的字符串
                String strAlarm = AlarmHour + ":" + AlarmMinute + " " + str + remind_spinner.getSpinnerText() + " " + subheading.getText().toString() + " " + "1";
                Logger.d(tag, strAlarm);


                updateAlarm(strAlarm);


            } else {//添加闹钟
                Logger.d(tag, "添加闹钟");

                //=====================================================
                Logger.d(tag, "时间：" + AlarmHour + ":" + AlarmMinute);

                //=====================================================
                //重复  周一、周二、周三、周四、周五、周六、周日
                String str = "";
                for (int i = 0; i < selectArray.length; i++) {
                    if (selectArray[i]) {
                        str += "1 ";
                    } else {
                        str += "0 ";
                    }
                }
                Logger.d(tag, "重复：" + str);

                //=====================================================
                Logger.d(tag, "标题：" + remind_spinner.getSpinnerText());

                Logger.d(tag, "副标题：" + subheading.getText().toString());


                //全部的数据字符串 闹钟保存到数据库用的字符串
                String strAlarm = AlarmHour + ":" + AlarmMinute + " " + str + remind_spinner.getSpinnerText() + " " + subheading.getText().toString() + " " + "1";
                Logger.d(tag, strAlarm);

                addAlarm(strAlarm);

            }

            setResult(100, getIntent());
            finish();

        }
    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {


        mgr = new DBManager(this);


        //初始化 时间 为系统时间
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        View view = inflater.inflate(R.layout.activity_my_alarm_edit, null);
        hour = (WheelView) view.findViewById(R.id.time_h);
        minute = (WheelView) view.findViewById(R.id.time_m);
        hour.setOffset(1);
        hour.setItems(Arrays.asList(PLANETS));
        hour.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Logger.d("wangzhijun", "selectedIndex: " + selectedIndex + ", item: " + item);
                AlarmHour = item;
            }
        });

        minute.setOffset(1);
        minute.setItems(Arrays.asList(minutes));
        minute.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Logger.d("wangzhijun", "selectedIndex: " + selectedIndex + ", item: " + item);
                AlarmMinute = item;
            }
        });
        view.findViewById(R.id.week_select).setOnClickListener(this);

        remind_spinner = (CustomLongSpinner) view.findViewById(R.id.remind_spinner);
        tempdataList.add(getResources().getString(R.string.message_medicineAlarms));
        tempdataList.add(getResources().getString(R.string.message_mealAlarms));

        remind_spinner.setDatas(tempdataList);

        remind_spinner.setSpinnerText();


        content = (TextView) view.findViewById(R.id.content); //重复  周一、周二、周三、周四、周五、周六、周日

        subheading = (EditText) view.findViewById(R.id.subheading);//副标题  晚餐定制


        //初始化 时间 为系统时间
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        hour.setSeletion(calendar.get(Calendar.HOUR_OF_DAY) - 1);
        minute.setSeletion(calendar.get(Calendar.MINUTE) - 1);
        AlarmHour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        AlarmMinute = String.valueOf(calendar.get(Calendar.MINUTE));


        //单击setOnChildClickListener 跳过来的，修改闹钟信息==================================================
        intent = this.getIntent();
        bundle = intent.getExtras();
        if ("ModifyAlarm".equals(bundle.getString("status"))) {
            //             id  时间  周一  至 周日  标题 副标题 开关off
            //alarm 格式是  90 10：10 1 1 1 1 1 1 1 aa ss 1
            s1 = bundle.getString("alarm").split(" ");

            //id 记录在数据库里的id
            id = Integer.parseInt(s1[0]);
            Logger.d(tag, "id = " + id);

            //时间
            String[] s2 = s1[1].split(":");
            Logger.d(tag, "小时：" + s2[0]);
            Logger.d(tag, "分钟：" + s2[1]);
            hour.setSeletion(Integer.parseInt(s2[0]) - 1);
            minute.setSeletion(Integer.parseInt(s2[1]) - 1);
            AlarmHour = s2[0];
            AlarmMinute = s2[1];


            //重复的状态==================================================
            for (int i = 0; i < 7; i++) {
                Logger.d(tag, i + " = " + s1[i + 2]);
                if ("1".equals(s1[i + 2])) {
                    selectArray[i] = true;
                } else {
                    selectArray[i] = false;
                }

            }
            String str = "";
            for (int i = 0; i < selectArray.length; i++) {
                if (selectArray[i]) {
                    str += week[i] + "、";
                }
            }
            content.setText(str);

            //标题==================================================
            remind_spinner.setSpinnerText(s1[9]);

            //副标题==================================================
            subheading.setText(s1[10]);

            //开关off==================================================


        }


        //MyAlarmActivity.java  传过来的数据
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if ("AddAlarm".equals(bundle.getString("status"))) {

            String title = bundle.getString("title");

//            Toast.makeText(this, "MyAlarmActivity传过来 0 ： " + n, Toast.LENGTH_SHORT).show();

            //标题==================================================
            remind_spinner.setSpinnerText(title);
        }


        return view;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.week_select:

//                Utils.moveTo(this, MyAlarmWeekActivity.class);

                //跳到选择重复页 选择  周一、周二、周三、周四、周五、周六、周日
                Intent intent = new Intent();
                intent.putExtra("alarm", s1);
                intent.setClass(this, MyAlarmWeekActivity.class);
                startActivityForResult(intent, 0);

                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {

            case Constants.MY_ALARM_WEEK_ACTIVITY_RESULT_OK:
                Bundle b = data.getExtras();
                selectArray = b.getBooleanArray("week");

                String str = "";
                for (int i = 0; i < selectArray.length; i++) {
                    if (selectArray[i]) {
                        str += week[i] + "、";
                    }
                }

                content.setText(str);


                break;
            default:
                break;

        }
    }


    /**
     * 检测表单
     *
     * @return
     */
    private boolean checkInput() {


        //=====================================================
        //重复  周一、周二、周三、周四、周五、周六、周日
        String str = "";
        for (int i = 0; i < selectArray.length; i++) {
            if (selectArray[i]) {
                str += "1 ";
            } else {
                str += "";
            }
        }
        Logger.d(tag, "重复：" + str);

        if ("".equals(str)) {

            Toast.makeText(this, "请选择重复时间", Toast.LENGTH_SHORT).show();
            return false;
        }

        if("".equals(subheading.getText().toString())){
            Toast.makeText(this, "请输入定制名称", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            subheading.setText(subheading.getText().toString().replaceAll(" ", ""));
        }


        return true;
    }


    /**
     * 添加闹钟 ： 默认 on-off == 1 time mon tue wed thu fri sat sun title subheading off 10:10 1 1 0 0 0 0 0 药定时提醒 早餐前吃过敏药 1
     *
     * @param time       时间
     * @param mon        周一
     * @param tue        周二
     * @param wed        周三
     * @param thu        周四
     * @param fri        周五
     * @param sat        周六
     * @param sun        周日
     * @param title      标题
     * @param subheading 副标题
     * @param off        闹钟开关  1开     0关
     */
    public void add(String time, String mon, String tue, String wed, String thu, String fri, String sat, String sun, String title, String subheading, String off) {
        ArrayList<Alarm> alarm = new ArrayList<Alarm>();

        Alarm alarm1 = new Alarm(time, mon, tue, wed, thu, fri, sat, sun, title, subheading, off);
        alarm.add(alarm1);

        mgr.add(alarm);
    }

    /**
     * 修改闹钟：修改Sqlite里的记录 ，然后准备好闹钟
     *
     * @param str
     */
    private void updateAlarm(String str) {
        String[] s1 = str.split(" ");
        update(id, s1[0], s1[1], s1[2], s1[3], s1[4], s1[5], s1[6], s1[7], s1[8], s1[9], s1[10]);
        // 准备闹钟
        prepareAlarm();
    }

    /**
     * 更新闹钟
     *
     * @param _id        id
     * @param time       时间
     * @param mon        周一
     * @param tue        周二
     * @param wed        周三
     * @param thu        周四
     * @param fri        周五
     * @param sat        周六
     * @param sun        周日
     * @param title      标题
     * @param subheading 副标题
     * @param off        闹钟开关  1开     0关
     */
    private void update(int _id, String time, String mon, String tue, String wed, String thu, String fri, String sat, String sun, String title, String subheading, String off) {
        Alarm alarm = new Alarm();
        alarm._id = _id;
        alarm.time = time;
        alarm.mon = mon;
        alarm.tue = tue;
        alarm.wed = wed;
        alarm.thu = thu;
        alarm.fri = fri;
        alarm.sat = sat;
        alarm.sun = sun;
        alarm.title = title;
        alarm.subheading = subheading;
        alarm.off = off;
        mgr.updateAge(alarm);

    }

    /**
     * 添加闹钟：把闹钟添加到Sqlite里 ，然后准备好闹钟
     */
    private void addAlarm(String str) {
        // TODO 自动生成的方法存根


        // 以：拆分字符串
        String[] s1 = str.split(" ");// 以":"为分隔符，截取上面的字符串。

        add(s1[0], s1[1], s1[2], s1[3], s1[4], s1[5], s1[6], s1[7], s1[8], s1[9], s1[10]);

        // 准备闹钟
        prepareAlarm();


    }


    /**
     * 准备闹钟
     */
    public void prepareAlarm() {
        Logger.d(tag, "准备闹钟");

        // 读闹钟数据库 开起状态的闹钟==========================================
        List<Alarm> alarms = wipeOff();

        // 保存在当前时间以后的闹钟==========================================
        List<Alarm> alarmLater = new ArrayList<Alarm>();

        // 获得系统时间
        calendar.setTimeInMillis(System.currentTimeMillis());
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
        // 排序 选出最小的时间==========================================
        int position = -1;// 保存最小时间的位置
        Date d1 = null;
        try {
            d1 = sdf.parse("23:59");//
        } catch (ParseException e1) {
            // TODO 自动生成的 catch 块
            e1.printStackTrace();
        }
        for (int i = 0; i < alarmLater.size(); i++) {
            try {
                Date d2 = sdf.parse(alarmLater.get(i).time);
                if (d1.getTime() >= d2.getTime()) {

                    System.out.println("d1=" + d1.toString() + " 在" + alarmLater.get(i).time + "前");
                    d1 = d2;
                    position = i;

                } else if (d1.getTime() <= d2.getTime()) {

                    System.out.println("d1=" + d1.toString() + " 在" + alarmLater.get(i).time + "后");

                }

            } catch (ParseException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }

        }
        Logger.d(tag, "最小的时间" + d1.toString());

        // 根据最小时间设置闹钟 有最小时间走if 没有最小时间走 else ==========================================
        if (position != -1) {

            Logger.d(tag, alarmLater.get(position).time);

            String[] s1 = alarmLater.get(position).time.split(":");// 以":"为分隔符，截取上面的字符串。结果为二段

            for (int i = 0; i < s1.length; i++) {
                System.out.println("设置闹钟的时间： " + s1[i]);// 循环输出结果
            }

            // 查找距当前时间最近的闹钟，而且要大于当前时间

            Logger.d(tag, "==111== alarmLater.get(position)._id = " + alarmLater.get(position)._id + "");

            // 设置闹钟
            setAlarm(false, Integer.parseInt(s1[0]), Integer.parseInt(s1[1]), alarmLater.get(position)._id);

        } else {

            // 在没有最小时间的时候，重新读数据库，选出最小时间，设置第二天响闹钟

            // 排序 选出最小的时间
            int position_1 = -1;// 保存最小时间的位置
            Date d3 = null;
            try {
                d3 = sdf.parse("23:59");//
            } catch (ParseException e1) {
                // TODO 自动生成的 catch 块
                e1.printStackTrace();
            }
            for (int i = 0; i < alarms.size(); i++) {
                try {
                    Date d2 = sdf.parse(alarms.get(i).time);
                    if (d3.getTime() >= d2.getTime()) {

                        System.out.println("d3=" + d3.toString() + " 在" + alarms.get(i).time + "前");
                        d3 = d2;
                        position_1 = i;

                    } else if (d3.getTime() <= d2.getTime()) {

                        System.out.println("d3=" + d3.toString() + " 在" + alarms.get(i).time + "后");

                    }

                } catch (ParseException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }

            }
            Logger.d(tag, "最小的时间" + d3.toString());

            if (position_1 != -1) {

                Logger.d(tag, alarms.get(position_1).time);

                // 以：拆分字符串
                String[] s1 = alarms.get(position_1).time.split(":");// 以":"为分隔符，截取上面的字符串。结果为三段

                for (int i = 0; i < s1.length; i++) {
                    System.out.println("设置闹钟的时间： " + s1[i]);// 循环输出结果
                }

                // 查找距当前时间最近的闹钟，而且要大于当前时间

                Logger.d(tag, "==555== alarmLater.get(position_1)._id = " + alarms.get(position_1)._id + "");

                // 设置闹钟
                setAlarm(true, Integer.parseInt(s1[0]), Integer.parseInt(s1[1]), alarms.get(position_1)._id);
            } else {//取消闹钟
                Intent intent = new Intent(this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
                AlarmManager am;
                /* 获取闹钟管理的实例 */
                am = (AlarmManager) getSystemService(ALARM_SERVICE);
                /* 取消 */
                am.cancel(pendingIntent);
                Logger.d(tag, "闹钟已取消！");
                if (Constants.ALARM_TOAST_OFF) {
                    Toast.makeText(this, "闹钟已取消", Toast.LENGTH_SHORT).show();
                }

            }


        }

    }

    // List<Alarm> alarms_1;

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
     * 设置闹钟
     *
     * @param hourOfDay 小时
     * @param minute    分钟
     */
    private void setAlarm(Boolean falg, int hourOfDay, int minute, int id) {
        Logger.d(tag, "设置闹钟");

        // 保存 数据库 闹钟记录的id
        SharedPreferencesUtil.saveIntToFile(this, "id", id);

        Calendar calendar;
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        if (falg) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
            Logger.d(tag, "明天闹钟：" + calendar.get(Calendar.DAY_OF_MONTH) + "号");
            if (Constants.ALARM_TOAST_OFF) {
                Toast.makeText(this, "明天闹钟：" + calendar.get(Calendar.DAY_OF_MONTH) + "号", Toast.LENGTH_SHORT).show();
            }
        }
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

		/* 建立Intent和PendingIntent，来调用目标组件 */
        Intent intent;
        intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        // AlarmManager am;
        /* 获取闹钟管理的实例 */
        AlarmManager am;
        am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        /* 设置闹钟 */
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        if (Constants.ALARM_TOAST_OFF) {
            Toast.makeText(this, hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mgr.closeDB();
    }
}
