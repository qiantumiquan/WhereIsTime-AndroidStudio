package com.qiantu.whereistime.service;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.qiantu.whereistime.R;
import com.qiantu.whereistime.util.x;

/**
 * 守护服务。
 * 将会一直运行于系统的后台，保持app的运行状态，不轻易让系统回收。
 * 
 * 原理：每隔多少秒检查一次当前运行的activity是否是当前应用，
 * 		如果是，则app正在运行，则无需操作；
 * 		如果不是，则app没有在运行，则后台运行app一下，避免被系统回收。
 * 
 * 注意：要加入权限 <uses-permission android:name="android.permission.GET_TASKS"/>
 */
public class DeamonService extends BaseService implements Runnable {
	private final int DELAY = 3000;//每隔多少秒检查一次当前运行的程序
	private final int RUN = 1000;//如果不是当前程序，则运行多少秒
	
	private ActivityManager mActManager;
	private BroadcastReceiver mReceiver;
	private Intent mIntent;
	
	private boolean flag = true;

	@Override
	public void onCreate() {
		super.onCreate();
		
		mActManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		mIntent = new Intent(getString(R.string.action_deamon_service));
		
		initBroadcast();
		startThread();
	}
	
	private void initBroadcast() {
		//定义广播接收器：每当收到广播，运行一秒
		mReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				try {
					Thread.sleep(RUN);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		// 注册广播接收器，当当前运行的程序不是自身，则让service运行一下
		IntentFilter filter = new IntentFilter();
		filter.addAction(getString(R.string.action_deamon_service));
		registerReceiver(mReceiver, filter);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		closeThread();
		x.xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx("deamon_service_exit");
    }

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	/**
	 * 开启线程
	 * 隔一段时间就判断一下当前运行的程序是不是自身，如果不是，则发送广播
	 */
	private void startThread() {
		new Thread(this).start();
	}

	/**
	 * 关闭，取消注册接收器以及停止线程
	 */
	public void closeThread() {
		// 记得取消广播注册，和关闭线程
		if(mReceiver != null) {
			unregisterReceiver(mReceiver);
		}
		flag = false;
	}

	@Override
	public void run() {
		while (flag) {
			try {
				Thread.sleep(DELAY);

				// 获取当前运行的Activity
				String pkgName = mActManager.getRunningAppProcesses().get(0).processName;

				if (!pkgName.equals(getPackageName())) {
					sendBroadcast(mIntent);
					continue;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
















