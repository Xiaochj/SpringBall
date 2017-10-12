package com.springball.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;

import com.springball.view.board.BaseBoardView;

/**
 * С��
 * 
 * @author cjxiao
 * 
 */
public class BallView {
	
	//С��뾶�ļ���ϵ��
	public final static int RADIUS_COEFFICENT = 24;
	// С��İ뾶����ʼλ�õ�x��y����
	private float radius, startX, startY;
	// ���ֱ�����ٶȣ��������ϣ����ֱ���ٶ�
	public float MAX_SPEED_VERTICAL = -24;
	// Ĭ�ϵ������߶�
	private float defaultJumpHight;
	// С���ڴ�ֱ�������ƶ��ľ���
	private float verticalDis;
	// ���������ٶȣ�����ٶȣ��Լ����ٶ�
	private float vt, v0 = 0f, a = 1f;
	// ���廭��
	private Paint mPaint;
	private BackgroundView backView;
	private float mapMove;
	private boolean isDying = false;
	private boolean needToAddScore = true;

	public BallView(BackgroundView backView) {
		this.backView = backView;
		// Ĭ�ϵ���߶�Ϊ��Ļ��1/3
		defaultJumpHight = backView.getViewHeight() / 3;

		// С��İ뾶����ʼ��λ������
		radius = backView.getViewWidth() / RADIUS_COEFFICENT;
		startX = backView.getViewWidth() / 2;
		startY = backView.getViewHeight() - radius;

		// ���ٶȹ�ʽVt^2-V0^2 = 2ax
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
	 * ��С�򻭳���
	 * 
	 * @param canvas
	 */
	public void drawBall(Canvas canvas) {
		ballMove();
		canvas.drawCircle(startX, startY, radius, mPaint);
	}

	/**
	 * С���ƶ��켣
	 */
	private void ballMove() {
		// �ȼ����˶���ʽ��Vt-V0=at
		vt = v0 + a;
		// �����ǰ�����ľ������Ĭ����������
		if (verticalDis > defaultJumpHight && vt < 0) {
			vt = MAX_SPEED_VERTICAL;
		}
		// һ���˶��ľ��룬s=t*(vt+v0)/2
		float sMove = (vt + v0) / 2;
		// ��Сʱ��ʾ�����˶���
		verticalDis = verticalDis + sMove;
		// С��ĸ߶ȴ�����Ļ��2/3���ҷ�������
		if (this.startY < backView.getViewHeight() / 3 && vt < 0) {
			for (BaseBoardView board : backView.boards) {
				// ����y���궼������sMove
				board.setStartY(board.getStartY() - sMove);
			}
			//����2/3���ͼӷ�
			backView.addScore(-sMove);
			mapMove -= sMove;
			if (mapMove >= backView.getViewHeight()) {
				// �����µ�����
				backView.initBoards();
				mapMove = 0;
			}
			isDying = true;
			needToAddScore = false;
		} else {
			// y������
			startY = startY + sMove;
		}
		if (sMove < 0 && needToAddScore) {
			//һ��ʼҲ�ӷ�
			backView.addScore(-sMove);
		} else {
			needToAddScore = false;
		}
		v0 = vt;
		// �ŵ���
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
