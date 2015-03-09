package com.qiantu.whereistime;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.qiantu.whereistime.model.AppInfo;
import com.qiantu.whereistime.model.Day;
import com.qiantu.whereistime.service.BackService;
import com.qiantu.whereistime.service.DeamonService;
import com.qiantu.whereistime.util.AppUtil;
import com.qiantu.whereistime.util.DBUtilx;
import com.qiantu.whereistime.util.StringUtil;
import com.qiantu.whereistime.util.Utilx;
import com.qiantu.whereistime.util.x;
import com.qiantu.whereistime.view.PageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LinZhiquan
 *
 */
public class MainActivity extends BaseActivity {
	private DbUtils db;
	
	/* 一系列的广播接收器 */
	private BroadcastReceiver mUpdateUIReceiver;//注册广播接收更新UI的命令;
	private BroadcastReceiver clearDatabaseReceiver;//注册广播删除数据库数据
	
	private List<TextView> list_text;
	private TextView text_date;
	
	private LayoutInflater mInflater;
	private PageView mPageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.activity_main);
		
		db = DBUtilx.getInstance(this);
		
		//初始化组件
		mInflater = LayoutInflater.from(this);
		mPageView = (PageView) findViewById(R.id.pageview);
		
		//设置titlebar的一些监听器
		super.setTitleBar();
		
		//注册一系列的广播接收器
		initBroadcase();
		
		startService(new Intent(this, DeamonService.class));
		startService(new Intent(this, BackService.class));
		
		// 判断是不是第一次打开
		if (Utilx.isFirstOpenApp(this)) {
			startActivity(new Intent(this, ReadmeActivity.class));
		}
	}
	
	@Override
	protected void onResume() {
		updateUI();
		super.onResume();
	}
	
	/**
	 * 注册一系列的广播接收器
	 */
	public void initBroadcase() {
		//注册广播接收更新UI的命令
		IntentFilter filter = new IntentFilter();
		filter.addAction(getString(R.string.action_update_ui));
		mUpdateUIReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				//注册广播接收更新UI的命令
				updateUI();
			}
		};
		this.registerReceiver(mUpdateUIReceiver, filter);
		
		//注册广播接收清除数据库信息的命令
		filter = new IntentFilter();
		filter.addAction(getString(R.string.action_clean_dbinfo));
		clearDatabaseReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				//注册广播接收清除数据库信息的命令
				clearDatabaseInfo();
			}
		};
		this.registerReceiver(clearDatabaseReceiver, filter);
	}

	public void unRegisterReceivers() {
		this.unregisterReceiver(mUpdateUIReceiver);
		this.unregisterReceiver(clearDatabaseReceiver);
	}
	
	@Override
	protected void onDestroy() {
		unRegisterReceivers();
		x.xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx("main_activity_exit");
        super.onDestroy();
	}

	public void updateUI() {
		mPageView.removeAllPages();
		
		List<Day> days = this.getDays(10);
		
		//当数据库没有数据的时候
		if(days.size() == 0) {
			LinearLayout view = (LinearLayout) mInflater.inflate(R.layout.view, null);
			mPageView.addPage(view);
			Toast.makeText(this, "亲~开始浏览其它应用吧，这里会记录着~", Toast.LENGTH_LONG).show();
			return;
		}
		
		//第一天是今天，第二天是昨天
		Day d = days.get(0);
		d.setDate(d.getDate() + "(今天)");
		days.remove(0);
		days.add(0, d);
		if(days.size() > 1) {
			Day d2 = days.get(1);
			d2.setDate(d2.getDate() + "(昨天)");
			days.remove(1);
			days.add(1, d2);
		}
		
		for(Day day : days) {
			//设定view
			LinearLayout view = (LinearLayout) mInflater.inflate(R.layout.view, null);
			//a代表大格，b代表中格，c代表小格，d代表最小
			TextView text_1_a = (TextView) view.findViewById(R.id.text_1_a);
			TextView text_2_b = (TextView) view.findViewById(R.id.text_2_b);
			TextView text_3_b = (TextView) view.findViewById(R.id.text_3_b);
			TextView text_4_c = (TextView) view.findViewById(R.id.text_4_c);
			TextView text_5_c = (TextView) view.findViewById(R.id.text_5_c);
			TextView text_6_c = (TextView) view.findViewById(R.id.text_6_c);
			TextView text_7_c = (TextView) view.findViewById(R.id.text_7_c);
			TextView text_8_d = (TextView) view.findViewById(R.id.text_8_d);
			TextView text_9_d = (TextView) view.findViewById(R.id.text_9_d);
			TextView text_10_d = (TextView) view.findViewById(R.id.text_10_d);
			TextView text_11_d = (TextView) view.findViewById(R.id.text_11_d);
			TextView text_12_d = (TextView) view.findViewById(R.id.text_12_d);
			TextView text_13_d = (TextView) view.findViewById(R.id.text_13_d);
			TextView text_14_d = (TextView) view.findViewById(R.id.text_14_d);
			TextView text_15_d = (TextView) view.findViewById(R.id.text_15_d);
			list_text = new ArrayList<TextView>();
			list_text.add(text_1_a);
			list_text.add(text_2_b);
			list_text.add(text_3_b);
			list_text.add(text_4_c);
			list_text.add(text_5_c);
			list_text.add(text_6_c);
			list_text.add(text_7_c);
			list_text.add(text_8_d);
			list_text.add(text_9_d);
			list_text.add(text_10_d);
			list_text.add(text_11_d);
			list_text.add(text_12_d);
			list_text.add(text_13_d);
			list_text.add(text_14_d);
			list_text.add(text_15_d);
			
			text_date = (TextView) view.findViewById(R.id.text_date);
			
			//数据从数据库中取得，根据usertime排列
			List<AppInfo> list_app = this.getAppInfos(day.getId(), 15);
			
			//设置日期
			text_date.setText(day.getDate());
			
			//总的使用时间
			double sum = 0;
			for(AppInfo app : list_app) {
				sum += app.getUseTime();
			}
			final double sumTime = sum;//用于传递到下一个activity
			
			//把所有text清楚
			for(TextView text : list_text) {
				text.setText("");
			}
			
			int index = 0;
			for(TextView text : list_text) {
				if(list_app.size() == index) break;
				final AppInfo app = list_app.get(index);
				
				String str = "";
				if(index < 7) {
					str = app.getName()+"\n"+ AppUtil.s2m(app.getUseTime())
							+ StringUtil.subDouble(app.getUseTime() / sum * 100)+"%";
				} else {
					str = app.getName()+"\n"
							+ StringUtil.subDouble(app.getUseTime() / sum * 100)+"%";
				}
				
				text.setText(str);
				text.setLongClickable(true);//这个是必须的
				text.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(MainActivity.this, AppInfoActivity.class);
						intent.putExtra("app", app);
						intent.putExtra("sumTime", sumTime);
						startActivity(intent);
					}
				});
				index++;
			}
			
			mPageView.addPage(view);
		}
		
	}
	
	/** 监听用户点击TextView
	 * @author LinZhiquan
	 */
	class AppInfoListener implements OnClickListener {
		private AppInfo app;
		public AppInfoListener(AppInfo app) {
			super();
			this.app = app;
		}
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, AppInfoActivity.class);
			intent.putExtra("app", app);
			startActivity(intent);
		}
	}

	/**
	 * 从数据库中读取AppInfo表中的所有记录
	 * @param dayId
	 * @param n
	 * @return
	 */
	public List<AppInfo> getAppInfos(int dayId, int n) {
		List<AppInfo> appInfos = null;
		try {
			appInfos = db.findAll(
					Selector.from(AppInfo.class)
						.where("day_id", "=", dayId)
						.orderBy("useTime", true)
						.limit(n));
		} catch (DbException e) {
			e.printStackTrace();
		}
		return appInfos;
	}
	
	/**
	 * 从数据库中读取前n天
	 * @param n
	 * @return
	 */
	public List<Day> getDays(int n) {
		List<Day> days = null;
		try {
			days = db.findAll(Selector.from(Day.class).orderBy("id", true).limit(n));
		} catch (DbException e) {
			e.printStackTrace();
		}
		return days;
	}
	
	/**
	 * 清楚数据库已经保存的信息
	 */
	public void clearDatabaseInfo() {
		try {
			db.deleteAll(AppInfo.class);
			this.updateUI();
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
}















