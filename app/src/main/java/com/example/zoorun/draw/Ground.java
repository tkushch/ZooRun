package com.example.zoorun.draw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.example.zoorun.interfaces.Drawable;
import com.example.zoorun.interfaces.Movable;

public class Ground implements Movable, Drawable {
    private float k1 = 4.3f, k2 = 4.3f, k3 = 4.3f;
    private float v;
    private Line[] left_lines;
    private Line[] right_lines;
    private float lines_length;
    private int count_of_lines;
    private float distance;
    private float period;
    private float movement = 0f;

    public Ground(float LINES_LENGTH, int COUNT_OF_LINES, float DISTANCE, float LEVEL) {
        this.lines_length = LINES_LENGTH;
        this.count_of_lines = COUNT_OF_LINES;
        this.distance = DISTANCE;
        this.v = LEVEL;

        left_lines = new Line[count_of_lines];
        right_lines = new Line[count_of_lines];

        this.count_of_lines = 0; //узнаем сколько поместится

        float x_left = 499f; // точка O (abscissa - 1f)
        float x_right = 501f; // точка O (abscissa + 1f)

        for (int i = 0; i < COUNT_OF_LINES; i++) {

            //корректировки из-за перспективы
            float length0 = lines_length * K(x_left);
            float distance0 = distance * K(x_right);
            //
            if (i == 0) {
                if (LEVEL == 0.004f) {
                    period = (length0 + distance0) * k1;
                } else if (LEVEL == 0.0065f) {
                    period = (length0 + distance0) * k2;
                } else if (LEVEL == 0.009f) {
                    period = (length0 + distance0) * k3;
                }

            }

            if (x_left > 800f / 3f) { // abscissa of point "E"
                this.count_of_lines += 1;
                left_lines[i] = new Line(x_left, left_EH(x_left), x_left - length0, left_EH(x_left - length0));
                x_left -= (length0 + distance0);
            }
            if (x_right < 2200f / 3f) { // abscissa of point "F"
                right_lines[i] = new Line(x_right, right_FL(x_right), x_right + length0, right_FL(x_right + length0));
                x_right += (length0 + distance0);
            }
        }
    }

    public void move() {
        float x_left, x_right;
        if (movement > period) {
            movement = 0;
            x_left = 499f;
            x_right = 501f;
        } else {
            x_left = left_lines[0].getX0() - v;
            x_right = right_lines[0].getX0() + v;
            movement += v;
        }

        for (int i = 0; i < count_of_lines; i++) {

            float length0 = lines_length * K(x_left);
            float distance0 = distance * K(x_right);


            left_lines[i].setX0(x_left);
            left_lines[i].setY0(left_EH(x_left));
            left_lines[i].setX1(x_left - length0);
            left_lines[i].setY1(left_EH(x_left - length0));
            x_left -= (length0 + distance0);

            right_lines[i].setX0(x_right);
            right_lines[i].setY0(right_FL(x_right));
            right_lines[i].setX1(x_right + length0);
            right_lines[i].setY1(right_FL(x_right + length0));
            x_right += (length0 + distance0);
        }


    }


    @Override
    public void draw(Canvas canvas, Paint paint, float RX, float RY) {
        paint.setColor(Color.BLACK);
        canvas.drawRect(RX * 0f, RY * 0f, RX * 1000f, RY * 1000f, paint);

        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(10f * RX);
        //AB
        canvas.drawLine(RX * (-3500f / 27f), RY * 1000f, RX * (3500f / 9f), RY * 0f, paint);
        //DC
        canvas.drawLine(RX * (30500f / 27f), RY * 1000f, RX * (5500f / 9f), RY * 0f, paint);

        for (int i = 0; i < count_of_lines; i++) {
            // draw left lines
            float x0 = left_lines[i].getX0();
            float y0 = left_lines[i].getY0();
            float x1 = left_lines[i].getX1();
            float y1 = left_lines[i].getY1();

            canvas.drawLine(RX * x0, RY * y0, RX * x1, RY * y1, paint);
        }
        for (int i = 0; i < count_of_lines; i++) {
            // draw right lines
            float x0 = right_lines[i].getX0();
            float y0 = right_lines[i].getY0();
            float x1 = right_lines[i].getX1();
            float y1 = right_lines[i].getY1();

            canvas.drawLine(RX * x0, RY * y0, RX * x1, RY * y1, paint);
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

    public float getK1() {
        return k1;
    }

    public void setK1(float k1) {
        this.k1 = k1;
    }

    public float getK2() {
        return k2;
    }

    public void setK2(float k2) {
        this.k2 = k2;
    }

    public float getK3() {
        return k3;
    }

    public void setK3(float k3) {
        this.k3 = k3;
    }
}


