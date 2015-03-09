package com.qiantu.whereistime.view;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by miquan on 2015/2/4.
 */
public class RotationTransformer implements ViewPager.PageTransformer {
    private final float MAX_ROTATE = 45;

    private float mRotate;

    @Override
    public void transformPage(View page, float position) {
        //设置旋转中心
        page.setPivotX(page.getWidth()/2);
        page.setPivotY(page.getHeight());

        if (position < -1) { // [-Infinity,-1)
            page.setRotation(0);
            page.setAlpha(0);

        } else if (position <= 0) { // [-1,0]
            mRotate = MAX_ROTATE * position;
            page.setRotation(mRotate);

            page.setAlpha(1 + position);

        } else if (position <= 1) { // (0,1]
            mRotate = MAX_ROTATE * position;
            page.setRotation(mRotate);

            page.setAlpha(1-position);

        } else { // (1,+Infinity]
            page.setRotation(0);
            page.setAlpha(0);
        }
    }
}
