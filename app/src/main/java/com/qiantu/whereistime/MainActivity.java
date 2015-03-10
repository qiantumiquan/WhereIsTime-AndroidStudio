package com.qiantu.whereistime;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.qiantu.whereistime.model.Day;
import com.qiantu.whereistime.service.DataService;
import com.qiantu.whereistime.util.DBUtilx;
import com.qiantu.whereistime.util.Utilx;
import com.qiantu.whereistime.util.x;
import com.qiantu.whereistime.view.LinearLayoutPage;
import com.qiantu.whereistime.view.ZoomOutPageTransformer;

public class MainActivity extends BaseActivity {
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.activity_main);

        //初始化数据库
        DBUtilx.init(this);

        //初始化组件
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        //添加滑动动画
        mViewPager.setPageTransformer(false, new ZoomOutPageTransformer());
        mViewPager.setAdapter(new MyPagerAdapter());

        //启动后台线程
        startService(new Intent(this, DeamonService.class));
        startService(new Intent(this, BackService.class));

        // 判断是不是第一次打开
        if (Utilx.isFirstOpenApp(this)) {
            startActivity(new Intent(this, ReadmeActivity.class));
        }

        ///////////////////////////////////////////////////////////
//        startActivity(new Intent(this, SettingActivity.class));
        ///////////////////////////////////////////////////////////
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //点击菜单键打开设置activity
        if(keyCode == KeyEvent.KEYCODE_MENU) {
            startActivity(new Intent(this, SettingActivity.class));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        x.xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx("main_activity_exit");
        super.onDestroy();
    }

    /**
     * 菜单的点击事件，绑定在了xml里面
     * @param v
     */
    public void onMenuButtonClick(View v) {
        startActivity(new Intent(this, SettingActivity.class));
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
            pageCount = (int)DataService.getDaysCount();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //获取数据
            Day day = DataService.getDay(position);

            //还没有数据
            if (day == null) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "尽情使用吧亲~", Toast.LENGTH_LONG).show();
                    }
                });
            }

            //获取view，并绑定跳转事件
            LinearLayoutPage page = new LinearLayoutPage();
            View view = page.getView(MainActivity.this, day);

            //判断是否是启动状态（初始化第一第二页）
            if (!isInit) {
                initCount++;
                if (initCount == 2) isInit = true;
                container.addView(view);
                currentPage = 0;
            }
            //已经初始化了。现在是用户主动滑动状态，要判断是添加在前面还是后面
            else {
                if (position > currentPage) {
                    container.addView(view);
                    currentPage++;
                }
                else {
                    container.addView(view, 0);
                    currentPage--;
                }
            }

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















