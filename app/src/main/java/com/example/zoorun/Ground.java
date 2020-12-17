package com.example.zoorun;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import static android.graphics.Color.rgb;
import static android.graphics.Color.valueOf;

public class Ground implements Movable, Drawable {
    private float v;
    private Line[] lines;
    private float lines_length;
    private int count_of_lines;
    private float distance;

    public Ground(float RX, float RY, float LINES_LENGTH, int COUNT_OF_LINES, float DISTANCE, float v) {
        lines = new Line[COUNT_OF_LINES * 2];
        float line_y = 0f;
        for (int i = 0; i < COUNT_OF_LINES * 2; i += 2) {
            lines[i] = new Line(RX * 1000f / 3f, line_y);
            lines[i + 1] = new Line(RX * 1000f / 3f * 2f, line_y);
            line_y += LINES_LENGTH + DISTANCE;
        }
        this.lines_length = LINES_LENGTH;
        this.count_of_lines = COUNT_OF_LINES;
        this.distance = DISTANCE;
        this.v = v;

    }

    public void move() {
    }


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
}
