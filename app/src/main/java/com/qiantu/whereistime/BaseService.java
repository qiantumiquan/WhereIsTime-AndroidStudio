package com.qiantu.whereistime;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class BaseService extends Service {
	private BroadcastReceiver mExitReceiver;
	
	@Override
	public void onCreate() {
		//注册广播，用于退出程序
		IntentFilter filter = new IntentFilter();
		filter.addAction(getString(R.string.action_exit));
		mExitReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				stopSelf();
			}
		};
		registerReceiver(mExitReceiver, filter);
		
		super.onCreate();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onDestroy() {
		unregisterReceiver(mExitReceiver);
		super.onDestroy();
	}
}
