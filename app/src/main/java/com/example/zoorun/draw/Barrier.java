package com.example.zoorun.draw;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.example.zoorun.interfaces.Drawable;
import com.example.zoorun.interfaces.Movable;

public class Barrier implements Movable, Drawable {
    private float x, y, dx, dy, radius;
    private int way; // 0, 1 or 2;
    private float LEFT_LINE_TANGENS = -1631f / 4590f;
    private float RIGHT_LINE_TANGENS = -LEFT_LINE_TANGENS;
    private boolean relevance = false;
    private Bitmap image;
    private int w, h;
    private float radius_x, radius_y;



    public Barrier(Canvas canvas, Bitmap yourBitmap, int way, float level) {


        w = (int) (yourBitmap.getWidth() * (canvas.getWidth() / 6000f));
        h = (int) (0.6f * w);
        image = Bitmap.createScaledBitmap(yourBitmap, w, h, true);
        float RX, RY;
        RX = canvas.getWidth() / 1000f;
        RY = canvas.getHeight() / 1000f;
        radius_x = w / 2f / RX;
        radius_y = h / 2f / RY;


        this.way = way;
        x = 500f;
        y = -1500f / 7f;
        radius = y * 2f;
        dy = level * 1000f;
        switch (way) {
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
        relevance = true;
    }

    @Override
    public void draw(Canvas canvas, Paint paint, float RX, float RY) {
        paint.setColor(Color.WHITE);
        canvas.drawBitmap(image, RX * (x - radius_x), RY * (y - radius_y), paint);

//        canvas.drawCircle(x * RX, y * RY, radius * RX, paint);
    }

    @Override
    public void move() {
        if (y - radius < 1000f) {
            x += dx;
            y += dy;
            if (y < 500f) {
                radius = Math.round(Math.sqrt(y) + 5f);
            }

            //else{
            //    radius += 0.0006f * y;
            //}
            if (y >= 835f) {
                dx *= 1.0035f;
                dy *= 1.0035f;
            }
        } else {
            relevance = false;
        }
    }

    public boolean isRelevance() {
        return relevance;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius_y;
    }

    public int getWay() {
        return way;
    }

    public float getX() {
        return x;
    }
}
