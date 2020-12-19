package com.example.zoorun;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.graphics.Color.rgb;

public class MainActivity extends Activity implements View.OnClickListener {
    private FrameLayout frameLayout;
    private Button run, pause;
    private MyDraw md;
    private boolean swipe = false;
    private float[] swipe_start_coords = {0f, 0f};
    //ВРЕМЕННО
    private TextView tv;
    //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Light_NoTitleBar_Fullscreen); //не помогло
        setContentView(R.layout.main);
        md = findViewById(R.id.MyDraw);

        //ВРЕМЕННО
        tv = findViewById(R.id.textView);
        //
        pause = findViewById(R.id.pause);
        pause.setOnClickListener(this);

        frameLayout = findViewById(R.id.frame_Layout);
        frameLayout.setBackgroundColor(Color.WHITE);
        frameLayout.setAlpha(1f);
        to_pause_screen();
    }

    @Override
    public void onClick(View v) {
        if (v == run) {
            frameLayout.removeView(run);
            md.setPause(false);
            tv.setText("run"); //ВРЕМЕННО
        }
        if (v == pause && !md.isPause()) {
            md.setPause(true);
            to_pause_screen();
            tv.setText("pause"); //ВРЕМЕННО
        }
    }

    public void to_pause_screen() {
        md.setPause(true);
        run = new Button(this);
        frameLayout.addView(run);
        run.setAlpha(0.6f);
        run.setBackgroundColor(rgb(200, 200, 200));
        run.setText(R.string.taptostart);
        run.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        run.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        run.setTextColor(Color.WHITE);
        run.setOnClickListener(this);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x, y;

        // координаты Touch-события
        x = event.getX();
        y = event.getY();

        switch (event.getAction()) {
            // касание началось
            case MotionEvent.ACTION_DOWN://начало касания
                if (y > 500f * md.getRY()) {
                    swipe = true;
                    swipe_start_coords[0] = x;
                    swipe_start_coords[1] = y;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                break;                                              //движение пальца


            case MotionEvent.ACTION_UP:                             //конец касания
                float x0 = swipe_start_coords[0];
                float y0 = swipe_start_coords[1];
                float x1 = event.getX();
                float y1 = event.getY();

                if (x0 == x1) {
                    swipe = false;
                }

                if (swipe) {

                    /*
                    если будут жесты вверх и вниз
                    то в этом и следующем if надо
                    добавить условие abs(y1 - y2) < 40f;
                    */

                    if ((x1 - x0) < -40f * md.getRX()) {
                        md.swipe_left();
                        tv.setText("swipe left");
                        //ВРЕМЕННО
                    }
                    if ((x1 - x0) > 40f * md.getRX()) {
                        md.swipe_right();
                        tv.setText("swipe right");
                        //ВРЕМЕННО
                    }

                }

                swipe = false;
                break;
        }
        return true;
    }

}
