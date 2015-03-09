package com.qiantu.whereistime.util;

/**
 * 监听某个目录的文件变化。
 * 比如监听某个保存图片的目录，当有增加图片，则处理
 */
public class PathFilter {
	public void filter() {
		/*
		 * 获取文件夹的最后修改时间，并保存到数据库
		 * 
		 * 开启service，循环检测
		 * 获取文件夹的最后修改时间
		 * 判断时间有没有变化
		 * if 	有变化
		 * 		
		 * 		注意：只有是新建的文件的时间才会和文件夹的时间是一样的。
		 * 
		 * else 没变化
		 * 		继续循环
		 * 
		 * */
	}
}
