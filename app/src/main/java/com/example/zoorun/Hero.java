package com.example.zoorun;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import static android.graphics.Color.rgb;

public class Hero implements Movable, Drawable {
    private float size;
    private float halfsize;
    //private Bitmap image;

    public Hero(float size) {
        this.size = size;
        this.halfsize = 0.5f * size;
    }

    @Override
    public void move() {


    }


    @Override
    public void draw(Canvas canvas, Paint paint, float RX, float RY) {
        paint.setColor(rgb(128, 50, 50));
        canvas.drawRect(RX * 500 - halfsize, RY * 830f - halfsize, RX * 500 + halfsize, RY * 830f + halfsize, paint);
    }
}
