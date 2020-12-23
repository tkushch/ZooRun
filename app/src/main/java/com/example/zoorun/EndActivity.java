package com.example.zoorun;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EndActivity extends Activity implements View.OnClickListener {
    Button play;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_activity);
        play = findViewById(R.id.play_again);
        play.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == play){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}