package com.bygus.androidtestch7.glbasics;

import com.bygus.androidtestch7.framework.GameObject;

public class Cannon extends GameObject {
    public float angle;
    
    public Cannon(float x, float y, float width, float height) {
        super(x, y, width, height);
        angle = 0;
    }
}
