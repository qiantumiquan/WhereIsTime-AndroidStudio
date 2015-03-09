package com.qiantu.whereistime.util;


public class AppUtil {
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
}
