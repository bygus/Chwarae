package com.bygus.llywyrgofod;

import com.bygus.gameframework.DynamicGameObject;

public class Explosion extends DynamicGameObject 
{
	public static final float EXPLOSION_WIDTH = 2f;
    public static final float EXPLOSION_HEIGHT = 1f;
    public static final float EXPLOSION_SPEED = 1f;
    
    public float stateTime = 0;
    
	public Explosion(float x, float y)
	{
		super(x, y, EXPLOSION_WIDTH, EXPLOSION_HEIGHT);
		velocity.set(0-EXPLOSION_SPEED, 0);
	}
	
	public void update(float deltaTime)
	{
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);
		stateTime += deltaTime;
	}
}
