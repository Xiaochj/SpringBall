package com.springball.view.move;

import com.springball.view.board.BaseBoardView;

/**
 * 移动跳板的基类
 * @author cjxiao
 *
 */
public abstract class BaseMoveBehavior {

	BaseBoardView boardView;

	public abstract void moveBoard();

}
