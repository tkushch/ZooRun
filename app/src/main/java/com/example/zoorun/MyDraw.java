package com.example.zoorun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


public class MyDraw extends View {
    private MainActivity ma;
    private int score = 0, gasoline = 1000;
    private OnScoreListener onScoreListener;
    private boolean OFF;
    private OnCollisionListener onCollisionListener;
    private Collision collision;
    private boolean was_collision = false;
    private Hero hero;
    private Ground ground;
    private Barrier[] barriers;
    private int barrier_delay = 0;
    private Coin[] coins;
    private int coin_delay = 0;
    private Bitmap coin_image = BitmapFactory.decodeResource(getResources(), R.drawable.coin1);
    private Bitmap hero_image = BitmapFactory.decodeResource(getResources(), R.drawable.car2);
    private boolean need_coin = true;
    private Paint paint = new Paint();
    private boolean isFirst;
    private boolean pause = true;
    private float RX;// условные единицы экрана 1000*1000
    private float RY;// условные единицы экрана 1000*1000
    private int COUNT_OF_LINES = 1000; // максимальное количество линий в ряду
    private float DISTANCE = 3f; // расстояние между абсциссами линий
    private float LEVEL = 0.0065f; // скорость    (от 0.003 до 0.007)
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

        for (int i = 0; i < coins.length; i++) {
            Coin c = coins[i];
            if (c != null) {
                if (c.isRelevance()) {
                    c.draw(canvas, paint, RX, RY);
                }
            }
        }

        //move
        if (!pause && !was_collision) {
//            ground.move();
            if (LEVEL == 0.004f) {
                score++;
                gasoline -= 2;

            }
            else if (LEVEL == 0.0065f){
                score += 2;
                gasoline -= 2;
            }
            else if (LEVEL == 0.009f){
                score += 3;
                gasoline -= 2;
            }

            if (hero.isInSwipe()) {
                hero.swipe_move();
            } else {
                //hero.jump();
            }

            for (int i = 0; i < barriers.length; i++) {
                Barrier b = barriers[i];
                if (b != null) {
                    if (b.isRelevance()) {
                        b.move();
                        check_collision(b);
                    }
                }
            }

            for (int i = 0; i < coins.length; i++) {
                Coin c = coins[i];
                if (c != null) {
                    if (c.isRelevance()) {
                        c.move();
                        check_collision(c, i);
                    }
                }
            }
            if (gasoline > 0){
                ma.setGasoline(gasoline);
            }
            else{
                collision = new Collision(hero.getX(),hero.getY(), 1f);
                was_collision = true;
                onCollisionListener.onCollision(score, "begin");
            }
            generate_barriers();
            if (need_coin) {
                generate_coins();
            }
        } else if (was_collision) {
            collision.draw(canvas, paint, RX, RY);
            collision.move();
            if (collision.getR() > 1000f) {
                onCollisionListener.onCollision(score, "end");
            }
        }
        if (!OFF) {
            onScoreListener.OnScore(score);
            invalidate();
        }
    }

    public void fill() {
        ground = new Ground(LINES_LENGTH, COUNT_OF_LINES, DISTANCE, LEVEL);
        hero = new Hero(LEVEL, hero_image);
        barriers = new Barrier[100];
        coins = new Coin[500];

    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public void setLEVEL(int level) {
        switch (level) {
            case 1:
                LEVEL = 0.004f;
                break;
            case 2:
                LEVEL = 0.0065f;
                break;
            case 3:
                LEVEL = 0.009f;
                break;

        }
    }

    public void swipe_left() {
        hero.swipe_left();
    }

    public void swipe_right() {
        hero.swipe_right();
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
        if (barrier_delay < 70 * (1f / LEVEL) / 200) {
            barrier_delay++;
            //избегаем наложения монет на препятствия
            if (barrier_delay > 10 * (1f / LEVEL / 200) && barrier_delay < 60 * (1f / LEVEL / 200)) {
                need_coin = true;
            } else {
                need_coin = false;
            }
        } else {
            need_coin = false;
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

    public void generate_coins() {
        if (coin_delay < 35) {
            coin_delay++;
        } else {
            coin_delay = 0;
            int w1 = rand_0_3();
            //int w2 = rand_0_3();
            //while (w2 == w1) {
            //    w2 = rand_0_3();
            //}
            for (int i = 0; i < coins.length; i++) {
                boolean flag = false;
                if (coins[i] == null) {
                    flag = true;
                } else if (!coins[i].isRelevance()) {
                    flag = true;
                }
                if (flag) {
                    coins[i] = new Coin(coin_image, w1, LEVEL);
                    break;
                }
            }
            /*for (int i = 0; i < coins.length; i++) {
                boolean flag = false;
                if (coins[i] == null) {
                    flag = true;
                } else if (!coins[i].isRelevance()) {
                    flag = true;
                }
                if (flag) {
                    coins[i] = new Coin(coin_image, w2, LEVEL);
                    break;
                }
            }*/

        }
    }

    public int rand_0_3() {
        return (Integer.parseInt(Character.toString(String.valueOf(Math.random()).charAt(5)))) % 3;
    }

    public void check_collision(Barrier b) {

        int checker = 0;
        if (b.getX() + b.getRadius() >= hero.getX() - hero.getRadius_x()) {
            checker++;
        }
        if (b.getX() - b.getRadius() <= hero.getX() + hero.getRadius_x()) {
            checker++;
        }
        if (b.getY() + b.getRadius() >= hero.getY() - hero.getRadius_y()) {
            checker++;
        }
        if (b.getY() - b.getRadius() <= hero.getY() + hero.getRadius_y()) {
            checker++;
        }
        if (checker == 4) {
            collision = new Collision(b.getX(), b.getY(), 1f);
            was_collision = true;
            onCollisionListener.onCollision(score, "begin");
        }
    }

    public void check_collision(Coin c, int id) {
        int checker = 0;
        if (c.getX() + c.getRadius() >= hero.getX() - hero.getRadius_x()) {
            checker++;
        }
        if (c.getX() - c.getRadius() <= hero.getX() + hero.getRadius_x()) {
            checker++;
        }
        if (c.getY() + c.getRadius() >= hero.getY() - hero.getRadius_y()) {
            checker++;
        }
        if (c.getY() - c.getRadius() <= hero.getY() + hero.getRadius_y()) {
            checker++;
        }
        if (checker == 4) {
            coins[id].setRelevance(false);
            score += 100;
            if (gasoline < 1000){
                gasoline += 400;
                if (gasoline > 1000){
                    gasoline = 1000;
                }
            }
            ma.setGasoline(gasoline);
            onCollisionListener.onCollision(score, "money");
        }
    }

    public void setOnCollisionListener(OnCollisionListener onCollisionListener) {
        this.onCollisionListener = onCollisionListener;
    }


    public void setOnScoreListener(OnScoreListener onScoreListener) {
        this.onScoreListener = onScoreListener;
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
            canvas.drawCircle(x * RX, y * RY, Math.max(r * RX, r * RY), paint);
        }

        @Override
        public void move() {
            r += v;
        }

        public float getR() {
            return r;
        }
    }


    public void setMa(MainActivity ma) {
        this.ma = ma;
    }


/*
    public void selevplus(){
        LEVEL += 0.0001f;
        System.out.println(LEVEL);
    }
    public void selevmin(){
        LEVEL -= 0.0001f;
        System.out.println(LEVEL);
    }

 */


}