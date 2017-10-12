package com.springball.view.move;

import com.springball.util.Utils;
import com.springball.view.board.BaseBoardView;

/**
 * 竖直移动
 * 
 * @author cjxiao
 *
 */
public class MoveVertical extends BaseMoveBehavior {

	private float moveY = 0;
	private float moveHeight = 0;
	private float zeroY;

	public MoveVertical(BaseBoardView boardView) {
		this.boardView = boardView;
		// 初始时候y轴坐标
		zeroY = boardView.getStartY();
		// 1帧移动的y轴距离
		moveY = boardView.backView.getViewHeight() / 240;
		// 总共移动的y轴距离
		moveHeight = (float) (boardView.backView.getViewHeight() / Utils.getRandFromAtoB(8, 10));
	}

	@Override
	public void moveBoard() {
		this.boardView.setStartY(this.boardView.getStartY() + moveY);
		float moveDis = Math.abs(zeroY - boardView.getStartY());
		// 如果移动的距离超过了规定的距离，那就调头
		if (moveDis >= moveHeight) {
			moveY = -moveY;
			zeroY = boardView.getStartY();
		}
	}

}
