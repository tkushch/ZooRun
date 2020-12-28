package com.example.zoorun;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

import static android.graphics.Color.rgb;

public class MainActivity extends Activity implements View.OnClickListener, OnCollisionListener, OnScoreListener {
    private SeekBar seekBar_level;
    private FrameLayout frameLayout;
    private Button run, pause, back;
    private MyDraw md;
    private boolean swipe = false;
    private final float[] swipe_start_coords = {0f, 0f};
    private EditText et_score;
    private MediaPlayer collision_sound, money_sound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Light_NoTitleBar_Fullscreen); //не помогло
        setContentView(R.layout.main);
        md = findViewById(R.id.MyDraw);
        pause = findViewById(R.id.pause);
        pause.setOnClickListener(this);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);

        seekBar_level = findViewById(R.id.seekBar_level);
        md.setLEVEL(getIntent().getIntExtra("level", 2));


        frameLayout = findViewById(R.id.frame_Layout);
        frameLayout.setBackgroundColor(Color.WHITE);
        frameLayout.setAlpha(1f);

        to_pause_screen(getString(R.string.taptoplay));
        md.setOnCollisionListener(this);
        md.setOnScoreListener(this);
        et_score = findViewById(R.id.edit_text_score);

        //Sounds
        float volume = (float) (1 - (Math.log(100 - 40) / Math.log(100))); //max volume - curr volume
        collision_sound = MediaPlayer.create(this, R.raw.explosion);
        collision_sound.setVolume(volume, volume);
        money_sound = MediaPlayer.create(this, R.raw.eat);
        money_sound.setVolume(volume, volume);


    }

    @Override
    public void onClick(View v) {
        if (v == run) {
            frameLayout.removeView(run);
            md.setPause(false);
            back.setAlpha(0f);
            back.setEnabled(false);
        }
        if (v == pause && !md.isPause()) {
            md.setPause(true);
            to_pause_screen(getString(R.string.taptoplay));
        }
        if (v == back){
            Intent intent = new Intent(this, StartActivity.class);
            startActivity(intent);
        }

    }

    public void to_pause_screen(String message) {
        md.setPause(true);
        run = new Button(this);
        frameLayout.addView(run);
        run.setAlpha(0.6f);
        run.setBackgroundColor(rgb(200, 200, 200));
        run.setText(message);
        run.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        run.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        run.setTextColor(Color.WHITE);
        run.setOnClickListener(this);
        back.setEnabled(true);
        back.setAlpha(1f);


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
                if (y > 100f * md.getRY()) {
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
                    }
                    if ((x1 - x0) > 40f * md.getRX()) {
                        md.swipe_right();
                    }

                }

                swipe = false;
                break;
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!md.isPause() && !md.Was_collision()) {
            to_pause_screen(getString(R.string.taptoplay));
        }
    }


    @Override
    public void onCollision(int score, String param) {
        if (param == "begin") {
            findViewById(R.id.pause).setAlpha(0f);
            findViewById(R.id.pause).setEnabled(false);
            findViewById(R.id.edit_text_score).setAlpha(0f);

            //sound
            collision_sound.start();

            //vibrate
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(VibrationEffect.createOneShot(300L, 10));



        } else if (param == "end") {
            md.setOFF(true);
            Intent intent = new Intent(MainActivity.this, EndActivity.class);
            intent.putExtra("score", score);
            startActivity(intent);
        } else if (param == "money") {
            money_sound.start();

        }

    }

    @Override
    public void OnScore(int score) {
        et_score.setText(String.valueOf(score));
    }

    @Override
    public void onBackPressed() {
    }


}

