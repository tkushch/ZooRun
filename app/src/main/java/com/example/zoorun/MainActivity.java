package com.example.zoorun;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.zoorun.interfaces.OnCollisionListener;
import com.example.zoorun.interfaces.OnScoreListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import static android.graphics.Color.rgb;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnCollisionListener, OnScoreListener {
    private int level;
    private FrameLayout frameLayout;
    private Button run, pause, back;
    private MyDraw md;
    private boolean swipe = false;
    private final float[] swipe_start_coords = {0f, 0f};
    private TextView et_score, gasoline;
    private MediaPlayer collision_sound, money_sound;
    private float volume;
    private boolean sound, vibration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        setContentView(R.layout.main);
        md = findViewById(R.id.MyDraw);
        pause = findViewById(R.id.pause);
        pause.setOnClickListener(this);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);

        loadSavedPreferences(); //level, sound, vibration
        md.setLEVEL(level);

        frameLayout = findViewById(R.id.frame_Layout);
        frameLayout.setBackgroundColor(Color.WHITE);
        frameLayout.setAlpha(1f);

        to_pause_screen(getString(R.string.taptoplay));
        md.setOnCollisionListener(this);
        md.setOnScoreListener(this);
        md.setMa(this);
        et_score = findViewById(R.id.edit_text_score);
        gasoline = findViewById(R.id.gasoline);

        //Sounds
        volume = (float) (1 - (Math.log(100 - 55) / Math.log(100))); //max volume - curr volume
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
        if (v == back) {
            savescore();
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
        run.setTextColor(Color.BLACK);
        run.setTextSize(getPx(25));
        run.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        run.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        run.setTextColor(Color.WHITE);
        run.setOnClickListener(this);
        back.setEnabled(true);
        back.setAlpha(1f);


    }

    public int getPx(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return ((int) (dp * scale + 0.5f));
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
        savescore();
        if (!md.isPause() && !md.Was_collision()) {
            to_pause_screen(getString(R.string.taptoplay));
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCollision(int score, String param) {
        if (param == "begin") {
            findViewById(R.id.pause).setAlpha(0f);
            findViewById(R.id.pause).setEnabled(false);
            findViewById(R.id.edit_text_score).setAlpha(0f);
            gasoline.setAlpha(0f);
            findViewById(R.id.textView8).setAlpha(0f);

            //sound
            if (sound) {
                collision_sound.start();
            }
            //vibrate
            if (vibration) {
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(VibrationEffect.createOneShot(300L, 10));
            }


        } else if (param == "end") {
            md.setOFF(true);
            Intent intent = new Intent(MainActivity.this, EndActivity.class);
            intent.putExtra("score", score);
            startActivity(intent);
        } else if (param == "money" && sound) {
            if (money_sound.isPlaying()) {
                money_sound.seekTo(0);
            } else {
                money_sound.start();
            }

        }

    }

    public void setGasoline(int g) {
        float gas = g * 0.12f;
        int px1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, gas, getResources().getDisplayMetrics());
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) gasoline.getLayoutParams();
        params.width = px1;
        this.gasoline.setLayoutParams(new ConstraintLayout.LayoutParams(params));
    }

    @Override
    public void OnScore(int score) {
        et_score.setText(String.valueOf(score));
    }

    public void savescore() {
        int score = Integer.parseInt(String.valueOf(et_score.getText()));
        int record = 0;

        //record
        File file = new File(getFilesDir(), "record.txt");

        try {
            Scanner sc = new Scanner(file);
            record = Integer.parseInt(sc.next());

        } catch (FileNotFoundException e) {
            System.err.println("No such file");
        }

        record = Math.max(score, record);

        file.delete();
        file = new File(getFilesDir(), "record.txt");

        try {
            PrintWriter pv = new PrintWriter(new FileWriter(file));
            pv.write(String.valueOf(record));
            pv.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
    }

    protected void loadSavedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);
//        level = 1;
        level = sharedPreferences.getInt("level", 1);
        sound = sharedPreferences.getBoolean("sound", true);
        vibration = sharedPreferences.getBoolean("vibration", true);
    }

}

