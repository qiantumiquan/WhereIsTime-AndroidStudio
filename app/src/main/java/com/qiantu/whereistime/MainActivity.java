package com.qiantu.whereistime;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.qiantu.whereistime.model.Day;
import com.qiantu.whereistime.service.DataService;
import com.qiantu.whereistime.util.DBUtilx;
import com.qiantu.whereistime.util.Utilx;
import com.qiantu.whereistime.util.x;
import com.qiantu.whereistime.view.LinearLayoutPage;
import com.qiantu.whereistime.view.ZoomOutPageTransformer;

import java.io.File;
import java.util.Date;

public class MainActivity extends BaseActivity {
    private ViewPager mViewPager;
    private MyPagerAdapter mPagerAdapter;

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
        mPagerAdapter = new MyPagerAdapter();
        mViewPager.setAdapter(mPagerAdapter);

        //启动后台线程
        startService(new Intent(this, DeamonService.class));
        startService(new Intent(this, BackService.class));

        ///////////////////////////////////////////////////////////
//        startActivity(new Intent(this, SettingActivity.class));
        ///////////////////////////////////////////////////////////
    }

    @Override
    protected void onResume() {
        //在显示的时候刷新数据
        mPagerAdapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        x.xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx("main_activity_exit");
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //点击菜单键打开设置activity
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            startActivity(new Intent(this, SettingActivity.class));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 截图。如果传入的v == null，则截全屏
     */
    private Bitmap getBitmap(View v) {
        if (v == null) {
            v = getWindow().getDecorView();
        }
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        return v.getDrawingCache();
    }

    /**
     * 菜单的点击事件，绑定在了xml里面
     */
    public void onMenuButtonClick(View v) {
        Utilx.animatorButtonClick(v);//动画
        startActivity(new Intent(this, SettingActivity.class));
    }

    /**
     * 分享的点击事件，绑定在了xml里面
     */
    public void onShareButtonClick(View v) {
        Utilx.animatorButtonClick(v);//动画

        Bitmap bitmap = getBitmap(mViewPager);

        //生成图片名
        String fileName = "" + new Date().getTime() + ".png";

        //获取图片存储路径
        File dir = Environment.getExternalStorageDirectory();
        File file = new File(dir, fileName);
        boolean success = Utilx.savePNG(bitmap, file);
        bitmap.recycle();

        if (success) {
            Utilx.shareImage(this, file);
        }

        //删除图片
//        File file = new File(imagePath);
//        if(file.exists()) {
//            file.delete();
//        }
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
            pageCount = (int) DataService.getDaysCount();
            pageCount = pageCount == 0 ? 1 : pageCount;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //获取数据
            Day day = DataService.getDay(position);

            x.xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx("init   ");

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
                } else {
                    container.addView(view, 0);
                    currentPage--;
                }
            }

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            // 返回 POSITION_NONE 是为了调用notifyDataSetChange的时候能够刷新界面。
            if(pageCount > 0) return POSITION_NONE;

            return super.getItemPosition(object);
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















