package com.springball.view.board;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.springball.activity.R;
import com.springball.view.BackgroundView;

/**
 * 超级跳板
 * @author cjxiao
 *
 */
public class SuperBoardView extends BaseBoardView {

	private Bitmap b;

	public SuperBoardView(BackgroundView backView, float x, float y) {
		this.backView = backView;
		this.startX = x;
		this.startY = y;
		mPaint = new Paint();
		mPaint.setColor(Color.GREEN);
		// 宽高
		width = this.backView.getViewWidth() / WIDTH_COEFFICENT;
		height = this.backView.getViewWidth() / HEIGHT_COEFFICENT;
		// board类型
		boardType = BoardType.SUPERBOARD;
		this.setMoveBehavior(getMoveBehaviorByRandom());
		b = BitmapFactory.decodeResource(backView.getResources(), R.drawable.board_super);
	}

	@Override
	public void drawBoard(Canvas canvas) {
		this.performMove();
		//		canvas.drawRect(getRect(), mPaint);
		canvas.drawBitmap(b, null, getRect(), mPaint);
	}

}
