package com.springball.view.board;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.springball.activity.R;
import com.springball.view.BackgroundView;

/**
 * 一般的跳板
 * @author cjxiao
 *
 */
public class NormalBoardView extends BaseBoardView{
	
	private Bitmap b;
	
	public NormalBoardView(BackgroundView gameView,float x,float y){
		this.backView = gameView;
		this.startX = x;
		this.startY = y;
		this.mPaint = new Paint();
		//设置红色
		mPaint.setColor(Color.RED);
		//宽高
		width = this.backView.getViewWidth() / WIDTH_COEFFICENT;
		height = this.backView.getViewWidth() / HEIGHT_COEFFICENT;
		//board类型
		this.boardType = BoardType.NORMALBOARD;
		this.setMoveBehavior(getMoveBehaviorByRandom());
		b = BitmapFactory.decodeResource(backView.getResources(), R.drawable.board_normal);
	}

	@Override
	public void drawBoard(Canvas canvas) {
		this.performMove();
		canvas.drawBitmap(b, null, getRect(), mPaint);
	}

}
