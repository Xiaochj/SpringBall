package com.springball.view.board;

import com.springball.activity.R;
import com.springball.view.BackgroundView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * 狗屎跳板，碰到就死
 *
 * @author cjxiao
 *
 */
public class ShitBoardView extends BaseBoardView {

	private Bitmap b;

	public ShitBoardView(BackgroundView backView, float x, float y) {
		this.backView = backView;
		this.startX = x;
		this.startY = y;
		mPaint = new Paint();
		mPaint.setColor(Color.GRAY);
		// 宽高
		width = this.backView.getViewWidth() / WIDTH_COEFFICENT;
		height = this.backView.getViewWidth() / HEIGHT_COEFFICENT;
		// board类型
		boardType = BoardType.SHITBOARD;
		this.setMoveBehavior(getMoveBehaviorByRandom());
		b = BitmapFactory.decodeResource(backView.getResources(), R.drawable.board_shit);
	}

	@Override
	public void drawBoard(Canvas canvas) {
		this.performMove();
		//		canvas.drawRect(getRect(), mPaint);
		canvas.drawBitmap(b, null, getRect(), mPaint);
	}

}
