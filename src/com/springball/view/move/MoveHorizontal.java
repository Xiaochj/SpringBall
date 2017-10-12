package com.springball.view.move;

import com.springball.view.board.BaseBoardView;

/**
 * ˮƽ�ƶ�
 * 
 * @author cjxiao
 *
 */
public class MoveHorizontal extends BaseMoveBehavior {

	float moveX;

	public MoveHorizontal(BaseBoardView boardView) {
		this.boardView = boardView;
		// ˮƽ�����ƶ��ľ���
		moveX = boardView.backView.getViewWidth() / 120;
	}

	@Override
	public void moveBoard() {
		boardView.setStartX(boardView.getStartX()+moveX);
		// ������崥���������ڣ��Ǿ͸ı�x�ķ���
		if (boardView.getStartX() <= 0
				|| boardView.getStartX() + boardView.getWidth() >= boardView.backView
						.getViewWidth())
			moveX = -moveX;
	}

}
