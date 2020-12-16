package com.example.zoorun;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MyDraw extends View {
    private Hero hero;
    private Ground ground;
    private Paint paint = new Paint();
    private boolean isFirst;


    public MyDraw(Context context, AttributeSet attrs) {
        super(context, attrs);
        isFirst = true;

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int RX = getWidth() / 1000;
        int RY = getHeight() / 1000;
        int COUNT_OF_LINES = 9;
        int DISTANCE = 15;
        int LEVEL = 3; // скорость земли в единицах "RX"
        int LINES_LENGTH = getHeight() / COUNT_OF_LINES - DISTANCE;
        if (isFirst) {
            fill(canvas, RX, RY, LINES_LENGTH, COUNT_OF_LINES, DISTANCE);
        }


        invalidate();
    }

    public void fill(Canvas canvas, int RX, int RY, int LINES_LENGTH, int COUNT_OF_LINES, int DISTANCE) {
        ground = new Ground(RX, RY, LINES_LENGTH, COUNT_OF_LINES, DISTANCE, 3 * RY);
        hero = new Hero();
    }
}
