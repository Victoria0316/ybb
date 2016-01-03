package com.bluemobi.ybb.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
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
import com.bluemobi.ybb.view.LoadingPage;
import com.bluemobi.ybb.view.SwitchDotView;
import com.bluemobi.ybb.view.SwitchView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * P14-31 我的定制
 * Created by wangzhijun on 2015/7/11.
 */
public class MyAlarmActivity extends BaseActivity {
    private final static String tag = "MyAlarmActivity";

    private ExpandableListView listView;

    private ListView listView_list; //临时的

    private List<TempGroup> groups = new ArrayList<TempGroup>();
    private List<List<Alarm>> childs = new ArrayList<List<Alarm>>();
    private AlarmAdapter mAdapter;
    private boolean edit;
    private TitleBarManager titleBarManager;

    private DBManager mgr;

    private final ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();

    private SimpleAdapter adapter;

    private List<Alarm> medicineAlarms = new ArrayList<Alarm>();//保存药定时提醒

    private List<Alarm> mealAlarms = new ArrayList<Alarm>();//保存订餐提醒

    private Calendar calendar;  //日历

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLoadingPage(false);
        titleBarManager = new TitleBarManager();
        titleBarManager.init(this, getSupportActionBar());
        titleBarManager.showTitleTextBar(R.string.my_account_remind, R.drawable.common_back, R.string.str_edit);
    }

    @Override
    public void clickBarRight() {
        if (edit) {
            titleBarManager.setRightText(R.string.str_edit);
        } else {
            titleBarManager.setRightText(R.string.str_finish);
        }
        edit = !edit;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {

        calendar = Calendar.getInstance();

        View view = inflater.inflate(R.layout.activity_alarm, null);

        query();

        //===========================================================================================================
        //临时 调试 用来看数据库内容 不对程序起作用==================================================================================================
        listView_list = (ListView) view.findViewById(R.id.listView); //临时的


        adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2, new String[]{"id", "title"}, new int[]{android.R.id.text1, android.R.id.text2});
        listView_list.setAdapter(adapter);

        listView_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Logger.d(tag, "你单击了onChildClick");
                Intent intent = new Intent();
                intent.setClass(MyAlarmActivity.this, MyAlarmEditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("alarm", list.get(position).get("title"));
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
//                Utils.moveTo(MyAlarmActivity.this, MyAlarmEditActivity.class);
            }
        });
        listView_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Logger.d(tag, "长按删除");
                delete(Integer.parseInt(list.get(position).get("id")));
                list.remove(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
        //===========================================================================================================
        //===========================================================================================================


        listView = (ExpandableListView) view.findViewById(R.id.alarms);
        groups.add(new TempGroup(getResources().getString(R.string.message_medicineAlarms)));
        groups.add(new TempGroup(getResources().getString(R.string.message_mealAlarms)));

//        List<Alarm> temp1 = new ArrayList<Alarm>();
//        temp1.add(new TempChild("02:02","title1"));
//        temp1.add(new TempChild("02:02","title2"));
//        temp1.add(new TempChild("02:02","title3"));
//        List<Alarm> temp2 = new ArrayList<Alarm>();
//        temp2.add(new TempChild("04:04","title1"));
//        temp2.add(new TempChild("04:04","title2"));
//        temp2.add(new TempChild("04:04","title3"));



        //把闹钟添加到 ExpandableListView childs 中
        childs.add(mealAlarms);
        childs.add(medicineAlarms);

        mAdapter = new AlarmAdapter();
        listView.setAdapter(mAdapter);
        for (int i = 0; i < mAdapter.getGroupCount(); i++) {
            listView.expandGroup(i);
        }
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Logger.d(tag, "你单击了onChildClick");

                String str = "";
                str += childs.get(groupPosition).get(childPosition)._id + " " +
                        childs.get(groupPosition).get(childPosition).time + " " +
                        childs.get(groupPosition).get(childPosition).mon + " " +
                        childs.get(groupPosition).get(childPosition).tue + " " +
                        childs.get(groupPosition).get(childPosition).wed + " " +
                        childs.get(groupPosition).get(childPosition).thu + " " +
                        childs.get(groupPosition).get(childPosition).fri + " " +
                        childs.get(groupPosition).get(childPosition).sat + " " +
                        childs.get(groupPosition).get(childPosition).sun + " " +
                        childs.get(groupPosition).get(childPosition).title + " " +
                        childs.get(groupPosition).get(childPosition).subheading + " " +
                        childs.get(groupPosition).get(childPosition).off;

                Intent intent = new Intent();
                intent.setClass(MyAlarmActivity.this, MyAlarmEditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("alarm", str);
                bundle.putString("status", "ModifyAlarm");
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
//                Utils.moveTo(MyAlarmActivity.this, MyAlarmEditActivity.class);
                return true;
            }
        });
        return view;
    }


    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    class AlarmAdapter extends BaseExpandableListAdapter {

        @Override
        public int getGroupCount() {
            return groups.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return childs.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groups.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childs.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            Logger.e("wangzhijun", "" + groupPosition);
            final TempGroup item = (TempGroup) getGroup(groupPosition);

            convertView = LayoutInflater.from(MyAlarmActivity.this).inflate(R.layout.adapter_group_alarm, parent, false);

            TextView title = (TextView) convertView.findViewById(R.id.title);
            title.setText(groups.get(groupPosition).title);

            ImageView plus = (ImageView) convertView.findViewById(R.id.plus);
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(MyAlarmActivity.this, MyAlarmEditActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("title", groups.get(groupPosition).title);
                    bundle.putString("status", "AddAlarm");
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 0);
//                    Utils.moveTo(MyAlarmActivity.this, MyAlarmEditActivity.class);
                }
            });
            if (item.expanded) {
//                listView.expandGroup(groupPosition);
            } else {
//                listView.collapseGroup(groupPosition);
            }
            convertView.findViewById(R.id.down).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.expanded = !item.expanded;
                    AlarmAdapter.this.notifyDataSetChanged();
                }
            });
            convertView.setTag(R.id.group_item, groupPosition);
            convertView.setTag(R.id.child_item, -1);
            return convertView;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(MyAlarmActivity.this).inflate(R.layout.adapter_child_alarm, parent, false);

            TextView time = (TextView) convertView.findViewById(R.id.time);//时间

            String[] s1 = childs.get(groupPosition).get(childPosition).time.split(":");// 以":"为分隔符，截取上面的字符串。结果为三段
            s1[0] = Utils.timeFormat(s1[0]);
            s1[1] = Utils.timeFormat(s1[1]);

            time.setText(s1[0] + ":" + s1[1]);

            TextView tip = (TextView) convertView.findViewById(R.id.tip);
            tip.setText(childs.get(groupPosition).get(childPosition).subheading);


//            ImageView imageView = (ImageView)convertView.findViewById(R.id.item_img);
//            TextView title = (TextView)convertView.findViewById(R.id.title);
//            TextView price = (TextView)convertView.findViewById(R.id.price);
//            TextView score = (TextView)convertView.findViewById(R.id.score);
//            TextView score_label = (TextView)convertView.findViewById(R.id.score_label);
//            TextView count = (TextView)convertView.findViewById(R.id.count);
//            TextView coupon = (TextView)convertView.findViewById(R.id.coupon);
            RelativeLayout rlDel = (RelativeLayout) convertView.findViewById(R.id.rl_del);
            ImageView arrow = (ImageView) convertView.findViewById(R.id.arrow_right);

            final SwitchView switchView = (SwitchView) convertView.findViewById(R.id.switchView);

            if ("1".equals(childs.get(groupPosition).get(childPosition).off)) {

                switchView.setChecked(true);

            } else {

                switchView.setChecked(false);

            }

            switchView.setOnCheckedChangeListener(new SwitchView.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(View view, boolean isChecked) {

                    id = childs.get(groupPosition).get(childPosition)._id;

                    String str = "";
                    str += childs.get(groupPosition).get(childPosition).time + " " +
                            childs.get(groupPosition).get(childPosition).mon + " " +
                            childs.get(groupPosition).get(childPosition).tue + " " +
                            childs.get(groupPosition).get(childPosition).wed + " " +
                            childs.get(groupPosition).get(childPosition).thu + " " +
                            childs.get(groupPosition).get(childPosition).fri + " " +
                            childs.get(groupPosition).get(childPosition).sat + " " +
                            childs.get(groupPosition).get(childPosition).sun + " " +
                            childs.get(groupPosition).get(childPosition).title + " " +
                            childs.get(groupPosition).get(childPosition).subheading + " ";

                    //清空集合数据
                    list.clear();
                    medicineAlarms.clear();
                    mealAlarms.clear();

                    if (isChecked) {
                        Logger.d(tag, "选中");
                        str += "1";
                        updateAlarm(str);
                    } else {
                        Logger.d(tag, "未选中");
                        str += "0";
                        updateAlarm(str);
                    }
                    //查询闹钟数据
                    query();
                    adapter.notifyDataSetChanged();
                    mAdapter.notifyDataSetChanged();
                }
            });

            if (edit) {
                rlDel.setVisibility(View.VISIBLE);
                arrow.setVisibility(View.VISIBLE);
                switchView.setVisibility(View.GONE);
            } else {
                rlDel.setVisibility(View.GONE);
                arrow.setVisibility(View.GONE);
                switchView.setVisibility(View.VISIBLE);
            }
            rlDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    delete(childs.get(groupPosition).get(childPosition)._id);
                    childs.get(groupPosition).remove(childPosition);
                    mAdapter.notifyDataSetChanged();
                }
            });

            convertView.setTag(R.id.group_item, groupPosition);
            convertView.setTag(R.id.child_item, childPosition);
            return convertView;
        }


        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }


    class TempGroup {
        String title;
        boolean expanded = true;

        public TempGroup(String title) {
            this.title = title;
        }
    }

    class TempChild {
        String time;
        String title;

        public TempChild(String time, String title) {
            this.time = time;
            this.title = title;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 100:
                list.clear();

                medicineAlarms.clear();
                mealAlarms.clear();

                query();

                adapter.notifyDataSetChanged();
                mAdapter.notifyDataSetChanged();
                break;
            default:
                break;

        }
    }

    /**
     * 查询闹钟
     */
    private void query() {
        mgr = new DBManager(this); //初始化数据库
        List<Alarm> alarms = mgr.query();

        for (Alarm alarm : alarms) {
            if (getResources().getString(R.string.message_medicineAlarms).equals(alarm.title)) {//保存订餐提醒

                Alarm a = new Alarm();
                a = alarm;
                mealAlarms.add(a);

            } else {//保存药定时提醒

                Alarm b = new Alarm();
                b = alarm;
                medicineAlarms.add(b);

            }
        }

        //闹钟排序
        sortAlarm(mealAlarms);
        sortAlarm(medicineAlarms);


        for (Alarm alarm : alarms) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id", String.valueOf(alarm._id));
            map.put("time", alarm.time);
            map.put("mon", alarm.mon);
            map.put("tue", alarm.tue);
            map.put("wed", alarm.wed);
            map.put("thu", alarm.thu);
            map.put("fri", alarm.fri);
            map.put("sat", alarm.sat);
            map.put("sun", alarm.sun);
            map.put("title", alarm._id + " " + alarm.time + " " + alarm.mon + " " + alarm.tue + " " + alarm.wed + " " + alarm.thu + " " + alarm.fri + " " + alarm.sat + " " + alarm.sun + " " + alarm.title + " "
                    + alarm.subheading + " " + alarm.off);
            map.put("subheading", alarm.subheading);
            map.put("off", alarm.off);
            list.add(map);
            Logger.d(tag, alarm._id + " " + alarm.time + " " + alarm.mon + " " + alarm.tue + " " + alarm.wed + " " + alarm.thu + " " + alarm.fri + " " + alarm.sat + " " + alarm.sun + " " + alarm.title
                    + " " + alarm.subheading + " " + alarm.off);
        }

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

//    /* 格式化字符串(7:3->07:03) */
//    private String format(String x) {
//        String s = "" + x;
//        if (s.length() == 1)
//            s = "0" + s;
//        return s;
//    }

    /**
     * 删除闹钟
     */
    private void delete(int id) {
        Alarm alarm = new Alarm();
        alarm._id = id;
        mgr.deletePerson(alarm);
        // 准备闹钟
        prepareAlarm();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mgr.closeDB();
    }
}
