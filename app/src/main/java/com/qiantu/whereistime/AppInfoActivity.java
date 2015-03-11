package com.qiantu.whereistime;

import android.app.AlertDialog;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiantu.whereistime.model.AppInfo;
import com.qiantu.whereistime.util.Utilx;
import com.qiantu.whereistime.util.x;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

public class AppInfoActivity extends BaseActivity {
	private LinearLayout layout_appicon;
	private TextView text_appname;
	private TextView text_usetime;
	private TextView text_sumtime;
	private LinearLayout layout_chart;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.activity_appinfo);
		
		//初始化组件
		layout_appicon = (LinearLayout) this.findViewById(R.id.layout_activityappinfo_appicon);
		text_appname = (TextView) this.findViewById(R.id.text_activityinfo_appname);
		text_usetime = (TextView) this.findViewById(R.id.text_activityinfo_usetime);
		text_sumtime = (TextView) this.findViewById(R.id.text_activityinfo_sumtime);
		layout_chart = (LinearLayout) this.findViewById(R.id.layout_chart);
		
		//赋值
		double sumTime = this.getIntent().getDoubleExtra("sumTime", 0);
		AppInfo app = (AppInfo) this.getIntent().getSerializableExtra("app");
		text_appname.setText("应用名称："+app.getName());
		text_usetime.setText("使用时间："+ Utilx.s2m(app.getUseTime()));
		text_sumtime.setText(" 总时间："+Utilx.s2m(sumTime));
		text_appname.setOnClickListener(new TextOnClickListener(app.getName()));
		
		//设置icon
		String pkgName = app.getPkgName();
		try {
			Drawable icon = this.getPackageManager().getApplicationIcon(pkgName);
            layout_appicon.setBackground(icon);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
		//设置报表
		double[] values = new double[] {app.getUseTime(), sumTime};
        int[] colors = new int[] {Color.RED, Color.GREEN};
        
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setLabelsTextSize(30);
        renderer.setLegendTextSize(30);
        renderer.setDisplayValues(true);
        renderer.setStartAngle(45l);//开始的角度0为3点钟方向
        renderer.setMargins(new int[] { 10, 10, 10, 10 });
        for (int color : colors) {
          SimpleSeriesRenderer r = new SimpleSeriesRenderer();
          r.setColor(color);
          renderer.addSeriesRenderer(r);
        }
        
        SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
        r.setGradientEnabled(true);
        r.setGradientStart(0, Color.BLACK);
        r.setGradientStop(0, Color.RED);
        r.setHighlighted(true);
        
        CategorySeries series = new CategorySeries("Project budget");
        series.add(app.getName(), values[0]);
        series.add("其它应用", values[1]);
        
        GraphicalView view = ChartFactory.getPieChartView(this, series, renderer);
        layout_chart.addView(view);
	}

	private class TextOnClickListener implements OnClickListener {
		private String str;
		public TextOnClickListener(String str) {
			super();
			this.str = str;
		}
		@Override
		public void onClick(View v) {
			new AlertDialog.Builder(AppInfoActivity.this)
				.setMessage(str)
				.setPositiveButton("关闭", null)
				.show();
		}
	}
	
	@Override
	protected void onDestroy() {
		x.xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx("appinfo_activity_exit");
        super.onDestroy();
	}
}


















