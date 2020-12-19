package com.example.zoorun;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Barrier implements Movable, Drawable{
    private float radius, v, x, y; //радиус для каждого следует умножать на коэф K(x);
    private int way; // 0, 1 or 2;


    @Override
    public void draw(Canvas canvas, Paint paint, float RX, float RY) {

    }

    @Override
    public void move() {

    }
}
