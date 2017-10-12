package com.springball.view.board;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.springball.view.BackgroundView;
import com.springball.view.move.BaseMoveBehavior;
import com.springball.view.move.MoveCircle;
import com.springball.view.move.MoveHorizontal;
import com.springball.view.move.MoveIncline;
import com.springball.view.move.MoveStill;
import com.springball.view.move.MoveVertical;

/**
 * 跳板的基类
 * @author xiaochj
 *
 */
public abstract class BaseBoardView {

	//宽高的计算系数
	public static final int WIDTH_COEFFICENT = 12;
	public static final int HEIGHT_COEFFICENT = 18;

	//跳板的种类
	protected BoardType boardType;
	//跳板的坐标位置
	protected float startX,startY;
	//跳板的宽高
	protected float width,height;
	public BackgroundView backView;
	protected Paint mPaint;
	protected BaseMoveBehavior moveBhv = new MoveStill();

	public abstract void drawBoard(Canvas canvas);

	/**
	 * 获取跳板的形状，方形的
	 * @return
	 */
	public Rect getRect(){
		return new Rect((int)startX, (int)startY, (int)(startX+getWidth()), (int)(startY+getHeight()));
	}

	/**
	 * 获取跳板的类型
	 * @return
	 */
	public BaseMoveBehavior getMoveBehaviorByRandom(){
		BaseMoveBehavior moveBhv = null;
		//获取0-1随机数
		double rand = Math.random();
		//根据关卡数设置type
		double type = (double)(backView.MAXLEVEL +1 -backView.level)/10;
		double otherType = (double)(1-type)/4;
		//随着等级越来越高，board的难度越来越大
		if(rand <= type)
			moveBhv = new MoveStill();
		else if(rand <= type +otherType*1)
			moveBhv = new MoveHorizontal(this);
		else if(rand <= type + otherType*2)
			moveBhv = new MoveVertical(this);
		else if(rand <= type + otherType*3)
			moveBhv = new MoveIncline(this);
		else if(rand <= type+ otherType*4)
			moveBhv = new MoveCircle(this);
		return moveBhv;
	}

	public void setMoveBehavior(BaseMoveBehavior moveBehavior) {
		this.moveBhv = moveBehavior;
	}

	/**
	 * 调用跳板的移动
	 */
	public void performMove() {
		moveBhv.moveBoard();
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
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}

	public BoardType getBoardType() {
		return boardType;
	}

	public void setBoardType(BoardType boardType) {
		this.boardType = boardType;
	}

}
