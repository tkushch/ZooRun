package com.example.zoorun;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import static android.graphics.Color.rgb;

public class Hero implements Drawable {
    private float radius_x = 90f, radius_y = 52f;
    private float x = 500f, y = 835f; // центр
    private float y_up = y - 10f, y_down = y + 10f;
    private float left_way_x = KN_line(y), center_way_x = x, right_way_x = PM_line(y);
    private int health = 3;

    private boolean move_side_flag = false;
    private int delay = 10;
    private int step_delay = 0;
    private boolean inSwipe = false;
    private boolean start_of_swipe;
    private float swipe_v; // swipe velocity (by level)
    private float swipe_target = center_way_x;
    private int way = 1; // 0, 1 or 2;
    //private Bitmap image;

    public Hero(float level) {
        swipe_v = level * 5 * 1000f;
    }


    public void jump() {
        if (step_delay < delay) {
            step_delay++;
        } else {
            if (move_side_flag) {
                y = y_up;
            } else {
                y = y_down;
            }
            step_delay = 0;
            move_side_flag = !move_side_flag;
        }
    }


    public void swipe_move() {
        if (start_of_swipe) {
            start_of_swipe = false;
            step_delay = 0;
            if (y != y_up) {
                move_side_flag = !move_side_flag;
                y = y_up;
            }

        }
        x += swipe_v;
        if ((swipe_v < 0f && x < swipe_target) || (swipe_v > 0f && x > swipe_target)) {
            x = swipe_target;
            way = way_by_target(swipe_target);
            inSwipe = false;
        }
    }


    @Override
    public void draw(Canvas canvas, Paint paint, float RX, float RY) {
        paint.setColor(Color.YELLOW);
        canvas.drawRect(RX * (x - radius_x), RY * (y - radius_y), RX * (x + radius_x), RY * (y + radius_y), paint);
    }

    public void swipe_left() {
        if (inSwipe) {
        } else {
            inSwipe = true;
            if (x == left_way_x) {
                inSwipe = false;
            } else if (x == center_way_x) {
                swipe_target = left_way_x;
                swipe_v = -(Math.abs(swipe_v));
                start_of_swipe = true;
            } else if (x == right_way_x) {
                swipe_target = center_way_x;
                swipe_v = -(Math.abs(swipe_v));
                start_of_swipe = true;
            }
        }
    }

    public void swipe_right() {
        if (inSwipe) {
        } else {
            inSwipe = true;
            if (x == right_way_x) {
                inSwipe = false;
            } else if (x == center_way_x) {
                swipe_target = right_way_x;
                swipe_v = (Math.abs(swipe_v));
                start_of_swipe = true;
            } else if (x == left_way_x) {
                swipe_target = center_way_x;
                swipe_v = (Math.abs(swipe_v));
                start_of_swipe = true;
            }
        }
    }

    public float KN_line(float y) {
        return ((y - (1945500f / 1631f)) / (-4590f / 1631f));
    }

    public float PM_line(float y) {
        return ((y + (2644500f / 1631f)) / (4590f / 1631f));
    }

    public boolean isInSwipe() {
        return inSwipe;
    }

    public int way_by_target(float swipe_target) {
        int way = 1;
        if (swipe_target == left_way_x) {
            way = 0;
        } else if (swipe_target == center_way_x) {
            way = 1;
        } else if (swipe_target == right_way_x) {
            way = 2;
        }
        return way;
    }

    public float getRadius_y() {
        return radius_y;
    }

    public void setRadius_y(float radius_y) {
        this.radius_y = radius_y;
    }

    public float getY_up() {
        return y_up;
    }

    public void setY_up(float y_up) {
        this.y_up = y_up;
    }

    public float getY_down() {
        return y_down;
    }

    public int getWay() {
        return way;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
