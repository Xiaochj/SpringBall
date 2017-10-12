package com.springball.view;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.springball.activity.GameActivity;
import com.springball.activity.R;
import com.springball.util.Utils;
import com.springball.view.board.BaseBoardView;
import com.springball.view.board.MiddleBoardView;
import com.springball.view.board.NormalBoardView;
import com.springball.view.board.ShitBoardView;
import com.springball.view.board.SuperBoardView;

/**
 * 背景view
 *
 * @author cjxiao
 *
 */
public class BackgroundView extends SurfaceView implements
		SurfaceHolder.Callback, Runnable, SensorEventListener {

	// 40ms更新一帧，相当于1s更新25帧
	private static final long TIME_FRAME = 40;

	// 画布管理者
	private SurfaceHolder mSurfaceHolder;
	// 绘图线程
	private Thread mThread;
	private boolean isRunning = false;
	// 画布
	private Canvas mCanvas = new Canvas();
	// 画笔
	private Paint mPaint = null;
	private Bitmap mBtpDrawable;
	// 左右按钮是否按下
	public boolean leftKeyDown = false, rightKeyDown = false;
	private BallView ballView;
	private int width, height;
	// board列表
	public List<BaseBoardView> boards;
	// 跳板的高度
	private float boardHeight = 0;
	private Sensor mSensor = null;
	private SensorManager mSensorManager = null;
	// 受到重力感应影响x坐标的变化
	private float gravityX;
	//初始等级1级
	public int level = 1;
	//最高等级7级
	public int MAXLEVEL = 7;
	//分数
	private int score = 0;
	private Context mContext;

	public BackgroundView(Context context, int width, int height) {
		super(context);
		mContext = context;
		this.setWidth(width);
		this.setHeight(height);
		mSurfaceHolder = this.getHolder();
		mSurfaceHolder.addCallback(this);
		mThread = new Thread(this);
		ballView = new BallView(this);
		boards = new LinkedList<BaseBoardView>();
		initBoards();
		mSensorManager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);
		// 加速度感应器
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.registerListener(this, mSensor,
				SensorManager.SENSOR_DELAY_GAME);
		mBtpDrawable = BitmapFactory.decodeResource(getResources(),
				R.drawable.background);
	}

	public void initBoards() {
		//初始化等级
		changeLevel();
		boardHeight = boardHeight - height;
		while (boardHeight < this.height * 2) {
			float randX = (float) Math.random();
			float randY = (float) Math.random();
			float boardType = (float) Math.random();
			// x的坐标轴
			float x = randX * (this.width - 20);
			// borad高度累加
			boardHeight += (float)(randY * this.height / 9 + this.height / 12);
			// y的坐标轴
			float y = this.height - boardHeight;
			//加入炸弹跳板
			if(randX>0.8 - level*level/100.0){
				float randomTemp=(float)Math.random();
				float bx=(float)(randomTemp*(width-20));
				float tempy = (float)(boardHeight + randomTemp * this.height / 9 + this.height / 6);
				float by=this.height-tempy;
				boards.add(new ShitBoardView(this, bx, by));
			}
			// 建立board模型并定位
			BaseBoardView boardView = getBoardTypeByRandom(boardType, x, y);
			boards.add(boardView);
		}
	}

	private void changeLevel(){
		if(score<800){
			level=1;
		}else if(score<1600){
			level=2;
		}else if(score<2500){
			level=3;
		}else if(score<4000){
			level=4;
		}else if(score<6000){
			level=5;
		}else if(score<10000){
			level=6;
		}else{
			level=7;
		}
	}

	/**
	 * 根据随机数创建board的类型
	 *
	 * @param rand
	 * @param x
	 * @param y
	 * @return
	 */
	private BaseBoardView getBoardTypeByRandom(float rand, float x, float y) {
		BaseBoardView board = null;
		float type = (float)(MAXLEVEL +1 -level)/10;
		if (rand <= type)
			board = new NormalBoardView(this, x, y);
		else if (rand <= type + (1-type)*3/5)
			board = new MiddleBoardView(this, x, y);
		else if (rand <= 1)
			board = new SuperBoardView(this, x, y);
		return board;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		isRunning = true;
		// 启动线程
		mThread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		isRunning = false;
	}

	@Override
	public void run() {
		Looper.prepare();
		while (isRunning) {
			long startTime = System.currentTimeMillis();
			synchronized (mSurfaceHolder) {
				// 拿到画布锁定
				mCanvas = mSurfaceHolder.lockCanvas();
				if (mCanvas != null) {
					drawCanvas(mCanvas);
					// 小球和跳板之间碰撞检测
					for (int i = 0; i < boards.size(); i++) {
						BaseBoardView borard = boards.get(i);
						if (Utils.isCollision(ballView, borard)) {
							// 碰撞了，那么给小球一个初速度
							ballView.setV0(ballView.MAX_SPEED_VERTICAL);
							// 判断跳板的类型
							switch (borard.getBoardType()) {
								case NORMALBOARD:
									ballView.setVerticalDis(ballView
											.getDefaultJumpHight()*1.2f);
									break;
								case MIDDLEBOARD:
									ballView.setVerticalDis(ballView
											.getDefaultJumpHight() * 1.8f);
									score += 10f;
									break;
								case SUPERBOARD:
									ballView.setVerticalDis(ballView
											.getDefaultJumpHight() * 2.5f);
									score += 20f;
									boards.remove(borard);
									break;
								case SHITBOARD:
									this.gameOver();
									break;
							}
						}
					}
					// 画小球
					ballView.drawBall(mCanvas);
					// 画跳板
					for (int i = 0; i < boards.size(); i++) {
						BaseBoardView board = boards.get(i);
						// 如果跳板在屏幕下面，移除它
						if (board.getStartY() > this.height)
							boards.remove(board);
					}
					for (BaseBoardView board : boards)
						board.drawBoard(mCanvas);
					// 绘制积分、等级标题栏
					Paint mPaint = new Paint();
					mPaint.setColor(Color.GREEN);
					mPaint.setAlpha(100);
					mPaint.setAntiAlias(true);
					mCanvas.drawRect(0, 0, width, height / 14, mPaint);
					mPaint.setColor(Color.BLACK);
					mPaint.setFakeBoldText(true);
					/* 根据分辨率设置字体大小 */
					if (height > 480) {
						mPaint.setTextSize(32);
					} else {
						mPaint.setTextSize(22);
					}
					/* 画出积分和level */
					mCanvas.drawText(this.getContext().getString(R.string.fenshu) + score, width / 36, height / 24 + 3,
							mPaint);
					mCanvas.drawText(this.getContext().getString(R.string.level) + level, 28 * width / 36,
							height / 24 + 3, mPaint);
					// 绘图完毕，解除画布锁定
					mSurfaceHolder.unlockCanvasAndPost(mCanvas);
				}
			}
			long endTime = System.currentTimeMillis();
			// 绘图的消耗时间
			long diffTime = Math.abs(endTime - startTime);
			// 确保1s更新25帧，要不然线程等待
			while (diffTime < TIME_FRAME) {
				diffTime = Math.abs(System.currentTimeMillis() - startTime);
				// 线程等待
				Thread.yield();
			}
		}
		Looper.loop();
	}

	private void drawCanvas(Canvas canvas) {
		mPaint = new Paint();
		mPaint.setColor(Color.BLACK);
		mPaint.setAntiAlias(true);
		// 充满屏幕
		RectF rectF = new RectF(0, 0, this.getWidth(), this.getHeight());
		// 将背景图放到画布上
		mCanvas.drawBitmap(mBtpDrawable, null, rectF, mPaint);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Context context = this.getContext();
			Activity activity = (Activity) context;
			if (!activity.isFinishing()) {
				activity.finish();
			}
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT)
			leftKeyDown = true;
		else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT)
			rightKeyDown = true;
		return true;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT)
			leftKeyDown = false;
		else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT)
			rightKeyDown = false;
		return true;
	}

	public void addScore(float s){
		s=s/height*100;
		score+=s;
	}

	public int getScore(){
		return score;
	}

	public void gameOver() {
		isRunning = false;
		((GameActivity)mContext).gameOver();
	}

	public int getViewHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getViewWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onSensorChanged(SensorEvent event) {
		gravityX = event.values[SensorManager.DATA_X];
		ballView.setStartX(ballView.getStartX() - gravityX * this.width / 240);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

}
