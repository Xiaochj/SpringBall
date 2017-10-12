package com.springball.view.move;

import com.springball.util.Utils;
import com.springball.view.board.BaseBoardView;

/**
 * б���ƶ�
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
		// ˮƽ�����ƶ��ľ���
		moveX = boardView.backView.getViewWidth() / 120;
		// ��ʼʱ��y������
		zeroY = boardView.getStartY();
		// 1֡�ƶ���y�����
		moveY = boardView.backView.getViewHeight() / 240;
		// �ܹ��ƶ���y�����
		moveHeight = (float) (boardView.backView.getViewHeight() / Utils
				.getRandFromAtoB(8, 10));
	}

	@Override
	public void moveBoard() {
		boardView.setStartX(boardView.getStartX() + moveX);
		// ������崥���������ڣ��Ǿ͸ı�x�ķ���
		if (boardView.getStartX() <= 0
				|| boardView.getStartX() + boardView.getWidth() >= boardView.backView
						.getViewWidth()) {
			moveX = -moveX;
		}
		this.boardView.setStartY(this.boardView.getStartY() + moveY);
		float moveDis = Math.abs(zeroY - boardView.getStartY());
		// ����ƶ��ľ��볬���˹涨�ľ��룬�Ǿ͵�ͷ
		if (moveDis >= moveHeight) {
			moveY = -moveY;
			zeroY = boardView.getStartY();
		}
	}

}
