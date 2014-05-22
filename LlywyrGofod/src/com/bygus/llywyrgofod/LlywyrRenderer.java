package com.bygus.llywyrgofod;

import javax.microedition.khronos.opengles.GL10;

import com.bygus.gameframework.android.GLGraphics;
import com.bygus.gameframework.gl.Animation;
import com.bygus.gameframework.gl.Camera2D;
import com.bygus.gameframework.gl.SpriteBatcher;
import com.bygus.gameframework.gl.TextureRegion;

public class LlywyrRenderer {
    static final float FRUSTUM_WIDTH = 15;
    static final float FRUSTUM_HEIGHT = 10;    
    GLGraphics glGraphics;
    World world;
    Camera2D cam;
    SpriteBatcher batcher;
    
    public LlywyrRenderer(GLGraphics glGraphics, SpriteBatcher batcher, World world) {
        this.glGraphics = glGraphics;
        this.world = world;
        this.cam = new Camera2D(glGraphics, FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        this.batcher = batcher;        
    }
    
    public void render() {
    //    if(world.spaceship.position.y > cam.position.y )
    //        cam.position.y = world.spaceship.position.y;
        cam.setViewportAndMatrices();
        renderBackground();
        renderObjects();        
    }

    public void renderBackground() {
        batcher.beginBatch(Assets.background);
        batcher.drawSprite(cam.position.x, cam.position.y,
                           FRUSTUM_WIDTH, FRUSTUM_HEIGHT, 
                           Assets.backgroundRegion);
        batcher.endBatch();
    }

    public void renderObjects() {
        GL10 gl = glGraphics.getGL();
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        
        batcher.beginBatch(Assets.items);
        renderSpaceship();
        renderBullets();
        renderSaucers();
        batcher.endBatch();
        gl.glDisable(GL10.GL_BLEND);
    }

    private void renderSpaceship() {
        Spaceship ship = world.spaceship;
        batcher.drawSprite(ship.position.x, ship.position.y, 1.5f, 1.5f, Assets.spaceship);
    }
    
    private void renderBullets() 
    {
        for(Bullet b : world.bullets)
        {
        	batcher.drawSprite(b.position.x, b.position.y, 0.5f, 0.25f, Assets.bullet);
        }
    }
    
    private void renderSaucers()
    {
    	TextureRegion keyFrame;
    	for(Saucer s : world.saucers)
    	{
    		keyFrame = Assets.saucer.getKeyFrame(s.stateTime, Animation.ANIMATION_LOOPING);
    		batcher.drawSprite(s.position.x, s.position.y, Saucer.SAUCER_WIDTH, Saucer.SAUCER_HEIGHT, keyFrame);
    	}
    }
}

