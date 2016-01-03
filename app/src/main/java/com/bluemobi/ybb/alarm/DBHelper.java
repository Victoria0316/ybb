package com.bluemobi.ybb.alarm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
	
	private static final String DATABASE_NAME = "alarm.db";  
    private static final int DATABASE_VERSION = 1; 
	
    public DBHelper(Context context) {  
        //CursorFactory设置为null,使用默认值  
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }  
    
    
  //数据库第一次被创建时onCreate会被调用  
	@Override
	public void onCreate(SQLiteDatabase db) { 
		// TODO 自动生成的方法存根
		
		// 添加闹钟 ： 默认 on-off == 1
		// time  mon tue wed thu fri sat sun    title        subheading      off
		// 10:10  1   1   0   0   0   0   0        药定时提醒             早餐前吃过敏药             1
		
		  db.execSQL("CREATE TABLE IF NOT EXISTS alarm" +  
	                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, time CHAR, " +
	                "mon CHAR, tue CHAR, wed CHAR, thu CHAR, fri CHAR, sat CHAR, sun CHAR, " +
	                "title TEXT, subheading TEXT, off CHAR )");  
	}

	//如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO 自动生成的方法存根
//		db.execSQL("ALTER TABLE alarm ADD COLUMN other STRING");  
		
	}

}
