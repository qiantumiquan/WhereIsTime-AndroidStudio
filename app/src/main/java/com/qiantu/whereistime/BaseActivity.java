package com.qiantu.whereistime;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

/**
 * 这个基本activity注册了广播，收到广播则退出。
 * 然后所有的activity都继承此activity。
 *
 * 原理：每个Activity和Service都注册了“退出广播”；
 */
public class BaseActivity extends Activity {
    private BroadcastReceiver mExitReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //注册广播，用于退出程序
        IntentFilter filter = new IntentFilter();
        filter.addAction(getString(R.string.action_exit));
        mExitReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
            }
        };
        this.registerReceiver(mExitReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(mExitReceiver);
        super.onDestroy();
    }
}













