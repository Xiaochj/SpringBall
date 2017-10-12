package com.springball.view.move;

import com.springball.view.board.BaseBoardView;

/**
 * 水平移动
 * 
 * @author cjxiao
 *
 */
public class MoveHorizontal extends BaseMoveBehavior {

	float moveX;

	public MoveHorizontal(BaseBoardView boardView) {
		this.boardView = boardView;
		// 水平方向移动的距离
		moveX = boardView.backView.getViewWidth() / 120;
	}

	@Override
	public void moveBoard() {
		boardView.setStartX(boardView.getStartX()+moveX);
		// 如果跳板触碰到了两壁，那就改变x的方向
		if (boardView.getStartX() <= 0
				|| boardView.getStartX() + boardView.getWidth() >= boardView.backView
						.getViewWidth())
			moveX = -moveX;
	}

}
