package com.secularsurfer.android.framework;

import com.secularsurfer.android.framework.Graphics.PixmapFormat;

public interface Pixmap {

    public int getWidth();

    public int getHeight();

    public PixmapFormat getFormat();

    public void dispose();
}
