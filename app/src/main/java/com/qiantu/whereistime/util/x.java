package com.qiantu.whereistime.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class x {
	//基本类型
	public static void xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	public static void xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx(String msg) {
		Log.e("mq", "xxxxx  "+msg);
	}
	public static void xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx(int msg) {
		Log.e("mq", "xxxxx  "+msg);
	}
	public static void xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx(float msg) {
		Log.e("mq", "xxxxx  "+msg);
	}
	public static void xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx(double msg) {
		Log.e("mq", "xxxxx  "+msg);
	}
	public static void xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx(boolean msg) {
		Log.e("mq", "xxxxx  "+msg);
	}
	
	public static void xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx(int width, int height) {
		Log.e("mq", "xxxxx  "+width+"*"+height);
	}
	
	/**打印一个对象
	 * @param obj
	 */
	public static void xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx(Object obj) {
		//list
		if(obj instanceof List<?>) {
			for(Object o : (List<?>)obj) {
				Log.e("mq", o.toString());
			}
			return;
		}
		
		Log.e("mq", obj.toString());
	}
}










