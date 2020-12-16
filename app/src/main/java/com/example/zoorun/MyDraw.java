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
    boolean isFirst;



    MyDraw(Context context, AttributeSet attrs) {
        super(context, attrs);
        isFirst = true;

    }


    @Override
    protected void onDraw(Canvas canvas) {
        int RX = getWidth() / 1000;
        int RY = getHeight() / 1000;
        if (isFirst){
            fill(canvas, RX, RY);
        }




        invalidate();
    }

    protected void fill(Canvas canvas, int RX, int RY){
        
    }
}
