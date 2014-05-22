package com.bygus.llywyrgofod;

import com.bygus.gameframework.DynamicGameObject;

public class Bullet extends DynamicGameObject 
{
    public static final float BULLET_WIDTH = 0.5f;
    public static final float BULLET_HEIGHT = 0.3f;
    public static final float BULLET_SPEED = 5f;
    
	public Bullet(float x, float y)
	{
		super(x, y, BULLET_WIDTH, BULLET_HEIGHT);
		velocity.set(BULLET_SPEED, 0);
	}
	
	public void update(float deltaTime)
	{
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
	}
}
