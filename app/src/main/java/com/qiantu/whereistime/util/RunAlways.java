package com.qiantu.whereistime.util;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * 这是一个封装好的类，
 * 要实现不被系统 kill 掉的service，
 * 只要在service中new一个对象，然后start方法，最后在onDestory中close就行
 * 注意：要加入权限 <uses-permission android:name="android.permission.GET_TASKS"/>
 * @author LinZhiquan
 *
 */
public class RunAlways {
	private Context context;
	private BroadcastReceiver myReceiver;
	/** 当service关闭的时候停止线程 */
	private boolean flag_runnable = true;

	public RunAlways(Context context) {
		this.context = context;

		myReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	/**
	 * 开启线程
	 */
	public void start() {
		// 注册广播接收器，当当前运行的程序不是自身，则让service运行一下
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.example.runalways.notSelf");
		context.registerReceiver(myReceiver, filter);
		
		// 开启线程，隔一段时间就判断一下当前运行的程序是不是自身，如果不是，则发送广播
		new Thread(new MyRunnable()).start();
	}

	/**
	 * 关闭，取消注册接收器以及停止线程
	 */
	public void close() {
		// 记得取消广播注册，和关闭线程
		context.unregisterReceiver(myReceiver);
		this.flag_runnable = false;
	}

	private class MyRunnable implements Runnable {
		@Override
		public void run() {
			while (flag_runnable) {
				try {
					Thread.sleep(3000);
					
					// 获取当前运行的Activity
					ActivityManager am = (ActivityManager) context
							.getSystemService(Context.ACTIVITY_SERVICE);
					ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
					String pkgName = cn.getPackageName();

					if (!pkgName.equals(context.getPackageName())) {
						Intent intent = new Intent();
						intent.setAction("com.example.runalways.notSelf");
						context.sendBroadcast(intent);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
