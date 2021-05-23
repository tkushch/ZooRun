package com.example.zoorun.draw;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.example.zoorun.interfaces.Drawable;
import com.example.zoorun.interfaces.Movable;


public class Coin implements Movable, Drawable {
    private float x, y, dx, dy, radius;
    private int way; // 0, 1 or 2;
    private float LEFT_LINE_TANGENS = -1631f / 4590f;
    private float RIGHT_LINE_TANGENS = -LEFT_LINE_TANGENS;
    private boolean relevance = false;
    private Bitmap image;


    public Coin(Bitmap image, int way, float level, Canvas canvas) {
        this.way = way;
        x = 500f;
        y = -1500f / 7f;
        radius = 10f;
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
        image = Bitmap.createScaledBitmap(image, (int) (image.getWidth() * 0.07), (int) (image.getHeight() * 0.07), true);
        this.image = image;
        relevance = true;
    }

    @Override
    public void draw(Canvas canvas, Paint paint, float RX, float RY) {
        paint.setColor(Color.YELLOW);
//        canvas.drawCircle(x * RX, y * RY, radius * RX, paint);
        canvas.drawBitmap(image, RX * x - (image.getWidth() / 2.0f), RY * y - (image.getHeight() / 2.0f), paint);

    }

    @Override
    public void move() {
        if (y - radius < 1000f) {
            x += dx;
            y += dy;
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

    public void setRelevance(boolean relevance) {
        this.relevance = relevance;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius;
    }

    public int getWay() {
        return way;
    }

    public float getX() {
        return x;
    }
}
