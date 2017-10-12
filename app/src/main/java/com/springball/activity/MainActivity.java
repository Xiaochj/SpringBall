package com.springball.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.newqm.sdkoffer.QuMiConnect;
import com.springball.base.BaseActivity;
import com.springball.view.CustomFontTextView;

/**
 * 主页面
 * @author cjxiao
 *
 */
public class MainActivity extends BaseActivity implements OnClickListener{

	//广告的id和key
	private static final String AD_ID = "621e7ead58f5c5ac";
	private static final String AD_KEY = "1dead80d2cbb421c";

	private CustomFontTextView startTv;
	private CustomFontTextView rankTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 初始化广告数据统计，不需要重复调用,调用一次即可
		QuMiConnect.ConnectQuMi(this, AD_ID, AD_KEY);
		this.setContentView(R.layout.main_layout);
		init();
	}

	private void init(){
		startTv = (CustomFontTextView)findViewById(R.id.start);
		rankTv = (CustomFontTextView)findViewById(R.id.rank);
		startTv.setOnClickListener(this);
		rankTv.setOnClickListener(this);
	}

	@Override()
	protected void onResume() {
		super.onResume();
		dismissDialog();
	}

	@Override
	public void onClick(View v) {
		showDialog(getString(R.string.loading));
		Intent intent = null;
		switch(v.getId()){
			case R.id.start:
				intent = new Intent(this, GameActivity.class);
				startActivity(intent);
				break;
			case R.id.rank:
				intent = new Intent(this,RankActivity.class);
				startActivity(intent);
				break;
		}
	}

}















