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
        float RX = getWidth() / 1000f; // условные единицы экрана 1000*1000
        float RY = getHeight() / 1000f; // условные единицы экрана 1000*1000
        int COUNT_OF_LINES = 1000; // максимальное количество линий в ряду
        float DISTANCE = 3f; // расстояние между абсциссами линий
        float LEVEL = 3f; // скорость земли
        float LINES_LENGTH = DISTANCE * 3f; // длина каждой линии
        if (isFirst) {
            fill(canvas, RX, RY, LINES_LENGTH, COUNT_OF_LINES, DISTANCE);
        }
        ground.draw(canvas, paint, RX, RY);
        hero.draw(canvas, paint, RX, RY);
        invalidate();
    }

    public void fill(Canvas canvas, float RX, float RY, float LINES_LENGTH, int COUNT_OF_LINES, float DISTANCE) {
        ground = new Ground(RX, RY, LINES_LENGTH, COUNT_OF_LINES, DISTANCE, RY * 3f);
        hero = new Hero(250f);
    }
}
