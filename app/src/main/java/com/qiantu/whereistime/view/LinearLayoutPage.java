package com.qiantu.whereistime.view;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiantu.whereistime.R;
import com.qiantu.whereistime.model.AppInfo;
import com.qiantu.whereistime.model.Day;
import com.qiantu.whereistime.util.AppUtil;
import com.qiantu.whereistime.util.StringUtil;

import java.util.List;

/**
 * 第一种布局。
 * 线性布局。
 */
public class LinearLayoutPage {
    private OnItemClickListener mOnItemClickListener;

    public View getView(LayoutInflater inflater, Day day) {
        //获取page
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.view, null);

        //日期
        TextView textDate = (TextView) view.findViewById(R.id.text_date);
        textDate.setText(day.getDate());

        //数据从数据库中取得，根据usertime排列
        List<AppInfo> list = day.getAppInfos().size() > 15 ?
                day.getAppInfos().subList(0, 15) : day.getAppInfos();

        //如果没有数据，则返回view
        if(list.size() == 0) {
            return view;
        }

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
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("app", app);
                    intent.putExtra("sumTime", sumTime);
                    if(mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(intent);
                    }
                }
            });
        }

        return view;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        /**
         * 点击item事件
         * @param intent intent未绑定Activity，只是绑定一些数据。
         *               intent.setClass之后才可以startActivity。
         *               应该跳转到AppInfoActivity.class
         */
        public void onItemClick(Intent intent);
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
