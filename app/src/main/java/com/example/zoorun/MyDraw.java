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
    private boolean pause = true;


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
        float LEVEL = 0.0055f; // скорость земли    (от 0.003 до 0.008)
        float LINES_LENGTH = DISTANCE * 3f; // длина каждой линии
        if (isFirst) {
            isFirst = false;
            fill(canvas, RX, RY, LINES_LENGTH, COUNT_OF_LINES, DISTANCE, LEVEL);
        }
        ground.draw(canvas, paint, RX, RY);
        hero.draw(canvas, paint, RX, RY);
        if (!pause) {
            ground.move();
            hero.move();
        }
        invalidate();
    }

    public void fill(Canvas canvas, float RX, float RY, float LINES_LENGTH, int COUNT_OF_LINES, float DISTANCE, float LEVEL) {
        ground = new Ground(RX, RY, LINES_LENGTH, COUNT_OF_LINES, DISTANCE,  LEVEL);
        hero = new Hero(220f);
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }
}
