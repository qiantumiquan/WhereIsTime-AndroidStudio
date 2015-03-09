package com.qiantu.whereistime;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.File;

public class ShareDialog extends Activity {
	private LinearLayout dialog_share_weixin;
	private LinearLayout dialog_share_qq;
	
	private Tencent mTencent;
	/** 已经截图的路径，又上个activity传过来 */
	private String imagePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_share);
		
		mTencent = Tencent.createInstance("101062345", getApplicationContext());
		
		imagePath = this.getIntent().getStringExtra("imagePath");
		
		//设置按钮按下效果
		LinearLayout layout_setting = (LinearLayout) this.findViewById(R.id.layout_share);
		layout_setting.setAlpha(0.7f);
		
		//微信
		dialog_share_weixin = (LinearLayout) this.findViewById(R.id.dialog_share_weixin);
		dialog_share_weixin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				ComponentName comp = new ComponentName("com.tencent.mm",
						"com.tencent.mm.ui.tools.ShareToTimeLineUI");
				intent.setComponent(comp);
				intent.setAction("android.intent.action.SEND");
				intent.setType("image/*");
				//intent.setFlags(0x3000001);
				intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imagePath)));
				startActivity(intent);
			}
		});
		
		//qq空间
		dialog_share_qq = (LinearLayout) this.findViewById(R.id.dialog_share_qq);
		dialog_share_qq.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Bundle params = new Bundle(); 
				params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT); 
				params.putString(QQShare.SHARE_TO_QQ_TITLE, "时间都去哪了"); 
				params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "我在使用'时间都去哪了'，你也来试试~！");
				params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  "http://zhushou.360.cn/detail/index/soft_id/1643393?recrefer=SE_D_%E6%97%B6%E9%97%B4%E9%83%BD%E5%8E%BB%E5%93%AA%E4%BA%86");
				params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,imagePath);
				params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "返回时间都去哪了");
				
				mTencent.shareToQQ(ShareDialog.this, params, new IUiListener() {
					@Override
					public void onError(UiError arg0) {
						Toast.makeText(ShareDialog.this, "分享出错", Toast.LENGTH_SHORT).show();
					}
					@Override
					public void onComplete(Object arg0) {
						Toast.makeText(ShareDialog.this, "分享成功", Toast.LENGTH_LONG).show();
					}
					@Override
					public void onCancel() {
						Toast.makeText(ShareDialog.this, "分享取消", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
		
		//点击空白地方，activity消失
		RelativeLayout dialog_except = (RelativeLayout) this.findViewById(R.id.dialog_except);
		dialog_except.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (null != mTencent)    
	        mTencent.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onDestroy() {
		//删除图片
		File file = new File(imagePath);
		if(file.exists()) {
			file.delete();
		}
		super.onDestroy();
	}
	
}













