package com.qiantu.whereistime.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qiantu.whereistime.AppInfoActivity;
import com.qiantu.whereistime.R;
import com.qiantu.whereistime.model.AppInfo;
import com.qiantu.whereistime.model.Day;
import com.qiantu.whereistime.util.Utilx;

import java.util.List;

/**
 * 第一种布局。
 * 线性布局。
 */
public class LinearLayoutPage implements View.OnClickListener {

    private OnButtonClickListener mOnButtonClickListener;

    public View getView(final Context context, Day day) {
        //获取page
        RelativeLayout view = (RelativeLayout) LayoutInflater.from(context)
                .inflate(R.layout.view_linearlayout_page, null);

        //如果没有数据，则返回view
        if(day == null || day.getAppInfos().size() == 0) return view;

        //数据从数据库中取得，根据usertime排列
        List<AppInfo> list = day.getAppInfos().size() > 15 ?
                day.getAppInfos().subList(0, 15) : day.getAppInfos();

        //如果有数据，则设置数据
        //日期
        TextView textDate = (TextView) view.findViewById(R.id.text_date);
        textDate.setText(day.getDate());

        //总的使用时间
        double sum = 0;
        for (AppInfo app : list) {
            sum += app.getUseTime();
        }
        final double sumTime = sum;//用于传递到下一个activity

        //遍历所有list，设置文字
        for (int i = 0; i < 15 && i < list.size(); i++) {
            TextView text = (TextView) view.findViewById(mTextViewIds[i]);

            final AppInfo app = list.get(i);

            String str = "";
            if (i < 7) {
                str = app.getName() + "\n" + Utilx.s2m(app.getUseTime())
                        + Utilx.subDouble(app.getUseTime() / sum * 100) + "%";
            } else {
                str = app.getName() + "\n"
                        + Utilx.subDouble(app.getUseTime() / sum * 100) + "%";
            }

            text.setTextColor(Color.rgb(0xEC, 0xF0, 0xF1));
            text.setText(str);
            text.setLongClickable(true);//这个是必须的
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("app", app);
                    intent.putExtra("sumTime", sumTime);
                    intent.setClass(context, AppInfoActivity.class);
                    context.startActivity(intent);
                }
            });
        }

        return view;
    }

    public void setOnButtonClickListener(OnButtonClickListener listener) {
        mOnButtonClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if(mOnButtonClickListener == null) return ;

        switch (v.getId()) {
            case R.id.btn_menu:
                mOnButtonClickListener.onMenuButtonClick(v);
                break;
        }
    }

    public interface OnButtonClickListener {
        public void onMenuButtonClick(View view);
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
