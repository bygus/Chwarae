package com.bygus.llywyrgofod;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.bygus.gameframework.Game;
import com.bygus.gameframework.Input.TouchEvent;
import com.bygus.gameframework.android.GLScreen;
import com.bygus.gameframework.gl.Camera2D;
import com.bygus.gameframework.gl.FPSCounter;
import com.bygus.gameframework.gl.SpriteBatcher;
import com.bygus.gameframework.math.OverlapTester;
import com.bygus.gameframework.math.Rectangle;
import com.bygus.gameframework.math.Vector2;
import com.bygus.llywyrgofod.World.WorldListener;

public class GameScreen extends GLScreen {
    static final int GAME_READY = 0;    
    static final int GAME_RUNNING = 1;
    static final int GAME_PAUSED = 2;
    static final int GAME_LEVEL_END = 3;
    static final int GAME_OVER = 4;
  
    int state;
    Camera2D guiCam;
    Vector2 touchPoint;
    SpriteBatcher batcher;    
    Rectangle pauseBounds;
    Rectangle resumeBounds;
    Rectangle upBounds;
    Rectangle downBounds;
    Rectangle fireBounds;
    Rectangle quitBounds; 
    World world;
    LlywyrRenderer renderer;
    

    public GameScreen(Game game) {
        super(game);
        state = GAME_RUNNING;
        guiCam = new Camera2D(glGraphics, 480, 320);
        touchPoint = new Vector2();
        batcher = new SpriteBatcher(glGraphics, 1000);
        world = new World();
        renderer = new LlywyrRenderer(glGraphics, batcher, world);
        
        upBounds = new Rectangle(10, 42, 32, 32);
        downBounds = new Rectangle(10, 0, 32, 32);
        fireBounds = new Rectangle(480 - 64, 0, 64, 64); // make the bounds a bit big !
    }

    @Override
    public void update(float deltaTime) {
        if(deltaTime > 0.1f)
            deltaTime = 0.1f;
        
        switch(state) {
        case GAME_READY:
            updateReady();
            break;
        case GAME_RUNNING:
            updateRunning(deltaTime);
            break;
        case GAME_PAUSED:
            updatePaused();
            break;
        case GAME_LEVEL_END:
            updateLevelEnd();
            break;
        case GAME_OVER:
            updateGameOver();
            break;
        }
    }

    private void updateReady() {
        if(game.getInput().getTouchEvents().size() > 0) {
            state = GAME_RUNNING;
        }
    }

    private void updateRunning(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP)
            {
            	world.spaceship.stop();
                continue;
            }
            touchPoint.set(event.x, event.y);
            guiCam.touchToWorld(touchPoint);
            
            // Process touch event
            if(OverlapTester.pointInRectangle(upBounds, touchPoint)) 
            {
            	world.spaceship.up();
            }
            if(OverlapTester.pointInRectangle(downBounds, touchPoint)) {
            	world.spaceship.down();
            }
            if(OverlapTester.pointInRectangle(fireBounds, touchPoint)) 
            {
            	world.fire();
            }
        }
        
        // update the world
        world.update(deltaTime);
        
    }

    private void updatePaused() {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type != TouchEvent.TOUCH_UP)
                continue;
            
            touchPoint.set(event.x, event.y);
            guiCam.touchToWorld(touchPoint);
            
            if(OverlapTester.pointInRectangle(resumeBounds, touchPoint)) {
                //Assets.playSound(Assets.clickSound);
                state = GAME_RUNNING;
                return;
            }
            
            if(OverlapTester.pointInRectangle(quitBounds, touchPoint)) {
                //Assets.playSound(Assets.clickSound);
                game.setScreen(new MainMenuScreen(game));
                return;
            
            }
        }
    }

    private void updateLevelEnd() {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {                   
            TouchEvent event = touchEvents.get(i);
            if(event.type != TouchEvent.TOUCH_UP)
                continue;
        //    world = new World(worldListener);
        //    renderer = new WorldRenderer(glGraphics, batcher, world);
        //    world.score = lastScore;
            state = GAME_READY;
        }
    }

    private void updateGameOver() {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {                   
            TouchEvent event = touchEvents.get(i);
            if(event.type != TouchEvent.TOUCH_UP)
                continue;
            game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override
    public void present(float deltaTime) {
        GL10 gl = glGraphics.getGL();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        
        renderer.render();
        
        guiCam.setViewportAndMatrices();
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        batcher.beginBatch(Assets.items);
        switch(state) {
        case GAME_READY:
            presentReady();
            break;
        case GAME_RUNNING:
            presentRunning();
            break;
        case GAME_PAUSED:
            presentPaused();
            break;
        case GAME_LEVEL_END:
            presentLevelEnd();
            break;
        case GAME_OVER:
            presentGameOver();
            break;
        }
        batcher.endBatch();
        gl.glDisable(GL10.GL_BLEND);
    }

    private void presentReady() {
//        batcher.drawSprite(160, 240, 192, 32, Assets.ready);
    }

    private void presentRunning() {
        batcher.drawSprite(26, 58, 32, 32, Assets.upButton);
        batcher.drawSprite(26, 16, 32, 32, Assets.downButton);
        batcher.drawSprite(480 - 16, 16, 32, 32, Assets.fireButton);
    }

    private void presentPaused() {        
//        batcher.drawSprite(160, 240, 192, 96, Assets.pauseMenu);
//        Assets.font.drawText(batcher, scoreString, 16, 480-20);
    }

    private void presentLevelEnd() {
        /*String topText = "the princess is ...";
        String bottomText = "in another castle!";
        float topWidth = Assets.font.glyphWidth * topText.length();
        float bottomWidth = Assets.font.glyphWidth * bottomText.length();
        Assets.font.drawText(batcher, topText, 160 - topWidth / 2, 480 - 40);
        Assets.font.drawText(batcher, bottomText, 160 - bottomWidth / 2, 40);*/
    }

    private void presentGameOver() {
    /*    batcher.drawSprite(160, 240, 160, 96, Assets.gameOver);        
        float scoreWidth = Assets.font.glyphWidth * scoreString.length();
        Assets.font.drawText(batcher, scoreString, 160 - scoreWidth / 2, 480-20);*/
    }

    @Override
    public void pause() {
        if(state == GAME_RUNNING)
            state = GAME_PAUSED;
    }

    @Override
    public void resume() {        
    }

    @Override
    public void dispose() {       
    }
}
