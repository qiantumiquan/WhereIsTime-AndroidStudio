package com.qiantu.whereistime;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.qiantu.whereistime.view.ZoomOutPageTransformer;

public class ReadmeActivity extends Activity {
	private LayoutInflater mInflater;
    private ViewPager mViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.activity_readme);

        //初始化组件
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        //添加滑动动画
        mViewPager.setPageTransformer(false, new ZoomOutPageTransformer());
        mViewPager.setAdapter(new MyPagerAdapter());

		mInflater = LayoutInflater.from(this);

		Button button = (Button) this.findViewById(R.id.button_start);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

    /**
     * 内存中只保存有三个页面（初始的时候只有两个，0和1）。
     * 显示中间那个，内存中加载前后两个。
     */
    private class MyPagerAdapter extends PagerAdapter {
        private int pageCount = 1;
        private int currentPage = 0;

        //刚启动的时候会init2次，记录下来
        private boolean isInit;
        private int initCount;

        public MyPagerAdapter() {
            pageCount = 2;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = null;
            if(position == 0) {

                view = mInflater.inflate(R.layout.view_readme, null);
                TextView text_readme = (TextView) view.findViewById(R.id.text_readme);
                String str = "亲~\n是不是无聊的时候拿出手机，\n打开屏幕\n向左一滑\n向右一滑\n锁屏~\n时间就没了";
                text_readme.setText(str);
            } else if(position == 1) {
                view = mInflater.inflate(R.layout.view_readme, null);
                TextView text_readme2 = (TextView) view.findViewById(R.id.text_readme);
                String str2 = "亲~\n知道你的时间去哪了吗\n这里帮你记录着\n时间到底花在了什么地方~";
                text_readme2.setText(str2);
            }
            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        @Override
        public int getCount() {
            return pageCount;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
