package com.bygus.llywyrgofod;

import com.bygus.gameframework.Music;
import com.bygus.gameframework.Sound;
import com.bygus.gameframework.android.GLGame;
import com.bygus.gameframework.gl.Animation;
import com.bygus.gameframework.gl.Font;
import com.bygus.gameframework.gl.Texture;
import com.bygus.gameframework.gl.TextureRegion;

public class Assets {
    public static Texture background;
    public static TextureRegion backgroundRegion;
    public static Texture items;
    public static TextureRegion playButton;
    public static TextureRegion gameOver;
    public static TextureRegion pauseMenu;
    public static TextureRegion spaceship;
    public static TextureRegion upButton;
    public static TextureRegion downButton;
    public static TextureRegion fireButton;
    public static TextureRegion bullet;
    public static Texture explosionTexture;
    public static Animation explosionAnim;
    public static Font font;
    
    public static Animation saucer;
    
    public static void load(GLGame game) {
        background = new Texture(game, "backgroundmolly1.png");
        backgroundRegion = new TextureRegion(background, 0, 0, 480, 320);
        
        items = new Texture(game, "items.png");        
        playButton = new TextureRegion(items, 0, 45, 145, 36);  
        pauseMenu = new TextureRegion(items, 0, 150, 192, 96);
        gameOver = new TextureRegion(items, 0, 232, 160, 89);
        spaceship = new TextureRegion(items, 48, 0, 64, 32);
        upButton = new TextureRegion(items, 0, 80, 32, 32);
        downButton = new TextureRegion(items, 34, 80, 32, 32);
        fireButton = new TextureRegion(items, 68, 80, 32, 32);
        bullet = new TextureRegion(items, 0, 112, 16, 10);
        
        saucer = new Animation(0.2f,
                new TextureRegion(items, 3, 124, 57, 26),
                new TextureRegion(items, 66, 124, 57, 26),
                new TextureRegion(items, 130, 124, 57, 26),
                new TextureRegion(items, 194, 124, 57, 26));
        
        explosionTexture = new Texture(game, "explode.png");
        
        TextureRegion[] keyFrames = new TextureRegion[16];
        int frame = 0;
        for (int y = 0; y < 256; y += 64) {
            for (int x = 0; x < 256; x += 64) {
                keyFrames[frame++] = new TextureRegion(explosionTexture, x, y, 64, 64);
            }
        }
        explosionAnim = new Animation(0.1f, keyFrames);
        
        font = new Font(items, 224, 0, 16, 16, 20);
    }       

    public static void reload() {
        background.reload();
        items.reload();
        explosionTexture.reload();
        //if(Settings.soundEnabled)
        //    music.play();
    }

    public static void playSound(Sound sound) {
        //if(Settings.soundEnabled)
        //    sound.play(1);
    }
}
