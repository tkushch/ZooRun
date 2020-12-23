package com.example.zoorun;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import static java.lang.Float.max;


public class MyDraw extends View {
    private boolean OFF;
    private OnCollisionListener onCollisionListener;
    private Collision collision;
    private boolean was_collision = false;
    private Hero hero;
    private Ground ground;
    private Barrier[] barriers;
    private int barrier_delay = 0;
    private Paint paint = new Paint();
    private boolean isFirst;
    private boolean pause = true;
    private float RX;// условные единицы экрана 1000*1000
    private float RY;// условные единицы экрана 1000*1000
    private int COUNT_OF_LINES = 1000; // максимальное количество линий в ряду
    private float DISTANCE = 3f; // расстояние между абсциссами линий
    private float LEVEL = 0.0065f; // скорость земли    (от 0.003 до 0.008)
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

        //fill
        if (isFirst) {
            isFirst = false;
            fill();
        }

        //draw
        ground.draw(canvas, paint, RX, RY);
        hero.draw(canvas, paint, RX, RY);
        for (int i = 0; i < barriers.length; i++) {
            Barrier b = barriers[i];
            if (b != null) {
                if (b.isRelevance()) {
                    b.draw(canvas, paint, RX, RY);
                }
            }
        }

        //move
        if (!pause && !was_collision) {
            ground.move();

            if (hero.isInSwipe()) {
                hero.swipe_move();
            } else {
                hero.jump();
            }

            for (int i = 0; i < barriers.length; i++) {
                Barrier b = barriers[i];
                if (b != null) {
                    if (b.isRelevance()) {
                        b.move();
                        check_collision(b, i);
                    }
                }
            }
            generate_barriers();
        } else if (was_collision) {
            collision.draw(canvas, paint, RX, RY);
            collision.move();
            if (collision.getR() > 1000f) {
                onCollisionListener.onCollision();
            }
        }
        if (!OFF) {
            invalidate();
        }
    }

    public void fill() {
        ground = new Ground(LINES_LENGTH, COUNT_OF_LINES, DISTANCE, LEVEL);
        hero = new Hero(LEVEL);
        barriers = new Barrier[100];

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

    public float getRY() {
        return RY;
    }

    public void generate_barriers() {
        if (barrier_delay < 70) {
            barrier_delay++;
        } else {
            barrier_delay = 0;
            int w1 = rand_0_3();
            int w2 = rand_0_3();
            while (w2 == w1) {
                w2 = rand_0_3();
            }
            for (int i = 0; i < barriers.length; i++) {
                boolean flag = false;
                if (barriers[i] == null) {
                    flag = true;
                } else if (!barriers[i].isRelevance()) {
                    flag = true;
                }
                if (flag) {
                    barriers[i] = new Barrier(w1, LEVEL);
                    break;
                }
            }
            for (int i = 0; i < barriers.length; i++) {
                boolean flag = false;
                if (barriers[i] == null) {
                    flag = true;
                } else if (!barriers[i].isRelevance()) {
                    flag = true;
                }
                if (flag) {
                    barriers[i] = new Barrier(w2, LEVEL);
                    break;
                }
            }

        }
    }

    public int rand_0_3() {
        return (Integer.parseInt(Character.toString(String.valueOf(Math.random()).charAt(5)))) % 3;
    }

    public void check_collision(Barrier b, int id) {
        if ((b.getWay() == hero.getWay()) && ((b.getY() + b.getRadius()) > hero.getY_up()) && (b.getY() - b.getRadius() < hero.getY_down())) {
            collision = new Collision(b.getX(), b.getY(), 1f);
            was_collision = true;

        }
    }

    public void setOnCollisionListener(OnCollisionListener onCollisionListener) {
        this.onCollisionListener = onCollisionListener;
    }

    public boolean Was_collision() {
        return was_collision;
    }

    public void setOFF(boolean OFF) {
        this.OFF = OFF;
    }

    class Collision implements Drawable, Movable {
        private float x, y, r;
        private float v = 20f;

        Collision(float x0, float y0, float r0) {
            x = x0;
            y = y0;
            r = r0;
        }

        @Override
        public void draw(Canvas canvas, Paint paint, float RX, float RY) {
            paint.setColor(Color.RED);
            canvas.drawCircle(x * RX, y * RY, max(r * RX, r * RY), paint);
    }

        @Override
        public void move() {
            r += v;
        }

        public float getR() {
            return r;
        }
    }


}