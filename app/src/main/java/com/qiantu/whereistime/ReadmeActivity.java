package com.qiantu.whereistime;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.qiantu.whereistime.view.PageView;

public class ReadmeActivity extends Activity {
	private LayoutInflater mInflater;
	private PageView mPageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_readme);

		mInflater = LayoutInflater.from(this);
		mPageView = (PageView) findViewById(R.id.pageview);

		View view = mInflater.inflate(R.layout.view_readme, null);
		TextView text_readme = (TextView) view.findViewById(R.id.text_readme);
		String str = "亲~\n是不是无聊的时候拿出手机，\n打开屏幕\n向左一滑\n向右一滑\n锁屏~\n时间就没了";
		text_readme.setText(str);
		mPageView.addPage(view);

		View view2 = mInflater.inflate(R.layout.view_readme, null);
		TextView text_readme2 = (TextView) view2.findViewById(R.id.text_readme);
		String str2 = "亲~\n知道你的时间去哪了吗\n这里帮你记录着\n时间到底花在了什么地方~";
		text_readme2.setText(str2);
		mPageView.addPage(view2);
		
		Button button = (Button) this.findViewById(R.id.button_start);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
