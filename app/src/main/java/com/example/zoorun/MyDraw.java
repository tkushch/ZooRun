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
    private float RX;// условные единицы экрана 1000*1000
    private float RY;// условные единицы экрана 1000*1000
    private int COUNT_OF_LINES = 1000; // максимальное количество линий в ряду
    private float DISTANCE = 3f; // расстояние между абсциссами линий
    private float LEVEL = 0.0055f; // скорость земли    (от 0.003 до 0.008)
    private float LINES_LENGTH = DISTANCE * 3f; // длина каждой линии


    public MyDraw(Context context, AttributeSet attrs) {
        super(context, attrs);
        isFirst = true;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RX = canvas.getWidth() / 1000f; // условные единицы экрана 1000*1000
        RY = canvas.getHeight() / 1000f; // условные единицы экрана 1000*1000

        if (isFirst) {
            isFirst = false;
            fill(canvas);
        }
        ground.draw(canvas, paint, RX, RY);
        hero.draw(canvas, paint, RX, RY);
        if (!pause) {
            ground.move();
            //hero.jump();           ----------------возможно удалим функцию jump-----
            if (hero.isInSwipe()) {
                hero.swipe_move();
            }
        }
        invalidate();
    }

    public void fill(Canvas canvas) {
        ground = new Ground(RX, RY, LINES_LENGTH, COUNT_OF_LINES, DISTANCE, LEVEL);
        hero = new Hero(LEVEL);
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public void swipe_left() {
        if (!hero.isInSwipe()) {
            hero.swipe_left();
        }
    }

    public void swipe_right() {
        if (!hero.isInSwipe()) {
            hero.swipe_right();
        }
    }

    /*
    public void swipe_up() {
    }

    public void swipe_down() {
    }
     */

    public float getRX() {
        return RX;
    }

    public void setRX(float RX) {
        this.RX = RX;
    }

    public float getRY() {
        return RY;
    }

    public void setRY(float RY) {
        this.RY = RY;
    }

    public float getLEVEL() {
        return LEVEL;
    }

    public void setLEVEL(float LEVEL) {
        this.LEVEL = LEVEL;
    }
}
