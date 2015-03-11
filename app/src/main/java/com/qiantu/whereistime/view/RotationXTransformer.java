package com.qiantu.whereistime.view;

import android.support.v4.view.ViewPager;
import android.view.View;

public class RotationXTransformer implements ViewPager.PageTransformer {

    public void transformPage(View view, float position) {

        if (position <= -1) { // [-Infinity,-1)
            view.setAlpha(0);

        } else if (position < 0) { // (-1,0]
            view.setTranslationX(-position * view.getWidth());
            view.setRotationY(180 * position);

        } else if (position < 1) { // (0,1)
            view.setTranslationX(-position * view.getWidth());
            view.setRotationY(180 * position);

        } else { // (1,+Infinity]
            view.setAlpha(0);
        }


    }
}
