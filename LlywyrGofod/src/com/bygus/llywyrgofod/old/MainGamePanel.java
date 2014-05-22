package com.bygus.llywyrgofod.old;

import java.util.ArrayList;
import java.util.Collection;

import com.bygus.llywyrgofod.R;
import com.bygus.llywyrgofod.Spaceship;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainGamePanel extends SurfaceView implements
		SurfaceHolder.Callback {

	private static final String TAG = MainGamePanel.class.getSimpleName();
	private static final int EXPLOSION_SIZE = 200;
	
	private MainThread thread;
	private Spaceship playerShip;
	private ArrayList<Bullet> bullets;

	private String avgFps;

	public MainGamePanel(Context context) {
		super(context);
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);

		// create player spacecraft
		//playerShip = new Spaceship(BitmapFactory.decodeResource(getResources(), R.drawable.llywyr1), 50, 200);
		bullets = new ArrayList<Bullet>();
		
		// create the game loop thread
		thread = new MainThread(getHolder(), this);
		
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// at this point the surface is created and
		// we can safely start the game loop
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Surface is being destroyed");
		// tell the thread to shut down and wait for it to finish
		// this is a clean shutdown
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
		Log.d(TAG, "Thread was shut down cleanly");
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// fills the canvas with black
		//canvas.drawColor(Color.BLACK);
		//droid.draw(canvas);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		int pointerCount = event.getPointerCount();
    	
    	if (event.getAction() == MotionEvent.ACTION_UP
    			||event.getAction() == MotionEvent.ACTION_POINTER_UP) 
		{
			//bullets.add(playerShip.fireBullet());
		}
	    return true;
	}
	
	public void update() 
	{
		//playerShip.update();
		for(Bullet b : getBulletClone())
		{
			b.update();
		}
	}
	
	public void setAvgFps(String avgFps) {
		    this.avgFps = avgFps;
	}
	
	public void render(Canvas canvas) 
	{
		canvas.drawColor(Color.WHITE);
		
		//playerShip.draw(canvas);
		for(Bullet b : getBulletClone())
		{
			b.draw(canvas);
		}

		// display fps - DEBUGGING ONLY
		//displayFps(canvas, avgFps);
	}
	
	private void displayFps(Canvas canvas, String fps) 
	{
		if (canvas != null && fps != null) 
		{
			Paint paint = new Paint();
	        paint.setARGB(255, 255, 255, 255);
	        canvas.drawText(fps, this.getWidth() - 50, 20, paint);
		}
	}
	
	private ArrayList<Bullet> getBulletClone()
	{
		Object lock = new Object();
		ArrayList<Bullet> copy = new ArrayList<Bullet>();
		synchronized(lock)
		{
			copy = (ArrayList<Bullet>)bullets.clone();
		}
		return copy;
	}

}
