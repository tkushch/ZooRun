package com.example.zoorun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndActivity extends Activity implements View.OnClickListener {
    private Button play;
    private TextView tv_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_activity);
        play = findViewById(R.id.play_again);
        play.setOnClickListener(this);
        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        tv_score = findViewById(R.id.text_view_results);
        tv_score.setText(tv_score.getText() + " " + String.valueOf(score));


    }

    @Override
    public void onClick(View v) {
        if (v == play) {
            Intent intent = new Intent(this, StartActivity.class);
            startActivity(intent);
        }
    }
}