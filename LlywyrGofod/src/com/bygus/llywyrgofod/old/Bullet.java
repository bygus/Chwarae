package com.bygus.llywyrgofod.old;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.util.Log;

public class Bullet 
{
	private static final String LogTag = Bullet.class.getSimpleName();
	private Velocity velocity; // the velocity of the bullet
	private int x;			// the X coordinate
	private int y;			// the Y coordinate
	
	public Bullet(int x, int y)
	{
		this.x=x;
		this.y=y;
		velocity = new Velocity(10, 0);
	}
	
	public void update()
	{
		x += (velocity.getXv() * velocity.getxDirection()); 
		y += (velocity.getYv() * velocity.getyDirection());
		Log.d(LogTag, "Bullet updated at (x,y) = (" + x + ", " + y + " )");
	}
	
	public void draw(Canvas canvas) 
	{
		Log.d(LogTag, "Bullet drawn");
		Rect r = new Rect(x, y, x+20, y+20);
		Paint paint = new Paint();
		paint.setColor(Color.GREEN);
	    paint.setStyle(Style.FILL);
		canvas.drawRect(r, paint);
	}
}
