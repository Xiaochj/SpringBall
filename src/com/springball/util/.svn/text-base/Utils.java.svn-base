package com.springball.util;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.springball.activity.GameActivity;
import com.springball.activity.R;
import com.springball.interfaces.OnDialogListener;
import com.springball.view.BallView;
import com.springball.view.board.BaseBoardView;

public class Utils {

	/**
	 * 弹出input dialog
	 * @param context
	 * @param listener
	 */
	public static void showInputDialog(final Context context,final OnDialogListener listener) {
		final EditText editText = new EditText(context);
		editText.setSingleLine();
		new AlertDialog.Builder(context).setMessage(R.string.gameover).setView(editText)
				.setPositiveButton(R.string.yes, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
//						Utils.hideInputMethod(context, editText);
						listener.onPostiveClick(editText.getText().toString());
					}
				}).setNegativeButton(R.string.no, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
//						Utils.hideInputMethod(context, editText);
						listener.onNegativeClick();
					}
				}).show();
	}
	
	/**
	 * 弹出dialog
	 * @param context
	 */
	public static void showDialog(final Context context){
		new AlertDialog.Builder(context).setMessage(R.string.over)
		.setPositiveButton(R.string.yes, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				((Activity)context).finish();
			}
		}).setNegativeButton(R.string.play_again, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				((Activity)context).finish();
				context.startActivity(new Intent(context,GameActivity.class));
			}
		}).show();
	}

	/**
	 * ball(圆)和board(矩形)是否碰撞
	 * 
	 * @return
	 */
	public static boolean isCollision(BallView circle, BaseBoardView rect) {
		// 矩形中心到圆心的水平和垂直距离
		float distX = Math.abs(circle.getStartX() - rect.getStartX()
				- rect.getWidth() / 2);
		float distY = Math.abs(circle.getStartY() - rect.getStartY()
				- rect.getHeight() / 2);
		// 两中心水平或垂直距离大于半径和矩形宽度/2的和或和高度/2的和，圆在矩形外面
		if (distX > circle.getRadius() + rect.getWidth() / 2
				|| distY > circle.getRadius() + rect.getHeight() / 2)
			return false;
		// 水平和垂直方向上有交集
		if (distX <= circle.getRadius() + rect.getWidth() / 2
				&& distY <= circle.getRadius() + rect.getHeight() / 2)
			return true;
		// 拐角处勾股定理计算判断
		float doubleDist = (float) (Math.pow((distX - rect.getWidth() / 2), 2) + Math
				.pow((distY - rect.getHeight() / 2), 2));
		return doubleDist <= circle.getRadius() / 2;
	}
	
	/**
	 * 生成[a,b]之间的随机数
	 * @param a
	 * @param b
	 * @return
	 */
	public static int getRandFromAtoB(int a,int b){
		Random randomFactor = new Random();
		int random = randomFactor.nextInt(b-a+1) + a;
		return random;
	}
	
	public  static void showInputMethod(Context context,View view){
		InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		if(!imm.isActive(view))
			imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
	}
	
	public static void hideInputMethod(Context context, View view){
		InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		if(imm.isActive(view)){
			imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			imm.restartInput(view);
		}
	}
}
