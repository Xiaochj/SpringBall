package com.springball.view.move;

import com.springball.util.Utils;
import com.springball.view.board.BaseBoardView;

/**
 * 斜线移动
 * 
 * @author cjxiao
 *
 */
public class MoveIncline extends BaseMoveBehavior {

	private float moveY = 0, moveX = 0;
	private float moveHeight = 0;
	private float zeroY;

	public MoveIncline(BaseBoardView boardView) {
		this.boardView = boardView;
		// 水平方向移动的距离
		moveX = boardView.backView.getViewWidth() / 120;
		// 初始时候y轴坐标
		zeroY = boardView.getStartY();
		// 1帧移动的y轴距离
		moveY = boardView.backView.getViewHeight() / 240;
		// 总共移动的y轴距离
		moveHeight = (float) (boardView.backView.getViewHeight() / Utils
				.getRandFromAtoB(8, 10));
	}

	@Override
	public void moveBoard() {
		boardView.setStartX(boardView.getStartX() + moveX);
		// 如果跳板触碰到了两壁，那就改变x的方向
		if (boardView.getStartX() <= 0
				|| boardView.getStartX() + boardView.getWidth() >= boardView.backView
						.getViewWidth()) {
			moveX = -moveX;
		}
		this.boardView.setStartY(this.boardView.getStartY() + moveY);
		float moveDis = Math.abs(zeroY - boardView.getStartY());
		// 如果移动的距离超过了规定的距离，那就调头
		if (moveDis >= moveHeight) {
			moveY = -moveY;
			zeroY = boardView.getStartY();
		}
	}

}
