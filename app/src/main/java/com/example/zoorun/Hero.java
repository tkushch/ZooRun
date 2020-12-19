package com.example.zoorun;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import static android.graphics.Color.rgb;

public class Hero implements Drawable {
    private float radius_x = 90f, radius_y = 52.5f;
    private float x = 500f, y = 835f; // центр
    private float left_way_x = KN_line(y), center_way_x = x, right_way_x = PM_line(y);

    /*private boolean move_side_flag = false;
    private int step_in_moving = 0;

     */
    private boolean inSwipe = false;
    private boolean start_of_swipe;
    private float swipe_v; // swipe velocity (by level)
    private float swipe_target = center_way_x;
    private int way = 1; // 0, 1 or 2;
    //private Bitmap image;

    public Hero(float level) {
        swipe_v = level * 5 * 1000f;
    }

    /*
    public void jump() {
        if (step_in_moving < 8) {
            step_in_moving++;
        } else {
            if (move_side_flag) {
                y += 20f;
            } else {
                y -= 20f;
            }
            step_in_moving = 0;
            move_side_flag = !move_side_flag;
        }
    }

     */

    public void swipe_move() {
        if (start_of_swipe) {
            start_of_swipe = false;
        }
        x += swipe_v;
        if (swipe_v < 0f && x < swipe_target) {
            x = swipe_target;
            inSwipe = false;
        }
        else if (swipe_v > 0f && x > swipe_target) {
            x = swipe_target;
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
            }
            else if (x == center_way_x) {
                swipe_target = left_way_x;
                swipe_v = -(Math.abs(swipe_v));
                start_of_swipe = true;
            }
            else if (x == right_way_x) {
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
            }
            else if (x == center_way_x) {
                swipe_target = right_way_x;
                swipe_v = (Math.abs(swipe_v));
                start_of_swipe = true;
            }
            else if (x == left_way_x) {
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
}
