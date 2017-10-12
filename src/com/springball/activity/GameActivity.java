package com.springball.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

import com.newqm.sdkoffer.AdView;
import com.newqm.sdkoffer.BanEventListener;
import com.newqm.sdkoffer.QuMiConnect;
import com.springball.base.BaseActivity;
import com.springball.interfaces.OnDialogListener;
import com.springball.util.DataBaseManager;
import com.springball.util.Utils;
import com.springball.view.BackgroundView;

/**
 * 主页面
 * 
 * @author cjxiao
 *
 */
public class GameActivity extends BaseActivity implements OnDialogListener,BanEventListener{
	
	private DataBaseManager dbMgr = null;
	private FrameLayout layout;
	private BackgroundView gameView;
	private AdView adView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		dbMgr = new DataBaseManager(this);
		layout = new FrameLayout(this);
		layout.setLayoutParams(new LayoutParams(-1,-1));
		gameView = new BackgroundView(this,dm.widthPixels,dm.heightPixels);
		adView = new AdView(this);
		layout.addView(gameView);
		FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 
				dm.heightPixels / 14);
		param.bottomMargin = 0;
		param.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL; 
		adView.setLayoutParams(param);
		//在底部加入广告条
		layout.addView(adView);
		setContentView(layout);
		//广告条的回调
		QuMiConnect.getQumiConnectInstance(this).setBannerlistener(this);
	}
	
	public void gameOver(){
		int minScore = 0,scoreCount = 0;
		//查询最小的纪录
		Cursor minCursor = dbMgr.queryDatas(new String[]{"min(score)"}, null, null, null, null, null);
		if(minCursor != null){
			minCursor.moveToFirst();
			minScore = minCursor.getInt(0);
		}
		//查询总共有多少行
		Cursor cursor = dbMgr.queryDatas(new String[]{"*"}, null, null, null, null, null);
		if(cursor != null)
			scoreCount = cursor.getCount();
		if(scoreCount < 10){
			//如果小于10条，就直接插进去
			Utils.showInputDialog(this,this);
		}else{
			//分数大于rank中的最小的，就加进去
			if(gameView.getScore() > minScore){
				Utils.showInputDialog(this,this);
				Cursor delCursor = dbMgr.queryDatas(new String[]{"time"}, "score=?", new String[]{minScore+""}, null, null, null);
				if(delCursor != null){
					delCursor.moveToFirst();
					dbMgr.deleteData(delCursor.getString(0));
				}
			}else{
				Utils.showDialog(this);
			}
		}
	}
	
	@Override
	public void onPostiveClick(Object obj) {
		String str = ((String)obj).trim();
		ContentValues values = new ContentValues();
		if(str.isEmpty() && str.length() == 0){
			str = "";
		}
		values.put("name", str);
		values.put("score", gameView.getScore()+"");
		values.put("time", System.currentTimeMillis()+"");
		dbMgr.insertData(values);
		this.finish();
	}

	@Override
	public void onNegativeClick() {
		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClicked(String arg0) {
		//用户点击广告后回调
	}

	@Override
	public void onShow(String arg0) {
		//广告展示后回调
	}
}