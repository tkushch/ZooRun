package com.example.zoorun.bin;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.zoorun.interfaces.Drawable;
import com.example.zoorun.interfaces.Movable;

public class Line_ implements Drawable, Movable {
    private float x0, y0, x1, y1, dx, dy;
    private int way; // 0 or 1;  (left and right )
    private float LEFT_WAY_TANGENS = -49f/255f;
    private float RIGHT_WAY_TANGENS = -LEFT_WAY_TANGENS;

    public Line_(float x0, float y0, float x1, float y1, int way, float level) {
        this.way = way;
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
        dy = level * 1000f;
        switch (way) {
            case 0:
                dx = LEFT_WAY_TANGENS * dy;
                break;
            case 1:
                dx = RIGHT_WAY_TANGENS * dy;
                break;
        }
    }


    @Override
    public void draw(Canvas canvas, Paint paint, float RX, float RY) {
        canvas.drawLine(RX * x0, RY * y0, RX * x1, RY * y1, paint);


    }

    @Override
    public void move() {
        x0 += dx;
        y0 += dy;
        x1 += dx;
        y1 += dy;
    }

    public float getX0() {
        return x0;
    }

    public float getDx() {
        return dx;
    }
}

/*
package com.example.zoorun;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ground implements Movable, Drawable {
    private Line[][] lines;
    private Line[][] lines_memory;
    private int count_of_lines;
    private float period;
    private float movement = 0f;

    private float LINES_LENGTH, COUNT_OF_LINES, DISTANCE, LEVEL;

    public Ground(float LINES_LENGTH, int COUNT_OF_LINES, float DISTANCE, float LEVEL) {
        fill_lines(LINES_LENGTH, COUNT_OF_LINES, DISTANCE, LEVEL);
        this.LINES_LENGTH = LINES_LENGTH;
        this.COUNT_OF_LINES = COUNT_OF_LINES;
        this.DISTANCE = DISTANCE;
        this.LEVEL = LEVEL;
    }


    public void move() {
        if (movement > period) {
            movement = 0f;
            fill_lines(LINES_LENGTH, COUNT_OF_LINES, DISTANCE, LEVEL);
        }
        else{
            movement += LEVEL;
        }

        for (int i = 0; i < count_of_lines; i++) {
            lines[0][i].move();
            lines[1][i].move();
        }
    }


    @Override
    public void draw(Canvas canvas, Paint paint, float RX, float RY) {
        //background
        paint.setColor(Color.BLACK);
        canvas.drawRect(RX * 0f, RY * 0f, RX * 1000f, RY * 1000f, paint);

        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(10f * RX);
        //AB
        canvas.drawLine(RX * (-3500f / 27f), RY * 1000f, RX * (3500f / 9f), RY * 0f, paint);
        //DC
        canvas.drawLine(RX * (30500f / 27f), RY * 1000f, RX * (5500f / 9f), RY * 0f, paint);


        //черточки (lines)
        for (int i = 0; i < count_of_lines; i++) {
            lines[0][i].draw(canvas, paint, RX, RY);
            lines[1][i].draw(canvas, paint, RX, RY);
        }

    }

    //functions of lines from draft.jpg
    public float left_EH(float x) {
        return (-255f / 49f * x + 117000f / 49f);
    }

    public float right_FL(float x) {
        return (255f / 49f * x - 138000f / 49f);
    }


    public float K(float x0) {
        //корректировки из-за перспективы
        float k = 0.005f;
        return (k * (Math.abs(x0 - 500f)));
        //
    }


    public void fill_lines(float LINES_LENGTH, float COUNT_OF_LINES, float DISTANCE, float LEVEL) {
        Line[] left_lines = new Line[(int) COUNT_OF_LINES];
        Line[] right_lines = new Line[(int) COUNT_OF_LINES];

        float x_left = 499f; //  (abscissa point "O" - 1f) начальная точка для левых
        float x_right = 501f; // (abscissa point "O" + 1f) начальная точка для правых

        this.count_of_lines = 0; //узнаем сколько поместится
        for (int i = 0; i < COUNT_OF_LINES; i++) {

            //корректировки размеров из-за перспективы
            float length0 = LINES_LENGTH * K(x_left);
            float distance0 = DISTANCE * K(x_right);

            //сохраняем период о иксу
            if (i == 0) {
                period = length0 + distance0;
            }

            if (x_left > 800f / 3f) { // abscissa of point "E"
                this.count_of_lines += 1;
                left_lines[i] = new Line(x_left, left_EH(x_left), x_left - length0, left_EH(x_left - length0), 0, LEVEL);
                x_left -= (length0 + distance0);
            }
            if (x_right < 2200f / 3f) { // abscissa of point "F"
                right_lines[i] = new Line(x_right, right_FL(x_right), x_right + length0, right_FL(x_right + length0), 1, LEVEL);
                x_right += (length0 + distance0);
            }
        }
        lines = new Line[][]{left_lines, right_lines};
        lines_memory = lines.clone();

    }
}
*/

