package com.springball.view.board;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.springball.activity.R;
import com.springball.view.BackgroundView;

/**
 * 稍微牛逼一点的跳板
 * @author cjxiao
 *
 */
public class MiddleBoardView extends BaseBoardView{

	private Bitmap b;

	public MiddleBoardView(BackgroundView backView,float x,float y){
		this.backView = backView;
		this.startX = x;
		this.startY = y;
		mPaint = new Paint();
		mPaint.setColor(Color.YELLOW);
		//宽高
		width = this.backView.getViewWidth() / WIDTH_COEFFICENT;
		height = this.backView.getViewWidth() / HEIGHT_COEFFICENT;
		//board类型
		boardType = BoardType.MIDDLEBOARD;
		this.setMoveBehavior(getMoveBehaviorByRandom());
		b = BitmapFactory.decodeResource(backView.getResources(), R.drawable.board_middle);

	}

	@Override
	public void drawBoard(Canvas canvas) {
		this.performMove();
		//		canvas.drawRect(getRect(), mPaint);
		canvas.drawBitmap(b, null, getRect(), mPaint);
	}

}
