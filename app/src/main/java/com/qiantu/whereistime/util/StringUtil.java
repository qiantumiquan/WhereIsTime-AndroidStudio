package com.qiantu.whereistime.util;

public class StringUtil {
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
}
