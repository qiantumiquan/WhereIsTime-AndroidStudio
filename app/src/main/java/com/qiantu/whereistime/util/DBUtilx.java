package com.qiantu.whereistime.util;

import android.content.Context;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.DbUtils.DbUpgradeListener;
import com.lidroid.xutils.exception.DbException;
import com.qiantu.whereistime.model.AppInfo;
import com.qiantu.whereistime.model.Day;

public class DBUtilx {
	private static DbUtils instance;
	
	public static DbUtils getInstance(Context context) {
		if(instance != null) {
			return instance;
		}
		synchronized (DBUtilx.class) {
			if(instance == null) {
				instance = DbUtils.create(context, "whereistime", 2, new MyDbUpdateListener());
				try {
					instance.createTableIfNotExist(Day.class);
					instance.createTableIfNotExist(AppInfo.class);
				} catch (DbException e) {
					e.printStackTrace();
				}
			}
			return instance;
		}
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




















