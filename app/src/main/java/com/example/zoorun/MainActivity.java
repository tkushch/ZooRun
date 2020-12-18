package com.example.zoorun;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import static android.graphics.Color.rgb;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    FrameLayout frameLayout;
    Button run, pause;
    MyDraw md;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.main);
        md = findViewById(R.id.MyDraw);

        pause = findViewById(R.id.pause);
        pause.setOnClickListener(this);

        frameLayout = findViewById(R.id.frame_Layout);
        frameLayout.setBackgroundColor(Color.WHITE);
        frameLayout.setAlpha(1f);
        pause_screen();
    }

    @Override
    public void onClick(View v) {
        if (v == run){
            frameLayout.removeView(run);
            md.setPause(false);
        }
        if (v == pause){
            md.setPause(true);
            pause_screen();
        }
    }

    public void pause_screen(){
        run = new Button(this);
        frameLayout.addView(run);
        run.setAlpha(0.6f);
        run.setBackgroundColor(rgb(200, 200, 200));
        run.setText("Tap to start");
        run.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        run.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        run.setTextColor(Color.WHITE);
        run.setOnClickListener(this);
    }
}