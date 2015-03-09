package com.qiantu.whereistime.util;

import android.content.Context;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.DbUtils.DbUpgradeListener;
import com.lidroid.xutils.exception.DbException;
import com.qiantu.whereistime.model.AppInfo;
import com.qiantu.whereistime.model.Day;

public class DBUtilx {
	private static DbUtils instance;

    /**
     * 在第一个Activity的时候调用此方法，new出实例。
     * 可以多次调用。
     */
    public static void init(Context context) {
        if(instance != null) return ;
        if(context == null) return ;
        synchronized (DBUtilx.class) {
            if(instance == null) {
                instance = DbUtils.create(context, "whereistime", 2, new MyDbUpdateListener());
                try {
                    //新建表
                    instance.createTableIfNotExist(Day.class);
                    instance.createTableIfNotExist(AppInfo.class);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 调用此方法前必须先调用init方法。
     * （建议在第一个Activity的onCreate中调用init()）
     */
	public static DbUtils getInstance() {
        return instance;
	}
	
	/**
	 * 数据库版本更新的时候执行，结合AFinalDB使用
	 */
	private static class MyDbUpdateListener implements DbUpgradeListener {
		//版本1：创建表的语句：CREATE TABLE IF NOT EXISTS person ( "id" INTEGER PRIMARY KEY AUTOINCREMENT,"password","name" )
		
//		private void version3(DbUtils db) {
			//版本3：增加字段love
//			String sql = "ALTER TABLE person add age default 55;";
//			db.execSQL(sql);
//		}
		
		@Override
		public void onUpgrade(DbUtils db, int oldVersion, int newVersion) {
			//第二个版本有ormlite换成xUtils，什么都不执行。
			if(newVersion == 2) {
				return;
			}
			
//			int num = newVersion - oldVersion;
//			if(num > 0) {
//				//执行最高版本，下面版本递减执行
//				version3(db);
//			}
//			if(num > 1) {
//				version2(db);
//			}
		}
	}
}




















