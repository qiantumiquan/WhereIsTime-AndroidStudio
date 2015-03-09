package com.qiantu.whereistime;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 一个公共的Activity，包含有OrmLiteBaseActivity<DBHelper>
 * 目的是为了让每个Activity都有同一的menu
 *
 * 完全退出：这个基本activity注册了广播，收到广播则退出。然后所有的activity都继承此activity。
 */
//public class BaseActivity extends ActionBarActivity {
public class BaseActivity extends Activity {
	private BroadcastReceiver mExitReceiver;
	
	TextView text_title;
	LinearLayout layout_share;
	LinearLayout layout_signin;
	LinearLayout layout_setting;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initBroadcase();
	}
	
	private void initBroadcase() {
		//注册广播，用于退出程序
		IntentFilter filter = new IntentFilter();
		filter.addAction(getString(R.string.action_exit));
		mExitReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				finish();
			}
		};
		this.registerReceiver(mExitReceiver, filter);
	}
	
	/**
	 * protected:只有同一个包下的类才能访问
	 * 只能让子类执行此方法，因为子类才会有下面的那些id
	 */
	protected void setTitleBar() {
		text_title = (TextView) this.findViewById(R.id.text_title);
		layout_share = (LinearLayout) this.findViewById(R.id.layout_share);
		layout_setting = (LinearLayout) this.findViewById(R.id.layout_setting);
		
		//分享
		layout_share.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//获取bitmap格式图片，也就是截屏
				View view = getWindow().getDecorView();
				view.setDrawingCacheEnabled(true);
				view.buildDrawingCache();
				Bitmap bitmap = view.getDrawingCache();
				
				//生成图片名
				Date date = new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd=HH-mm-ss", Locale.US);
				String imageName = format.format(date) + ".png";
				
				//获取图片存储路径
				File dir = Environment.getExternalStorageDirectory();//获取跟目录
				File file = new File(dir, imageName);
				FileOutputStream fos = null;
		        try {
		        	file.createNewFile();
		        	fos = new FileOutputStream(file);
		        	
		        	//存储图片
		        	bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
		        	
		        	fos.flush();
		        	fos.close();
		        } catch (FileNotFoundException e) {
		                e.printStackTrace();
		        } catch (IOException e) {
					e.printStackTrace();
				}
				
				Intent intent = new Intent();
				intent.putExtra("imagePath", file.getPath());
				intent.setClass(BaseActivity.this, ShareDialog.class);
				startActivity(intent);
			}
		});
		
		//设置
		layout_setting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(BaseActivity.this, SettingDialog.class);
				startActivity(intent);
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		this.unregisterReceiver(mExitReceiver);
		super.onDestroy();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
        return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.menu_exit) {
			//发送广播，停止service
			Intent intent = new Intent();
			intent.setAction(getString(R.string.action_exit));
			this.sendBroadcast(intent);
		}
		return true;
	}
}













