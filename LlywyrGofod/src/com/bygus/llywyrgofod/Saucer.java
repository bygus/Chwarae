package com.bygus.llywyrgofod;

import com.bygus.gameframework.DynamicGameObject;

public class Saucer extends DynamicGameObject 
{
    static final int SAUCER_ALIVE = 0;
    static final int SAUCER_DEAD = 1;
    
    public static final float SAUCER_WIDTH = 2f;
    public static final float SAUCER_HEIGHT = 1f;
    public static final float SAUCER_SPEED = 2f;
    
    public int state;
    public float stateTime = 0;
    
	public Saucer(float x, float y)
	{
		super(x, y, SAUCER_WIDTH, SAUCER_HEIGHT);
		velocity.set(0-SAUCER_SPEED, 0);
		state = SAUCER_ALIVE;
	}
	
	public void update(float deltaTime)
	{
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);
		stateTime += deltaTime;
	}
	
	public void kill()
	{
		this.state = SAUCER_DEAD;
		stateTime = 0;
	}
}
