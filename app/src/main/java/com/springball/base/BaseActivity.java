package com.springball.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public abstract class BaseActivity extends Activity {

	private ProgressDialog baseProgressDialog;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//屏幕保持
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		baseProgressDialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_DARK);
	}

	/**
	 * 显示带message的dialog
	 *
	 * @param message
	 */
	public void showDialog(String message) {
		if (baseProgressDialog.isShowing())
			return;
		baseProgressDialog.setMessage(message);
		baseProgressDialog.show();
	}

	/**
	 * dialog消失
	 */
	public void dismissDialog() {
		if (baseProgressDialog.isShowing())
			baseProgressDialog.dismiss();
	}

}
