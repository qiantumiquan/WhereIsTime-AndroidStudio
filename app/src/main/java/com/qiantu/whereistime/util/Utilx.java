package com.qiantu.whereistime.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

import com.qiantu.whereistime.R;

public class Utilx {
    /**
     * 完全退出app，关闭所有的Activity和Service。
     * 原理：每个Activity和Service都注册了“退出广播”；
     * @param context
     */
    public static void exitApp(Context context) {
        //发送广播，停止service
        Intent intent = new Intent();
        intent.setAction(context.getString(R.string.action_exit));
        context.sendBroadcast(intent);
    }

	/**
	 * 判断是否已连接网路
	 * 需要权限：
	 * android.permission.ACCESS_NETWORK_STATE
	 * android.permission.ACCESS_WIFI_STATE
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager connectivityManager = 
				(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(connectivityManager != null) {
			NetworkInfo[] infos = connectivityManager.getAllNetworkInfo();
			if(infos != null) {
				for(NetworkInfo info : infos) {
					if(info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					} else {
						//启动设置网络界面
						context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 判断用户是否是第一次打开app
	 * @param act
	 * @return
	 */
	public static boolean isFirstOpenApp(Activity act) {
		//获取当前版本号
		int versionCode = 0;
		try {
			versionCode = act.getPackageManager()
					.getPackageInfo(act.getPackageName(), 0)
					.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
		String valName = "isFirstOpenApp_" + versionCode;//后面跟着版本号
		SharedPreferences sp = act.getSharedPreferences("isFirstOpenApp", Activity.MODE_PRIVATE);    
		boolean isFirstOpenApp = sp.getBoolean(valName, true);    
		if(isFirstOpenApp) {    
		    SharedPreferences.Editor editor = sp.edit();    
		    editor.putBoolean(valName, false);    
		    editor.commit();    
		        
		    return true;  
		}
		return false;
	}
}
