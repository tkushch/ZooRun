package com.example.zoorun;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import static android.graphics.Color.rgb;
import static android.graphics.Color.valueOf;

public class Ground implements Movable, Drawable {
    private float v;
    private Line[] left_lines;
    private Line[] right_lines;
    private float lines_length;
    private int count_of_left_lines;
    private int count_of_right_lines;
    private float distance;

    public Ground(float RX, float RY, float LINES_LENGTH, int COUNT_OF_LINES, float DISTANCE, float v) {
        this.lines_length = LINES_LENGTH;
        this.count_of_left_lines = COUNT_OF_LINES;
        this.count_of_right_lines = COUNT_OF_LINES;
        this.distance = DISTANCE;
        this.v = v;

        left_lines = new Line[COUNT_OF_LINES];
        right_lines = new Line[COUNT_OF_LINES];

        this.count_of_left_lines = 0; //узнаем сколько поместится

        float x = 7800f / 17f;
        for (int i = 0; i < COUNT_OF_LINES; i++) {
            if (x > 800f / 3f) { // abscissa of point "E"
                this.count_of_left_lines += 1;
                left_lines[i] = new Line(x, left_EH(x), x - LINES_LENGTH, left_EH(x - LINES_LENGTH));
                x -= (LINES_LENGTH + DISTANCE);
            }
        }
        this.count_of_right_lines = 0;
        x = 9200f / 17f;
        for (int i = 0; i < COUNT_OF_LINES; i++) {
            if (x < 2200f / 3f) { // abscissa of point "F"
                this.count_of_right_lines += 1;
                right_lines[i] = new Line(x, right_FL(x), x + LINES_LENGTH, right_FL(x + LINES_LENGTH));
                x += (LINES_LENGTH + DISTANCE);
            }
        }


    }

    public void move() {
    }


    @Override
    public void draw(Canvas canvas, Paint paint, float RX, float RY) {
        paint.setColor(Color.BLACK);
        //AB
        canvas.drawLine(RX * 0f, RY * 750f, RX * (3500f / 9f), RY * 0f, paint);
        //DC
        canvas.drawLine(RX * 1000f, RY * 750f, RX * (5500f / 9f), RY * 0f, paint);

        for (int i = 0; i < count_of_left_lines; i++) {
            // draw left lines
            float x0 = left_lines[i].getX0();
            float y0 = left_lines[i].getY0();
            float x1 = left_lines[i].getX1();
            float y1 = left_lines[i].getY1();

            canvas.drawLine(RX * x0, RY * y0, RX * x1, RY * y1, paint);
        }
        for (int i = 0; i < count_of_right_lines; i++) {
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
}


//old

        /*for (int i = 0; i < COUNT_OF_LINES * 2; i += 2) {
            lines[i] = new Line(RX * 1000f / 3f, line_y);
            lines[i + 1] = new Line(RX * 1000f / 3f * 2f, line_y);
            line_y += LINES_LENGTH + DISTANCE;
        }
        this.lines_length = LINES_LENGTH;
        this.count_of_lines = COUNT_OF_LINES;
        this.distance = DISTANCE;*/



    /*
    @Override
    public void draw(Canvas canvas, Paint paint, float RX, float RY) {
        paint.setColor(rgb(255, 222, 173));
        //canvas.drawRect(0, 0, RX * 1000, RY * 1000, paint);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
        paint.setColor(Color.BLACK);
        for (int i = 0; i < count_of_lines * 2; i++) {
            canvas.drawRect(lines[i].getX(), lines[i].getY(), lines[i].getX() + RX * 3f, lines[i].getY() + lines_length, paint);
        }

    }
    */