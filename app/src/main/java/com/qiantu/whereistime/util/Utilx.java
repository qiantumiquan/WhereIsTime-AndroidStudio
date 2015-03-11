package com.qiantu.whereistime.util;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;
import android.view.animation.Animation;

import com.qiantu.whereistime.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Utilx {
    /**
     * 放大、透明》缩小、不透明
     */
    public static void animatorButtonClick(View v) {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(v, View.SCALE_X, 1, 2);
        anim1.setRepeatMode(Animation.REVERSE);
        anim1.setRepeatCount(1);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(v, View.SCALE_Y, 1, 2);
        anim2.setRepeatMode(Animation.REVERSE);
        anim2.setRepeatCount(1);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(v, View.ALPHA, 1, 0);
        anim3.setRepeatMode(Animation.REVERSE);
        anim3.setRepeatCount(1);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(anim1, anim2, anim3);
        set.setDuration(300).start();
    }

    /**
     * 保存图片为PNG
     * @param fileToWrite 生成的图片
     */
    public static boolean savePNG(Bitmap bitmap, File fileToWrite) {
        try {
            FileOutputStream out = new FileOutputStream(fileToWrite);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                out.flush();
                out.close();
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 分享图片
     */
    public static void shareImage(Context context, File image) {
        Uri uri = Uri.fromFile(image);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/jpeg");
        context.startActivity(Intent.createChooser(intent, "分享"));
    }

    /**
     * 把以秒为单位的时间转换成以分为但闻的时间字符串
     * @param
     * @return
     */
    public static String s2m(double sTime) {
        int m = (int) (sTime / 60);
        int s = (int) (sTime % 60);
        String ms = m + "分";
        String ss = s + "秒";
        if(m == 0) {
            ms = "";
        }
        if(s == 0) {
            ss = "";
        }
        return ms + ss;
    }

    /**	把一个double类型的数字转换为保留4个字符的字符串
     * @param num
     * @return
     */
    public static String subDouble(double num) {
        String str = String.valueOf(num);
        if(str.length() < 4) {
            return str;
        } else {
            return str.substring(0, 4);
        }
    }

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

        x.xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx(versionCode);
		
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
