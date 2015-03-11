package com.qiantu.whereistime;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lidroid.xutils.exception.DbException;
import com.qiantu.whereistime.model.AppInfo;
import com.qiantu.whereistime.util.DBUtilx;
import com.qiantu.whereistime.util.Utilx;


public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private Button mBtnReturn;//返回上一个Activity

    private Button mBtnclear;//清除记录
    private Button mBtnAppDesc;//功能介绍
    private Button mBtnExit;//完全退出

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mBtnReturn = (Button) findViewById(R.id.btn_return);
        mBtnReturn.setOnClickListener(this);
        mBtnclear = (Button) findViewById(R.id.btn_clear);
        mBtnclear.setOnClickListener(this);
        mBtnAppDesc = (Button) findViewById(R.id.btn_app_desc);
        mBtnAppDesc.setOnClickListener(this);
        mBtnExit = (Button) findViewById(R.id.btn_exit);
        mBtnExit.setOnClickListener(this);
    }

    ///////////////////////////////////////////////////////////////
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode == KeyEvent.KEYCODE_MENU) {
//        }
//        return true;
//    }
    ///////////////////////////////////////////////////////////////

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_return:
                finish();
                break;
            case R.id.btn_clear:
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("确定要清除所有记录吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    DBUtilx.getInstance().deleteAll(AppInfo.class);
                                    Toast.makeText(SettingActivity.this, "删除完成！", Toast.LENGTH_SHORT).show();
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
                break;
            case R.id.btn_app_desc:
                new AlertDialog.Builder(this)
                        .setTitle("说明")
                        .setMessage("此应用会让你知道你花在手机的时间分别用在了什么软件上面。主屏幕按照使用时间排列，点击进去可以看到比例。")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
                break;
            case R.id.btn_exit:
                Utilx.exitApp(this);
                break;
        }
    }
}
