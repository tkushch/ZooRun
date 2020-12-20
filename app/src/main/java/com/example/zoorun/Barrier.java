package com.example.zoorun;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Barrier implements Movable, Drawable{
    private float x, y, dx, dy, radius;
    private int way; // 0, 1 or 2;
    private float LEFT_LINE_TANGENS = -1631f/4590f;
    private float RIGHT_LINE_TANGENS = -LEFT_LINE_TANGENS;


    public Barrier(int way, float level) {
        this.way = way;
        x = 500f;
        y = -1500f/7f;
        radius = 50f;
        dy = level * 1000f;
        switch (way){
            case 0:
                dx = LEFT_LINE_TANGENS * dy;
                break;
            case 1:
                dx = 0;
                break;
            case 2:
                dx = RIGHT_LINE_TANGENS * dy;
                break;
        }
    }

    @Override
    public void draw(Canvas canvas, Paint paint, float RX, float RY) {
        paint.setColor(Color.WHITE);
        canvas.drawCircle(x * RX, y * RY, radius * RX, paint);
    }

    @Override
    public void move() {
        x += dx;
        y += dy;
    }

    public float left_NK(float x){
        return ((-4590f)/(1631f) * x + ((1945500f)/(1631f)));
    }
    public float right_MP(float x){
        return ((4590f)/(1631f) * x - (2644500f)/(1631f));
    }

}
