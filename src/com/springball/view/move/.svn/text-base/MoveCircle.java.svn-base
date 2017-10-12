package com.springball.view.move;

import com.springball.view.board.BaseBoardView;

/**
 * ÕýÇÐÔË¶¯
 * @author cjxiao
 *
 */
public class MoveCircle extends BaseMoveBehavior {
	
	float t;
	float r; 

	public MoveCircle(BaseBoardView boardView){
		this.boardView = boardView;
		t = (float) (Math.random()*120);
		r = boardView.getWidth()/5;
		if(boardView.getStartX()-r < 0)
			boardView.setStartX(boardView.getStartX()+r);
		else if(boardView.getStartX()+r > boardView.backView.getViewWidth())
			boardView.setStartX(boardView.backView.getViewWidth() - r );
	}
	
	@Override
	public void moveBoard() {
		t++;
		boardView.setStartX((float) (boardView.getStartX() + Math.cos(Math.PI*t/30)*r));
		boardView.setStartY((float) (boardView.getStartY() + Math.sin(Math.PI*t/30)*r));
		if(t >120){
			t = 0;
		}
	}

}
