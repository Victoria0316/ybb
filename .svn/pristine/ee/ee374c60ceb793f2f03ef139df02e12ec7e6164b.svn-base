package com.bluemobi.ybb.app;

import android.content.Context;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.DbUtils.DbUpgradeListener;

public class DbManager
{   
    
    private DbUtils.DaoConfig conf;

    public static DbManager instance;

    private Context context;

    private static final int DB_VERSION = 1;

    private static String DB_NAME = "ybb.db";

    public static DbManager getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new DbManager(context);
        }
        return instance;
    }

    private DbManager(Context context)
    {
        this.context = context;
        conf = new DbUtils.DaoConfig(context);
        conf.setDbName(DB_NAME);
        conf.setDbVersion(DB_VERSION);
        conf.setDbUpgradeListener(new DbUpgradeListener()
        {

            @Override
            public void onUpgrade(DbUtils db, int oldVersion, int newVersion)
            {
                if (2 > oldVersion)
                {// 2要做的事情
                    db.getDatabase().execSQL(
                            " ALTER TABLE child ADD realName TEXT");
                    //					try {
                    //						db.createTableIfNotExist(Menber.class);
                    //					} catch (DbException e) {
                    //						// TODO Auto-generated catch block
                    //						e.printStackTrace();
                    //					}
                }
                if (3 > oldVersion)
                {

                }
            }
        });
    }

    public DbUtils getDefaultDbUtils()
    {
        return DbUtils.create(conf);
    }
}
