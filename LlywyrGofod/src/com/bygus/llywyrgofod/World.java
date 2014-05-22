package com.bygus.llywyrgofod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.bygus.gameframework.math.OverlapTester;
import com.bygus.gameframework.math.Vector2;

public class World {
    public interface WorldListener {
        public void jump();
        public void highJump();
        public void hit();
        public void coin();
    }

    public static final float WORLD_WIDTH = 15;
    public static final float WORLD_HEIGHT = 10;    
    public static final int WORLD_STATE_RUNNING = 0;
    public static final int WORLD_STATE_NEXT_LEVEL = 1;
    public static final int WORLD_STATE_GAME_OVER = 2;
    public static final Vector2 gravity = new Vector2(0, -12);

    public final Spaceship spaceship;
    public List<Bullet> bullets;
    public List<Saucer> saucers;
    //public final WorldListener listener;
    public final Random rand;

    public int state;

    public World() {
        this.spaceship = new Spaceship(2, 5); 
        this.bullets = new ArrayList<Bullet>();
        this.saucers = new ArrayList<Saucer>();
        this.saucers.add(	new Saucer(13, 4));
        this.saucers.add(new Saucer(17, 2));
        this.saucers.add(new Saucer(17, 8));
        this.saucers.add(new Saucer(20, 5));
        //this.listener = listener;
        rand = new Random();

        this.state = WORLD_STATE_RUNNING;
        
    }

    public void update(float deltaTime) 
    {
    	updateSpaceship(deltaTime);
    	updateBullets(deltaTime);
    	updateSaucers(deltaTime);
    	checkCollisions();
    }
 
    public void fire()
    {
    	if(spaceship.canFire())
    		bullets.add(spaceship.fireBullet());
    }
    
    private void updateBullets(float deltaTime)
    {
    	int len = bullets.size();
    	for(int i = 0; i<len;i++)
    	{
    		bullets.get(i).update(deltaTime);
    	}
    }
    
    private void updateSaucers(float deltaTime)
    {
    	int len = saucers.size();
    	for(int i = 0; i<len;i++)
    	{
    		saucers.get(i).update(deltaTime);
    	}
    }
    
    private void updateSpaceship(float deltaTime)
    {
    	spaceship.update(deltaTime);
    	if(spaceship.position.y > WORLD_HEIGHT - Spaceship.SHIP_HEIGHT)
    		spaceship.position.y = WORLD_HEIGHT - Spaceship.SHIP_HEIGHT;
    	if(spaceship.position.y < 0 + Spaceship.SHIP_HEIGHT)
    		spaceship.position.y = Spaceship.SHIP_HEIGHT;
    }
    
    private void checkCollisions()
    {
    	
    	// Check saucers vs bullets
    	for(Saucer s : this.saucers)
    	{
    		if(OverlapTester.overlapRectangles(s.bounds, this.spaceship.bounds))
    		{
    	    	// Check saucers vs Spaceship
    			this.state = WORLD_STATE_GAME_OVER;
    			// And explode !!
    			
    		}
    		// Need a SpatialHashGrid !!
    		for(Bullet b : bullets)
    		{
        		if(OverlapTester.overlapRectangles(s.bounds, b.bounds))
        		{
        			// And explode !!
        			
        		}
    		}
    	}    	
    }
}
