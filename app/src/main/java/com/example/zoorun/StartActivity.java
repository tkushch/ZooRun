package com.example.zoorun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;


public class StartActivity extends Activity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private Button start_button;
    private Button extrab;
    private SeekBar seekBar;
    private int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        start_button = findViewById(R.id.start_button);
        start_button.setOnClickListener(this);
        extrab = new Button(this);
        seekBar = findViewById(R.id.seekBar_level);
        seekBar.setOnSeekBarChangeListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == start_button) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("level", level);
            startActivity(intent);
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
}


