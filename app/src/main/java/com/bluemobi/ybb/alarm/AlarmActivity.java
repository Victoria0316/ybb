/**
 *
 */
package com.bluemobi.ybb.alarm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.util.Constants;
import com.bluemobi.ybb.util.SharedPreferencesUtil;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.view.CustomDialog;

public class AlarmActivity extends Activity {
    private final static String tag = "AlarmActivity";

    MediaPlayer alarmMusic = new MediaPlayer();

    Calendar calendar;

    private DBManager mgr;

    private int id = 0;

    Handler myHandler;

    MyThread myThread = new MyThread();

//	private MainActivity mainActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.alarm);

        mgr = new DBManager(this);

        Log.d(tag, "==444== alarmLater.get(position)._id = " + SharedPreferencesUtil.getIntFromFile(this, "id"));

        id = SharedPreferencesUtil.getIntFromFile(this, "id");

//        // 加载指定音乐，并为之创建MediaPlayer对象
//        alarmMusic = MediaPlayer.create(this, R.raw.alarm);
//        alarmMusic.setLooping(true);

        try {
            alarmMusic.setDataSource(this, RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            alarmMusic.setLooping(true);
            alarmMusic.prepare();
//            alarmMusic.start();
        } catch (Exception e) {
            e.printStackTrace();
        }


        calendar = Calendar.getInstance();
        int n = calendar.get(Calendar.DAY_OF_WEEK);
        Log.d(tag, "获得今天在本周的第几天: " + n);

        List<Alarm> alarms = mgr.query();

        switch (n) {
            case 1:
                // 周日
                for (Alarm alarm : alarms) {

                    // 找到是那条闹钟记录
                    if (id == alarm._id) {
                        // 判断闹钟是否响
                        isRingAlarm(alarm, alarm.sun);
                    }
                }
                break;
            case 2:
                // 周一
                for (Alarm alarm : alarms) {

                    // 找到是那条闹钟记录
                    if (id == alarm._id) {
                        // 判断闹钟是否响
                        isRingAlarm(alarm, alarm.mon);
                    }
                }
                break;
            case 3:
                // 周二
                for (Alarm alarm : alarms) {

                    // 找到是那条闹钟记录
                    if (id == alarm._id) {
                        // 判断闹钟是否响
                        isRingAlarm(alarm, alarm.tue);
                    }
                }
                break;
            case 4:
                // 周三

                for (Alarm alarm : alarms) {

                    // 找到是那条闹钟记录
                    if (id == alarm._id) {
                        // 判断闹钟是否响
                        isRingAlarm(alarm, alarm.wed);
                    }
                }

                break;
            case 5:
                // 周四
                for (Alarm alarm : alarms) {

                    // 找到是那条闹钟记录
                    if (id == alarm._id) {
                        // 判断闹钟是否响
                        isRingAlarm(alarm, alarm.thu);
                    }

                }
                break;
            case 6:
                // 周五
                for (Alarm alarm : alarms) {

                    // 找到是那条闹钟记录
                    if (id == alarm._id) {
                        // 判断闹钟是否响
                        isRingAlarm(alarm, alarm.fri);
                    }
                }
                break;
            case 7:
                // 周六
                for (Alarm alarm : alarms) {

                    // 找到是那条闹钟记录
                    if (id == alarm._id) {
                        // 判断闹钟是否响
                        isRingAlarm(alarm, alarm.sat);
                    }
                }
                break;

            default:
                break;
        }

    }

    /**
     * 判断闹钟是否响
     *
     * @param alarm
     * @param str
     */
    private void isRingAlarm(Alarm alarm, String str) {

        // 判断这条闹钟记录是否开启闹钟
        if ("1".equals(str)) {
            // 开启闹钟
            Log.d(tag, "周六  " + "开启闹钟");
            Log.d(tag, alarm._id + " " + alarm.time + " " + alarm.mon + " " + alarm.tue + " " + alarm.wed + " " + alarm.thu + " " + alarm.fri + " " + alarm.sat + " " + alarm.sun + " " + alarm.title
                    + " " + alarm.subheading + " " + alarm.off);
            showAlertDialog(alarm);
        } else {
            // 没开启闹钟
            Log.d(tag, "没开启闹钟");
            prepareAlarm();
            // 结束该Activity
            AlarmActivity.this.finish();
        }
    }


    private CustomDialog dialog;

    /**
     * 显示闹钟对话框
     */
    private void showAlertDialog(Alarm alarm) {

        // 播放音乐
        alarmMusic.start();
        // 创建一个对话框

        String[] s1 = alarm.time.split(":");

        myThread.start();

//        Builder builder = new Builder(AlarmActivity.this).setTitle(alarm.title).setMessage(Utils.timeFormat(s1[0]) + ":" + Utils.timeFormat(s1[1]) + " " + alarm.subheading).setPositiveButton("确定", new OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // 停止音乐
//                alarmMusic.stop();
//
//                prepareAlarm();
//
//                // 结束该Activity
//                AlarmActivity.this.finish();
//            }
//        });
//        builder.setCancelable(false);
//        builder.show();

        CustomDialog.Builder customBuilder = new CustomDialog.Builder(AlarmActivity.this);
        customBuilder.setTitle(alarm.title).setMessage(Utils.timeFormat(s1[0]) + ":" + Utils.timeFormat(s1[1]) + " " + alarm.subheading).setLineGONE(View.GONE).setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                // 停止音乐
                alarmMusic.stop();

                prepareAlarm();

                // 结束该Activity
                AlarmActivity.this.finish();

            }
        });
        dialog = customBuilder.create();
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        // TODO 自动生成的方法存根
        super.onDestroy();
        mgr.closeDB();
    }

    /**
     * 准备闹钟
     */
    public void prepareAlarm() {
        Log.d(tag, "准备闹钟");

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
        // 排除系统时间之前的时间
        for (Alarm alarm : alarms) {
            Log.d(tag, "alarm._id" + alarm._id + " alarm.of = " + alarm.off);
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
        Log.d(tag, "最小的时间" + d1.toString());
        // 根据最小时间设置闹钟 有最小时间走if 没有最小时间走 else ==========================================
        if (position != -1) {

            Log.d(tag, alarmLater.get(position).time);

            String[] s1 = alarmLater.get(position).time.split(":");// 以":"为分隔符，截取上面的字符串。结果为三段

            for (int i = 0; i < s1.length; i++) {
                System.out.println("设置闹钟的时间： " + s1[i]);// 循环输出结果
            }

            // 查找距当前时间最近的闹钟，而且要大于当前时间

            Log.d(tag, "==555== alarmLater.get(position)._id = " + alarmLater.get(position)._id + "");

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
            Log.d(tag, "最小的时间" + d3.toString());

            if (position_1 != -1) {

                Log.d(tag, alarms.get(position_1).time);

                // 以：拆分字符串
                String[] s1 = alarms.get(position_1).time.split(":");// 以":"为分隔符，截取上面的字符串。结果为三段

                for (int i = 0; i < s1.length; i++) {
                    System.out.println("设置闹钟的时间： " + s1[i]);// 循环输出结果
                }

                // 查找距当前时间最近的闹钟，而且要大于当前时间

                Log.d(tag, "==555== alarmLater.get(position_1)._id = " + alarms.get(position_1)._id + "");

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
                Log.d(tag, "开起状态的闹钟：" + a._id + " " + a.time + " " + a.mon + " " + a.tue + " " + a.wed + " " + a.thu + " " + a.fri + " " + a.sat + " " + a.sun + " " + a.title + " " + a.subheading
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
        Log.d(tag, "设置闹钟");

        // 保存 数据库 闹钟记录的id
        SharedPreferencesUtil.saveIntToFile(this, "id", id);

        Calendar calendar;
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        if (falg) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
            Log.d(tag, "明天闹钟：" + calendar.get(Calendar.DAY_OF_MONTH) + "号");
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


    public class MyThread extends Thread implements Runnable {
        @Override
        public void run() {
            // TODO Auto-generated method stub

            try {
                Thread.sleep(60*1000);// 线程暂停1分钟，单位毫秒
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);// 发送消息
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            // 要做的事情

            if (1 == Integer.parseInt("" + msg.what)) {
                // 停止音乐
                alarmMusic.stop();

                prepareAlarm();

                // 结束该Activity
                AlarmActivity.this.finish();
            }

            super.handleMessage(msg);


        }
    };
}
