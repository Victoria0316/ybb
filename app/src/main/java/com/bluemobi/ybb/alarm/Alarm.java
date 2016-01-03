package com.bluemobi.ybb.alarm;

public class Alarm {

	// 添加闹钟 ： 默认 on-off == 1
	// time  mon tue wed thu fri sat sun    title        subheading      off
	// 10:10  1   1   0   0   0   0   0        药定时提醒             早餐前吃过敏药              1

	public int _id;
	public String time;
	public String mon;
	public String tue;
	public String wed;
	public String thu;
	public String fri;
	public String sat;
	public String sun;
	public String title;
	public String subheading;
	public String off;

	public Alarm() {
	}

	public Alarm(String time,String mon,String tue,String wed,String thu,String fri,String sat,String sun,String title,String subheading,String off) {
		this.time = time;
		this.mon = mon;
		this.tue = tue;
		this.wed = wed;
		this.thu = thu;
		this.fri = fri;
		this.sat = sat;
		this.sun = sun;
		this.title = title;
		this.subheading = subheading;
		this.off = off;
	}

}
