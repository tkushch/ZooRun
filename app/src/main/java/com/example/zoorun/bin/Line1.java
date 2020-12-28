package com.example.zoorun.bin;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.example.zoorun.Drawable;
import com.example.zoorun.Movable;

public class Line1 implements Movable, Drawable {
    private float x, y, dx, dy, radius = 5f;
    private int way; // 0, 1
    private float LEFT_LINE_TANGENS = -49f / 255f;
    private float RIGHT_LINE_TANGENS = -LEFT_LINE_TANGENS;


    public Line1(int way, float level, float x, float y) {
        this.x = x;
        this.y = y;
        this.way = way;
        dy = level * 1000f;
        switch (way) {
            case 0:
                dx = LEFT_LINE_TANGENS * dy;
                break;
            case 1:
                dx = RIGHT_LINE_TANGENS * dy;
                break;
        }
    }

    @Override
    public void draw(Canvas canvas, Paint paint, float RX, float RY) {
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(x * RX, y * RY, radius * RX, paint);

    }

    @Override
    public void move() {
        if (y - radius < 1000f) {
            x += dx;
            y += dy;
            //if (y >= 835f) {
            //    dx *= 1.0035f;
            //    dy *= 1.0035f;
            //}
        } else {
            x = 500f;
            y = -1500f / 7f;
        }
    }


    @Override
    public String toString() {
        return String.valueOf(x) + " " + String.valueOf(y);
    }
}
