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
 * ����view
 * 
 * @author cjxiao
 * 
 */
public class BackgroundView extends SurfaceView implements
		SurfaceHolder.Callback, Runnable, SensorEventListener {

	// 40ms����һ֡���൱��1s����25֡
	private static final long TIME_FRAME = 40;
	
	// ����������
	private SurfaceHolder mSurfaceHolder;
	// ��ͼ�߳�
	private Thread mThread;
	private boolean isRunning = false;
	// ����
	private Canvas mCanvas = new Canvas();
	// ����
	private Paint mPaint = null;
	private Bitmap mBtpDrawable;
	// ���Ұ�ť�Ƿ���
	public boolean leftKeyDown = false, rightKeyDown = false;
	private BallView ballView;
	private int width, height;
	// board�б�
	public List<BaseBoardView> boards;
	// ����ĸ߶�
	private float boardHeight = 0;
	private Sensor mSensor = null;
	private SensorManager mSensorManager = null;
	// �ܵ�������ӦӰ��x����ı仯
	private float gravityX;
	//��ʼ�ȼ�1��
	public int level = 1;
	//��ߵȼ�7��
	public int MAXLEVEL = 7;
	//����
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
		// ���ٶȸ�Ӧ��
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.registerListener(this, mSensor,
				SensorManager.SENSOR_DELAY_GAME);
		mBtpDrawable = BitmapFactory.decodeResource(getResources(),
				R.drawable.background);
	}

	public void initBoards() {
		//��ʼ���ȼ�
		changeLevel();
		boardHeight = boardHeight - height;
		while (boardHeight < this.height * 2) {
			float randX = (float) Math.random();
			float randY = (float) Math.random();
			float boardType = (float) Math.random();
			// x��������
			float x = randX * (this.width - 20);
			// borad�߶��ۼ�
			boardHeight += (float)(randY * this.height / 9 + this.height / 12);
			// y��������
			float y = this.height - boardHeight;
			//����ը������
			if(randX>0.8 - level*level/100.0){
				float randomTemp=(float)Math.random();
				float bx=(float)(randomTemp*(width-20));
				float tempy = (float)(boardHeight + randomTemp * this.height / 9 + this.height / 6);
				float by=this.height-tempy;
				boards.add(new ShitBoardView(this, bx, by));
			}
			// ����boardģ�Ͳ���λ
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
	 * �������������board������
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
		// �����߳�
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
				// �õ���������
				mCanvas = mSurfaceHolder.lockCanvas();
				if (mCanvas != null) {
					drawCanvas(mCanvas);
					// С�������֮����ײ���
					for (int i = 0; i < boards.size(); i++) {
						BaseBoardView borard = boards.get(i);
						if (Utils.isCollision(ballView, borard)) {
							// ��ײ�ˣ���ô��С��һ�����ٶ�
							ballView.setV0(ballView.MAX_SPEED_VERTICAL);
							// �ж����������
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
					// ��С��
					ballView.drawBall(mCanvas);
					// ������
					for (int i = 0; i < boards.size(); i++) {
						BaseBoardView board = boards.get(i);
						// �����������Ļ���棬�Ƴ���
						if (board.getStartY() > this.height)
							boards.remove(board);
					}
					for (BaseBoardView board : boards)
						board.drawBoard(mCanvas);
					// ���ƻ��֡��ȼ�������
					Paint mPaint = new Paint();
					mPaint.setColor(Color.GREEN);
					mPaint.setAlpha(100);
					mPaint.setAntiAlias(true);
					mCanvas.drawRect(0, 0, width, height / 14, mPaint);
					mPaint.setColor(Color.BLACK);
					mPaint.setFakeBoldText(true);
					/* ���ݷֱ������������С */
					if (height > 480) {
						mPaint.setTextSize(32);
					} else {
						mPaint.setTextSize(22);
					}
					/* �������ֺ�level */
					mCanvas.drawText(this.getContext().getString(R.string.fenshu) + score, width / 36, height / 24 + 3,
							mPaint);
					mCanvas.drawText(this.getContext().getString(R.string.level) + level, 28 * width / 36,
							height / 24 + 3, mPaint);
					// ��ͼ��ϣ������������
					mSurfaceHolder.unlockCanvasAndPost(mCanvas);
				}
			}
			long endTime = System.currentTimeMillis();
			// ��ͼ������ʱ��
			long diffTime = Math.abs(endTime - startTime);
			// ȷ��1s����25֡��Ҫ��Ȼ�̵߳ȴ�
			while (diffTime < TIME_FRAME) {
				diffTime = Math.abs(System.currentTimeMillis() - startTime);
				// �̵߳ȴ�
				Thread.yield();
			}
		}
		Looper.loop();
	}

	private void drawCanvas(Canvas canvas) {
		mPaint = new Paint();
		mPaint.setColor(Color.BLACK);
		mPaint.setAntiAlias(true);
		// ������Ļ
		RectF rectF = new RectF(0, 0, this.getWidth(), this.getHeight());
		// ������ͼ�ŵ�������
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
