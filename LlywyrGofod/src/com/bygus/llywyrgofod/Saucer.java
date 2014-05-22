package com.bygus.llywyrgofod;

import com.bygus.gameframework.DynamicGameObject;

public class Saucer extends DynamicGameObject 
{
    public static final float SAUCER_WIDTH = 2f;
    public static final float SAUCER_HEIGHT = 1f;
    public static final float SAUCER_SPEED = 5f;
    
    public float stateTime = 0;
    
	public Saucer(float x, float y)
	{
		super(x, y, SAUCER_WIDTH, SAUCER_HEIGHT);
		velocity.set(0-SAUCER_SPEED, 0);
	}
	
	public void update(float deltaTime)
	{
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		stateTime += deltaTime;
	}
}
