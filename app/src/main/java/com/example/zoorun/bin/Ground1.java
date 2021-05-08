package com.example.zoorun.bin;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.example.zoorun.interfaces.Drawable;
import com.example.zoorun.interfaces.Movable;

public class Ground1 implements Movable, Drawable {
    private Line1[] lines;
    private int lines_count = 0;

    public Ground1(float LINES_LENGTH, int COUNT_OF_LINES, float DISTANCE, float LEVEL) {
        lines = new Line1[1000];
        float x0 = 500f;
        float y0 = -1500f / 7f;
        float x1 = 500f;
        float y1 = -1500f / 7f;
        int i = 0;
        while (y0 < 1100f){
            lines[i] = new Line1(0, LEVEL, x0, y0);
            lines[i + 1] = new Line1(1, LEVEL, x1, y1);
            lines_count += 2;
            y0 += 50f;
            y1 += 50f;
            x0 = left_EH(y0);
            x1 = right_FL(y1);
            i += 2;

        }


    }


    public void move() {
        for (int i = 0; i < lines_count; i++){
            if (lines[i] != null){
                lines[i].move();
            }
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


        for (int i = 0; i < lines_count; i++){
            lines[i].draw(canvas, paint, RX, RY);
        }
    }


    //functions of lines from draft.jpg

    public float left_EH(float y) {
        return (y - 117000f/49f) / (-255f/49f);
    }

    public float right_FL(float y) {
        return (y + 138000f/49f) / (255f/49f);
    }


}


