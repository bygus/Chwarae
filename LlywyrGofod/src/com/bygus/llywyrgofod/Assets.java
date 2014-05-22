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
    public static TextureRegion spaceship;
    public static TextureRegion upButton;
    public static TextureRegion downButton;
    public static TextureRegion fireButton;
    public static TextureRegion bullet;
    
    public static Animation saucer;
    
    public static void load(GLGame game) {
        background = new Texture(game, "background.png");
        backgroundRegion = new TextureRegion(background, 0, 0, 480, 320);
        
        items = new Texture(game, "items.png");        
        playButton = new TextureRegion(items, 0, 45, 145, 36);     
        spaceship = new TextureRegion(items, 0, 0, 48, 48);
        upButton = new TextureRegion(items, 0, 80, 32, 32);
        downButton = new TextureRegion(items, 34, 80, 32, 32);
        fireButton = new TextureRegion(items, 68, 80, 32, 32);
        bullet = new TextureRegion(items, 0, 112, 16, 10);
        
        saucer = new Animation(0.2f,
                new TextureRegion(items, 0, 124, 64, 32),
                new TextureRegion(items, 64, 124, 64, 32),
                new TextureRegion(items, 128, 124, 64, 32),
                new TextureRegion(items, 192, 124, 64, 32));
    }       

    public static void reload() {
        background.reload();
        items.reload();
        //if(Settings.soundEnabled)
        //    music.play();
    }

    public static void playSound(Sound sound) {
        //if(Settings.soundEnabled)
        //    sound.play(1);
    }
}
