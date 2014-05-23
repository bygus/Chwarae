package com.bygus.llywyrgofod;

import com.bygus.gameframework.DynamicGameObject;

public class Spaceship extends DynamicGameObject
{	
    public static final float SHIP_WIDTH = 2f;
    public static final float SHIP_HEIGHT = 1f;
    public static final float SHIP_SPEED = 2;
    public static final float SHIP_RELOAD_TIME = 0.5f;
    
    private float startTime = 0;
    
	public Spaceship(float x, float y) 
	{
		super(x, y, SHIP_WIDTH, SHIP_HEIGHT);
		velocity.set(0, 0);
	}
	
	
	public void update(float deltaTime)
	{
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);
	}
	
	public void up()
	{
		velocity.set(0, SHIP_SPEED);
	}
	
	public void down()
	{
		velocity.set(0, 0-SHIP_SPEED);
	}
	
	public void stop()
	{
		velocity.set(0, 0);
	}
	
	public Boolean canFire()
	{
		float deltaTime = (System.nanoTime()-startTime) / 1000000000.0f;
		if(deltaTime < SHIP_RELOAD_TIME) // can't shoot until a slight delay
			return false;
		return true;
	}
	
	public synchronized Bullet fireBullet()
	{
		//if(!canFire()) // can't shoot until a slight delay
		//	return null;
		
        startTime = System.nanoTime();
		return new Bullet(position.x, position.y);
	}
}
