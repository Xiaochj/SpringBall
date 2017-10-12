package com.springball.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;

import com.springball.view.board.BaseBoardView;

/**
 * 小球
 * 
 * @author cjxiao
 * 
 */
public class BallView {
	
	//小球半径的计算系数
	public final static int RADIUS_COEFFICENT = 24;
	// 小球的半径，初始位置的x，y坐标
	private float radius, startX, startY;
	// 最大垂直方向速度，方向向上，最大垂直加速度
	public float MAX_SPEED_VERTICAL = -24;
	// 默认弹起最大高度
	private float defaultJumpHight;
	// 小球在垂直方向上移动的距离
	private float verticalDis;
	// 定义最终速度，最初速度，以及加速度
	private float vt, v0 = 0f, a = 1f;
	// 定义画笔
	private Paint mPaint;
	private BackgroundView backView;
	private float mapMove;
	private boolean isDying = false;
	private boolean needToAddScore = true;

	public BallView(BackgroundView backView) {
		this.backView = backView;
		// 默认弹起高度为屏幕的1/3
		defaultJumpHight = backView.getViewHeight() / 3;

		// 小球的半径，起始的位置坐标
		radius = backView.getViewWidth() / RADIUS_COEFFICENT;
		startX = backView.getViewWidth() / 2;
		startY = backView.getViewHeight() - radius;

		// 加速度公式Vt^2-V0^2 = 2ax
		MAX_SPEED_VERTICAL = -(float) Math.sqrt(2 * a * defaultJumpHight
				+ Math.pow(v0, 2));
		verticalDis = defaultJumpHight;
		v0 = MAX_SPEED_VERTICAL;

		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		LinearGradient gradient = new LinearGradient(0, 0, radius * 2,
				radius * 2, Color.WHITE, Color.BLACK, Shader.TileMode.MIRROR);
		mPaint.setShader(gradient);
	}

	/**
	 * 把小球画出来
	 * 
	 * @param canvas
	 */
	public void drawBall(Canvas canvas) {
		ballMove();
		canvas.drawCircle(startX, startY, radius, mPaint);
	}

	/**
	 * 小球移动轨迹
	 */
	private void ballMove() {
		// 匀加速运动公式：Vt-V0=at
		vt = v0 + a;
		// 如果当前上升的距离大于默认上升距离
		if (verticalDis > defaultJumpHight && vt < 0) {
			vt = MAX_SPEED_VERTICAL;
		}
		// 一次运动的距离，s=t*(vt+v0)/2
		float sMove = (vt + v0) / 2;
		// 减小时表示向上运动了
		verticalDis = verticalDis + sMove;
		// 小球的高度大于屏幕的2/3并且方向向上
		if (this.startY < backView.getViewHeight() / 3 && vt < 0) {
			for (BaseBoardView board : backView.boards) {
				// 跳板y坐标都降下来sMove
				board.setStartY(board.getStartY() - sMove);
			}
			//超过2/3屏就加分
			backView.addScore(-sMove);
			mapMove -= sMove;
			if (mapMove >= backView.getViewHeight()) {
				// 加入新的跳板
				backView.initBoards();
				mapMove = 0;
			}
			isDying = true;
			needToAddScore = false;
		} else {
			// y轴坐标
			startY = startY + sMove;
		}
		if (sMove < 0 && needToAddScore) {
			//一开始也加分
			backView.addScore(-sMove);
		} else {
			needToAddScore = false;
		}
		v0 = vt;
		// 着地了
		if (startY > backView.getViewHeight()) {
			if (isDying) {
				backView.gameOver();
			} else {
				startY = backView.getViewHeight() - radius;
				v0 = MAX_SPEED_VERTICAL;
				verticalDis = defaultJumpHight;
			}
		}
		if (backView.leftKeyDown)
			startX = startX - 10;
		if (backView.rightKeyDown)
			startX = startX + 10;
		if (startX < radius)
			startX = radius;
		if (startX > backView.getViewWidth() - radius)
			startX = backView.getViewWidth() - radius;

	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public float getStartX() {
		return startX;
	}

	public void setStartX(float startX) {
		this.startX = startX;
	}

	public float getStartY() {
		return startY;
	}

	public void setStartY(float startY) {
		this.startY = startY;
	}

	public float getV0() {
		return v0;
	}

	public void setV0(float v0) {
		this.v0 = v0;
	}

	public float getVerticalDis() {
		return verticalDis;
	}

	public void setVerticalDis(float verticalDis) {
		this.verticalDis = verticalDis;
	}

	public float getDefaultJumpHight() {
		return defaultJumpHight;
	}

	public void setDefaultJumpHight(float defaultJumpHight) {
		this.defaultJumpHight = defaultJumpHight;
	}
}
