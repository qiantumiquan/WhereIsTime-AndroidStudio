package com.qiantu.whereistime;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SettingDialog extends Activity {
	private Button dialog_setting_exitsystem;
	private Button dialog_setting_cleardata;
	private Button dialog_setting_apphelp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_setting);
		
		//设置按钮按下效果
		LinearLayout layout_setting = (LinearLayout) this.findViewById(R.id.layout_setting);
		layout_setting.setAlpha(0.7f);
		
		//应用说明
		dialog_setting_apphelp = (Button) this.findViewById(R.id.dialog_setting_apphelp);
		dialog_setting_apphelp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(SettingDialog.this)
					.setTitle("说明")
					.setMessage("此应用会让你知道你花在手机的时间分别用在了什么软件上面。主屏幕按照使用时间排列，点击进去可以看到比例。")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					})
					.show();
			}
		});
		
		//退出程序
		dialog_setting_exitsystem = (Button) this.findViewById(R.id.dialog_setting_exitsystem);
		dialog_setting_exitsystem.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction(getString(R.string.action_exit));
				sendBroadcast(intent);
				finish();
			}
		});
		
		//清除所有记录
		dialog_setting_cleardata = (Button) this.findViewById(R.id.dialog_setting_cleardata);
		dialog_setting_cleardata.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(SettingDialog.this)
					.setTitle("提示")
					.setMessage("确定要清除所有记录吗？")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent();
							intent.setAction(getString(R.string.action_clean_dbinfo));
							sendBroadcast(intent);
							finish();
						}
					})
					.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
						}
					})
					.show();
			}
		});
		
		//点击空白地方，activity消失
		RelativeLayout dialog_except = (RelativeLayout) this.findViewById(R.id.dialog_except);
		dialog_except.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}
	
}













