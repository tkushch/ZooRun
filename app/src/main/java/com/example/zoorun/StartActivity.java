package com.example.zoorun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.SeekBar;


public class StartActivity extends Activity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private Button start_button, info_button, infoback;
    private SeekBar seekBar;
    private int level = 1;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        start_button = findViewById(R.id.start_button);
        start_button.setOnClickListener(this);
        seekBar = findViewById(R.id.seekBar_level);
        seekBar.setOnSeekBarChangeListener(this);
        info_button = findViewById(R.id.info);
        info_button.setOnClickListener(this);
        infoback = findViewById(R.id.backinfo);
        infoback.setOnClickListener(this);
        scrollView = findViewById(R.id.scrollView2);

    }


    @Override
    public void onClick(View v) {
        if (v == start_button) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("level", level);
            startActivity(intent);
        }
        else if (v == info_button){
            scrollView.setAlpha(1);
            infoback.setAlpha(1);
            infoback.setEnabled(true);
            start_button.setEnabled(false);
            start_button.setAlpha(0);
            findViewById(R.id.constraintlayo).setEnabled(false);
            findViewById(R.id.constraintlayo).setAlpha(0);
            findViewById(R.id.textView).setAlpha(0);

        }
        else if (v == infoback){
            scrollView.setAlpha(0);
            infoback.setAlpha(0);
            infoback.setEnabled(false);
            start_button.setEnabled(true);
            start_button.setAlpha(1);
            findViewById(R.id.constraintlayo).setEnabled(true);
            findViewById(R.id.constraintlayo).setAlpha(1);
            findViewById(R.id.textView).setAlpha(1);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        level = progress;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onBackPressed() {
    }
}


