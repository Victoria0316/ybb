package com.bluemobi.ybb.alarm;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DBManager {
	private DBHelper helper;
	private SQLiteDatabase db;

	public DBManager(Context context) {
		helper = new DBHelper(context);
		// 因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
		// 所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
		db = helper.getWritableDatabase();
	}

	/**
	 * add alarms
	 * 
	 * @param persons
	 * 
	 *  添加闹钟 ： 默认 on-off == 1
	 *  time  mon tue wed thu fri sat sun    title        subheading      off
	 *	10:10  1   1   0   0   0   0   0        药定时提醒             早餐前吃过敏药              1
	 */
	public void add(List<Alarm> alarms) {
		db.beginTransaction(); // 开始事务
		try {
			for (Alarm alarm : alarms) {
				db.execSQL("INSERT INTO alarm VALUES(null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", 
						new Object[] { alarm.time, alarm.mon, alarm.tue, alarm.wed, alarm.thu,alarm.fri,alarm.sat,alarm.sun,alarm.title,alarm.subheading,alarm.off});
			}
			db.setTransactionSuccessful(); // 设置事务成功完成
		} finally {
			db.endTransaction(); // 结束事务
		}
	}

	/**
	 * 更新闹钟
	 * @param alarm
	 */
	public void updateAge(Alarm alarm) {
		ContentValues cv = new ContentValues();
		cv.put("time", alarm.time);
		cv.put("mon", alarm.mon);
		cv.put("tue", alarm.tue);
		cv.put("wed", alarm.wed);
		cv.put("thu", alarm.thu);
		cv.put("fri", alarm.fri);
		cv.put("sat", alarm.sat);
		cv.put("sun", alarm.sun);
		cv.put("title", alarm.title);
		cv.put("subheading", alarm.subheading);
		cv.put("off", alarm.off);
		
		db.update("alarm", cv, "_id = ?", new String[] { String.valueOf(alarm._id) });
	}

	/**
	 * delete alarm
	 * 
	 * @param alarm
	 */
	public void deletePerson(Alarm alarm) {
		db.delete("alarm", "_id = ?", new String[] { String.valueOf(alarm._id) });
	}

	/**
	 * query all alarm, return list
	 * 
	 * @return List<Alarm>
	 */
	public List<Alarm> query() {
		ArrayList<Alarm> alarms = new ArrayList<Alarm>();
		Cursor c = queryTheCursor();
		while (c.moveToNext()) {
			Alarm alarm = new Alarm();
			alarm._id = c.getInt(c.getColumnIndex("_id"));
			alarm.time = c.getString(c.getColumnIndex("time"));
			alarm.mon = c.getString(c.getColumnIndex("mon"));
			alarm.tue = c.getString(c.getColumnIndex("tue"));
			alarm.wed = c.getString(c.getColumnIndex("wed"));
			alarm.thu = c.getString(c.getColumnIndex("thu"));
			alarm.fri = c.getString(c.getColumnIndex("fri"));
			alarm.sat = c.getString(c.getColumnIndex("sat"));
			alarm.sun = c.getString(c.getColumnIndex("sun"));
			alarm.title = c.getString(c.getColumnIndex("title"));
			alarm.subheading = c.getString(c.getColumnIndex("subheading"));
			alarm.off = c.getString(c.getColumnIndex("off"));
			alarms.add(alarm);
		}
		c.close();
		return alarms;
	}

	/**
	 * query all persons, return cursor
	 * 
	 * @return Cursor
	 */
	public Cursor queryTheCursor() {
		Cursor c = db.rawQuery("SELECT * FROM alarm", null);
		return c;
	}

	/**
	 * close database
	 */
	public void closeDB() {
		db.close();
	}
}
