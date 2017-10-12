package com.springball.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 自定义字体的textView
 * 
 * @author cjxiao
 *
 */
public class CustomFontTextView extends TextView {

	public CustomFontTextView(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init(context);
	}

	public CustomFontTextView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public CustomFontTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public CustomFontTextView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		AssetManager assetMgr = context.getAssets();
		Typeface typeFace = Typeface.createFromAsset(assetMgr,
				"fonts/huawencaiyun.TTF");
		this.setTypeface(typeFace);
	}
}
