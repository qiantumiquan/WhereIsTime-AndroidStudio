package com.qiantu.whereistime.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtilx {
	/**
	 * 删除一个文件或者文件夹(包括文件夹底下的所有文件)
	 * @param realPath
	 */
	public static void deleteDirOrFile(String realPath) {
		// 参数为null
		if (realPath == null) {
			return;
		}

		File file = new File(realPath);

		// 目录不存在
		if (!file.exists()) {
			return;
		}

		// 不是目录，直接删除
		if (!file.isDirectory()) {
			file.delete();
			return;
		}
		// 是目录,递归删除地下所有文件
		for (String name : file.list()) {
			File f = new File(file, name);
			deleteDirOrFile(f.getPath());
		}
	}
	
	/** 如果目录不存在，则创建一个目录；如果已存在，则忽略
	 *  需要加入权限:<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
	 * @param realPath
	 * @return
	 */
	public static File createDir(String realPath, boolean isCover) {
		File dir = new File(realPath);
		if(dir.exists()) {
			if(isCover) {
				deleteDirOrFile(realPath);
				dir.mkdir();
			}
		} else {
			dir.mkdir();
		}
		return dir;
	}
	
	/** 判断某目录是否已经存在
	 * @param realPath 真实物理路径
	 * @return
	 */
	public static boolean isExistsDir(String realPath) {
		File dir = new File(realPath);
		if(dir.exists() && dir.isDirectory()) {
			return true;
		}
		return false;
	}
	
	 /** 保存一个文件到手机中，比如gif图片
	 * @param realPath 文件的真实物理路径
	 * @param fileIs 文件流
	 * @param isCover 如果文件已存在,是否覆盖
	 * @return
	 */
	public static boolean saveFile(String realPath, InputStream fileIs, boolean isCover) {
		try {
			File file = new File(realPath);
			//存在且不覆盖
			if(file.exists() && !isCover) {
				return true;
			}
			//存在,但是要覆盖
			if(file.exists() && isCover) {
				file.delete();
			}
			//不存在
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(inputStreamToByteArray(fileIs));
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/** 把InputStream转化为byte
	 * @return
	 */
	public static byte[] inputStreamToByteArray(final InputStream is) {
		byte[] result = null;
        try {
        	int len = is.available();
        	result = new byte[len];
        	is.read(result);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}  
        return result; 
	}
	
	public static String inputStreamToString(final InputStream is) {
		byte[] bs = inputStreamToByteArray(is);
		return new String(bs);
	}
}
