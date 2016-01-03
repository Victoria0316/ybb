package com.bluemobi.ybb.db.control;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

public class YbbDbUtils {

	private Context mContext;

	public static YbbDbUtils instance;

	private static DbUtils mDbUtils;

	private static String DB_NAME = "ybb_message.db";

	private static int DB_VERSION = 1;

	private YbbDbUtils(Context context) {
		mContext = context;
		DbUtils.DaoConfig conf = new DbUtils.DaoConfig(context);
		conf.setDbName(DB_NAME);
		// conf.setDbVersion(DB_VERSION);
		mDbUtils = DbUtils.create(conf);
	}

	public static YbbDbUtils getInstance(Context context) {
		if (instance == null) {
			instance = new YbbDbUtils(context);
		}
		return instance;
	};

	public DbUtils getYbbDbUtils() {
		return mDbUtils;
	}

//	public List<AreaModel> getProvices() {
//		List<AreaModel> provinces = new ArrayList<AreaModel>();
//		List<AreaModel> list1 = null;// 直辖市
//		List<AreaModel> list2 = null;// 直辖市
//		try {
//			list1 = mDbUtils.findAll(Selector.from(AreaModel.class)
//					.where("divisionType", "=", "1").and("id", "like", "86%")
//					.orderBy("divisionJp", false));
//			list2 = mDbUtils.findAll(Selector.from(AreaModel.class)
//					.where("divisionType", "=", "1").and("id", "not like", "86%")
//					.orderBy("divisionJp", false));
//		} catch (DbException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if(list1 != null){
//			provinces.addAll(list1);
//			provinces.addAll(list2);
//		}
//		return provinces;
//	}
//
//	public List<AreaModel> getChildByPid(String pid) {
//		List<AreaModel> list = null;
//		try {
//			list = mDbUtils.findAll(Selector.from(AreaModel.class)
//					.where("pid", "=", pid).orderBy("divisionJp", false));
//		} catch (DbException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return list;
//	}
//
//
//	public static void save(Context context, TestEntity entity) {
//		DbUtils dbUtils = DbManager.getInstance(context).getDefaultDbUtils();
//		try {
//			dbUtils.save(entity);
//		} catch (DbException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public String getCityCode(String city) {
//		String code = "";
//		List<AreaModel> list = null;
//		try {
//			list = mDbUtils.findAll(Selector.from(AreaModel.class)
//					.where("divisionName", "like ", "%" + city + "%")
//					.and("divisionType", "=", "2"));
//		} catch (DbException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if (list != null && list.size() != 0) {
//			code = list.get(0).getId();
//		}
//		return code;
//	}
//
//	public List<AreaModel> getCitys() {
//		List<AreaModel> list = null;
//		try {
//			list = mDbUtils.findAll(Selector.from(AreaModel.class)
//					.where("divisionType", "=", "2").orderBy("divisionJp"));
//		} catch (DbException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return list;
//	}
}
