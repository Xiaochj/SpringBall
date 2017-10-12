package com.springball.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.springball.base.BaseActivity;
/**
 * »¶Ó­Ò³Ãæ
 * @author cjxiao
 *
 */
public class WelcomeActivity extends BaseActivity {
	
	private final static int ANIMATION_DURTION = 5000;
	private RelativeLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initLayout();
	}

	private void initLayout(){
		layout = new RelativeLayout(this);
		ImageView imageView = new ImageView(this);
		layout.setLayoutParams(new LayoutParams(-1, -1));
		imageView.setLayoutParams(new RelativeLayout.LayoutParams(-1,-1));
		imageView.setBackgroundResource(R.drawable.background);
		imageView.setScaleType(ScaleType.FIT_XY);
		layout.addView(imageView);
		setContentView(layout);
		initAnimation();
	}
	
	private void initAnimation(){
		AlphaAnimation anim = new AlphaAnimation(0, 1);
		anim.setDuration(ANIMATION_DURTION);
		anim.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				startIntent();
			}
		});
		layout.startAnimation(anim);
	}
	
	private void startIntent(){
		Intent intent = new Intent(this,MainActivity.class);
		this.startActivity(intent);
		this.finish();
	}
}











