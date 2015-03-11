package com.qiantu.whereistime;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.qiantu.whereistime.util.Utilx;
import com.qiantu.whereistime.view.DepthPageTransformer;

import java.util.ArrayList;
import java.util.List;

public class ReadmeActivity extends Activity {
    private ViewPager mViewPager;

    private int[] mImageIds = new int[]{
            R.drawable.nav_1, R.drawable.nav_2, R.drawable.nav_3, R.drawable.nav_4
    };
    private List<ImageView> imageViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.activity_readme);

        // 判断是不是第一次打开
        if (!Utilx.isFirstOpenApp(this)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        //初始化组件
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        //添加滑动动画
        mViewPager.setPageTransformer(false, new DepthPageTransformer());
        mViewPager.setAdapter(new MyPagerAdapter());

        Button button = (Button) this.findViewById(R.id.button_start);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //动画退出
                v.animate().rotationBy(360 * 10).setDuration(1000).start();
                ObjectAnimator anim = ObjectAnimator.ofFloat(v, View.TRANSLATION_Y, 0, -500);
                ObjectAnimator anim2 = ObjectAnimator.ofFloat(v, View.SCALE_X, 1, 0);
                ObjectAnimator anim3 = ObjectAnimator.ofFloat(v, View.SCALE_Y, 1, 0);
                ObjectAnimator anim4 = ObjectAnimator.ofFloat(v, View.ALPHA, 1f, 0f);
                AnimatorSet set = new AnimatorSet();
                set.playTogether(anim, anim2, anim3, anim4);
                set.setDuration(1000).start();
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        startActivity(new Intent(ReadmeActivity.this, MainActivity.class));
                        finish();
                    }
                });
            }
        });

        initImageViewList();
    }

    private void initImageViewList() {
        imageViewList = new ArrayList<>();
        for (int id : mImageIds) {
            ImageView image = new ImageView(this);
            image.setImageResource(id);
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViewList.add(image);
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
            pageCount = mImageIds.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = imageViewList.get(position);

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
        public int getCount() {
            return pageCount;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
