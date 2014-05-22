package com.bygus.androidtestch7.framework;

import com.bygus.androidtestch7.framework.Graphics.PixmapFormat;

public interface Pixmap {
    public int getWidth();

    public int getHeight();

    public PixmapFormat getFormat();

    public void dispose();
}
