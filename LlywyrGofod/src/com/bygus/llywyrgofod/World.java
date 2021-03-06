package com.bygus.llywyrgofod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import android.util.Log;

import com.bygus.gameframework.math.OverlapTester;
import com.bygus.gameframework.math.Vector2;
import com.bygus.llywyrgofod.old.Explosion;

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
    public List<Explosion> explosions;
    //public final WorldListener listener;
    public final Random rand;

    public int state;

    public World() {
        this.spaceship = new Spaceship(2, 5); 
        this.bullets = new ArrayList<Bullet>();
        this.saucers = getSaucers();
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
    	if(saucers.size()==0)
    	{
    		this.state=WORLD_STATE_NEXT_LEVEL;
    	}
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
    	Iterator<Saucer> saucerIt = saucers.iterator();
    	while(saucerIt.hasNext())
		{
			Saucer s = saucerIt.next();
    		s.update(deltaTime, spaceship.position);
    		if(s.state==Saucer.SAUCER_DEAD && s.stateTime > Saucer.SAUCER_EXPLOSION_TIME)
    		{
    			saucerIt.remove();
    		}
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
    	Iterator<Saucer> saucerIt = saucers.iterator();
    	while(saucerIt.hasNext())
		{
			Saucer s = saucerIt.next();
			if(s.state==Saucer.SAUCER_ALIVE)
			{
				
				if(OverlapTester.overlapRectangles(s.bounds, this.spaceship.bounds))
	    		{
	    	    	// Check saucers vs Spaceship
	    			this.state = WORLD_STATE_GAME_OVER;
	    			// And explode !!
	    			
	    		}
				// if saucer leaves left hand side, remove it
				if(s.position.x<=0-(Saucer.SAUCER_WIDTH/2))
        		{
        			saucerIt.remove();
        			continue;
        		}
	    		// Need a SpatialHashGrid !!
				Iterator<Bullet> bulletIt = bullets.iterator();
	    		while(bulletIt.hasNext())
	    		{
	    			Bullet b = bulletIt.next();
	    			//Log.d(World.class.getSimpleName(), "CheckCollision " + b.bounds.debugText());
	        		if(OverlapTester.overlapRectangles(s.bounds, b.bounds))
	        		{
	        			// remove saucer
	        			s.kill();
	        			bulletIt.remove();   			
	        		}
	        		if(b.position.x>=WORLD_WIDTH)
	        		{
	        			bulletIt.remove();
	        		}
	    		}
			}
		} 	
    }
    
    private List<Saucer> getSaucers()
    {
    	List<Saucer> saucers = new ArrayList<Saucer>();
        saucers.add(new Saucer(13, 4, 3f, Saucer.SAUCER_BASIC));
        saucers.add(new Saucer(17, 2, 3f, Saucer.SAUCER_BASIC));
        saucers.add(new Saucer(17, 8, 3f, Saucer.SAUCER_BASIC));
        saucers.add(new Saucer(20, 5, 3f, Saucer.SAUCER_BASIC));
        
        saucers.add(new Saucer(25, 1, 3f, Saucer.SAUCER_BASIC));
        saucers.add(new Saucer(26, 3, 3f, Saucer.SAUCER_BASIC));
        saucers.add(new Saucer(27, 5, 3f, Saucer.SAUCER_BASIC));
        saucers.add(new Saucer(26, 7, 3f, Saucer.SAUCER_BASIC));
        saucers.add(new Saucer(25, 9, 3f, Saucer.SAUCER_BASIC));
        
        saucers.add(new Saucer(30, 2, 3f, Saucer.SAUCER_VERTICAL));
        saucers.add(new Saucer(30, 5, 3f, Saucer.SAUCER_VERTICAL));
        saucers.add(new Saucer(30, 8, 3f, Saucer.SAUCER_VERTICAL));
        
        saucers.add(new Saucer(35, 5, 3f, Saucer.SAUCER_HOMING));
        saucers.add(new Saucer(36, 1, 3f, Saucer.SAUCER_HOMING));
        saucers.add(new Saucer(37, 7, 3f, Saucer.SAUCER_HOMING));
        return saucers;
    }
}
