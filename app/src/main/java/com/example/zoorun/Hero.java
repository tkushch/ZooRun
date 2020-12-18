package com.example.zoorun;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import static android.graphics.Color.rgb;

public class Hero implements Movable, Drawable {
    private float size;
    private float x0, y0, x1, y1;
    private float halfsize;
    private boolean move_to_left = true;
    private int counter_moving = 0;
    //private Bitmap image;

    public Hero(float size) {
        this.size = size;
        this.halfsize = 0.5f * size;
        x0 = 500f - halfsize;
        y0 = 800f;
        x1 = 500 + halfsize;
        y1 = 800f + halfsize;
    }

    @Override
    public void move() {
        if (counter_moving < 5) {
            counter_moving++;
        } else {
            if (move_to_left) {
                x0 += 5f;
                x1 += 5f;
            }
            else{
                x0 -= 5f;
                x1 -= 5f;
            }
            counter_moving = 0;
            move_to_left = !move_to_left;
        }
    }


    @Override
    public void draw(Canvas canvas, Paint paint, float RX, float RY) {
        paint.setColor(Color.YELLOW);
        canvas.drawRect(RX * x0, RY * y0, RX * x1, RY * y1, paint);
    }
}
