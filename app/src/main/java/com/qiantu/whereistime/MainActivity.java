package com.qiantu.whereistime;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.qiantu.whereistime.model.AppInfo;
import com.qiantu.whereistime.model.Day;
import com.qiantu.whereistime.service.BackService;
import com.qiantu.whereistime.service.DataService;
import com.qiantu.whereistime.service.DeamonService;
import com.qiantu.whereistime.util.AppUtil;
import com.qiantu.whereistime.util.DBUtilx;
import com.qiantu.whereistime.util.StringUtil;
import com.qiantu.whereistime.util.Utilx;
import com.qiantu.whereistime.util.x;
import com.qiantu.whereistime.view.RotationTransformer;

import java.util.List;

public class MainActivity extends BaseActivity {
    private DbUtils db;

    /* 一系列的广播接收器 */
    private BroadcastReceiver mUpdateUIReceiver;//注册广播接收更新UI的命令;
    private BroadcastReceiver clearDatabaseReceiver;//注册广播删除数据库数据

    private ViewPager mViewPager;

    private List<TextView> list_text;
    private TextView text_date;

    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.activity_main);

        DBUtilx.init(this);
        db = DBUtilx.getInstance();

        //初始化组件
        mInflater = LayoutInflater.from(this);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        //添加滑动动画
        mViewPager.setPageTransformer(false, new RotationTransformer());
        mViewPager.setAdapter(new MyPagerAdapter());

        //注册一系列的广播接收器
        initBroadcast();

        //启动后台线程
        startService(new Intent(this, DeamonService.class));
        startService(new Intent(this, BackService.class));

        // 判断是不是第一次打开
        if (Utilx.isFirstOpenApp(this)) {
            startActivity(new Intent(this, ReadmeActivity.class));
        }
    }

    /**
     * 注册一系列的广播接收器
     */
    public void initBroadcast() {
        //注册广播接收更新UI的命令
        IntentFilter filter = new IntentFilter();
        filter.addAction(getString(R.string.action_update_ui));
        mUpdateUIReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //注册广播接收更新UI的命令
//                updateUI();
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

    /**
     * 清楚数据库已经保存的信息
     */
    public void clearDatabaseInfo() {
        try {
            db.deleteAll(AppInfo.class);
//            this.updateUI();
        } catch (DbException e) {
            e.printStackTrace();
        }
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
            //判断是否是启动状态（初始化第一第二页）
            if (!isInit) {
                initCount++;
                if (initCount == 2) isInit = true;
            }

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

            //获取page
            LinearLayout view = (LinearLayout) mInflater.inflate(R.layout.view, null);

            text_date = (TextView) view.findViewById(R.id.text_date);
            text_date.setText(day.getDate());

            //数据从数据库中取得，根据usertime排列
            List<AppInfo> list = day.getAppInfos().size() > 15 ?
                    day.getAppInfos().subList(0, 15) : day.getAppInfos();

            //总的使用时间
            double sum = 0;
            for (AppInfo app : list) {
                sum += app.getUseTime();
            }
            final double sumTime = sum;//用于传递到下一个activity

            //遍历所有list
            for (int i = 0; i < 15 && i < list.size(); i++) {
                TextView text = (TextView) view.findViewById(mTextViewIds[i]);

                final AppInfo app = list.get(i);

                String str = "";
                if (i < 7) {
                    str = app.getName() + "\n" + AppUtil.s2m(app.getUseTime())
                            + StringUtil.subDouble(app.getUseTime() / sum * 100) + "%";
                } else {
                    str = app.getName() + "\n"
                            + StringUtil.subDouble(app.getUseTime() / sum * 100) + "%";
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
            }

            //添加在前面还是后面
            if (position > currentPage) container.addView(view);
            else container.addView(view, 0);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            x.xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx("remove" + position);

            //删除前面还是后面
            if (position > currentPage) container.removeViewAt(2);
            else container.removeViewAt(0);
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

    /**
     * a代表大格，b代表中格，c代表小格，d代表最小
     */
    private int[] mTextViewIds = new int[]{
            R.id.text_1_a,
            R.id.text_2_b,
            R.id.text_3_b,
            R.id.text_4_c,
            R.id.text_5_c,
            R.id.text_6_c,
            R.id.text_7_c,
            R.id.text_8_d,
            R.id.text_9_d,
            R.id.text_10_d,
            R.id.text_11_d,
            R.id.text_12_d,
            R.id.text_13_d,
            R.id.text_14_d,
            R.id.text_15_d
    };
}















