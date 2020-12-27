package com.example.zoorun;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class Coin implements Movable, Drawable {
    private float x, y, dx, dy, radius;
    private int way; // 0, 1 or 2;
    private float LEFT_LINE_TANGENS = -1631f / 4590f;
    private float RIGHT_LINE_TANGENS = -LEFT_LINE_TANGENS;
    private boolean relevance = false;
    private Bitmap image;


    public Coin(Bitmap image, int way, float level) {
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
        this.image = image;
        relevance = true;
    }

    @Override
    public void draw(Canvas canvas, Paint paint, float RX, float RY) {
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(x * RX, y * RY, radius * RX, paint);

        /*Matrix matrix = new Matrix();
        //matrix.setScale(100f * RX / image.getWidth(), 100f * RY / image.getHeight());
        matrix.setScale(0.5f, 0.5f);
        matrix.postTranslate((x - radius_x) * RX , (y - radius_y) * RY);
        paint.setAlpha(255);
        canvas.drawBitmap(image, matrix, new Paint());

         */
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
