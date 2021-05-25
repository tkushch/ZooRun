package com.example.zoorun.draw;

import android.graphics.*;
import com.example.zoorun.R;
import com.example.zoorun.interfaces.Drawable;
import com.example.zoorun.interfaces.Movable;

import java.nio.InvalidMarkException;

public class Barrier implements Movable, Drawable {
    private float x, y, dx, dy, RX, RY;
    private int way; // 0, 1 or 2;
    private float LEFT_LINE_TANGENS = -1631f / 4590f;
    private float RIGHT_LINE_TANGENS = -LEFT_LINE_TANGENS;
    private boolean relevance = false;
    private Bitmap image, image_fist;
    private int imgWidth, imgHeight;
    private int w, h;
    private float radius_x, radius_y, kW, kH;


    public Barrier(Bitmap yourBitmap, int way, float level, float RX, float RY) {
        image_fist = yourBitmap;
        imgWidth = yourBitmap.getWidth();
        imgHeight = yourBitmap.getHeight();
        kW = RX / 54;
        kH = kW / 2;

        this.RX = RX;
        this.RY = RY;
        radius_x = imgWidth * kW / 2;
        radius_y = imgHeight * kH / 2;


        this.way = way;
        x = 500f;
        y = -1500f / 7f;
        dy = level * 900f;
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
        Matrix matrix = new Matrix();
        matrix.setScale(kW, kH);
        matrix.postTranslate(RX * x - radius_x, RY * y - radius_y);
        paint.setAlpha(255);
        canvas.drawBitmap(image_fist, matrix, paint);
    }

    @Override
    public void move() {
        if (y - radius_y < 1000f) {
            x += dx;
            y += dy;
            if (y >= 835f) {
                dx *= 1.0035f;
                dy *= 1.0035f;
            }
//            масштабирование
            kW += 0.001f;
            kH += 0.001f;
            radius_x = imgWidth * kW / 2;
            radius_y = imgHeight * kH / 2;
//
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
        return radius_y / RY;
    }

    public int getWay() {
        return way;
    }

    public float getX() {
        return x;
    }
}
