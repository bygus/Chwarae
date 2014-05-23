package com.bygus.llywyrgofod;

import com.bygus.gameframework.DynamicGameObject;
import com.bygus.gameframework.math.Vector2;

public class Saucer extends DynamicGameObject 
{
    static final int SAUCER_ALIVE = 0;
    static final int SAUCER_DEAD = 1;
    static final int SAUCER_BASIC = 0;
    static final int SAUCER_VERTICAL = 1;
    static final int SAUCER_HOMING = 2;
    static final int SAUCER_EXPLOSION_TIME = 2;
    
    public static final float SAUCER_WIDTH = 2f;
    public static final float SAUCER_HEIGHT = 1f;
    public float speed = 2f;
    
    public int type;
    
    public int state;
    public float stateTime = 0;
    public float velocityTime = 0;
    
	public Saucer(float x, float y, float speed, int type)
	{
		super(x, y, SAUCER_WIDTH, SAUCER_HEIGHT);
		state = SAUCER_ALIVE;
		this.type = type;
		this.speed = speed;
		if(type==SAUCER_VERTICAL)
			velocity.set(0-speed, speed);
		else
			velocity.set(0-speed, 0);
	}
	
	public void update(float deltaTime, Vector2 targetPosition)
	{
		if(type==SAUCER_BASIC)
			position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		else if(type==SAUCER_VERTICAL)
		{
			position.add(velocity.x * deltaTime, velocity.y * deltaTime);
			if(velocityTime > 2f)
			{ //reverse direction
				velocity.y = 0-velocity.y;
				velocityTime = 0;
			}
		}
		else if(type==SAUCER_HOMING)
		{
			Vector2 test = targetPosition.cpy();
			float secondsToImpact = test.dist(this.position) / this.speed;
			velocity.set((test.x - position.x)/secondsToImpact, (test.y - position.y)/secondsToImpact);
			position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		}			
        
		bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);
		stateTime += deltaTime;
		velocityTime += deltaTime;
	}
	
	public void kill()
	{
		this.state = SAUCER_DEAD;
		stateTime = 0;
	}
}
