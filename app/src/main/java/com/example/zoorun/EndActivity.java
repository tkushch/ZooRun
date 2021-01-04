package com.example.zoorun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.*;
import java.util.Scanner;

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
        int record = 0;

        //record
        File file = new File(getDataDir(), "record.txt");

        try {
            Scanner sc = new Scanner(file);
            record = Integer.parseInt(sc.next());

        } catch (FileNotFoundException e) {
            System.err.println("No such file");
        }

        record = Math.max(score, record);

        file.delete();
        file = new File(getDataDir(), "record.txt");

        try {
            PrintWriter pv = new PrintWriter(new FileWriter(file));
            pv.write(String.valueOf(record));
            pv.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextView record_tv = findViewById(R.id.record);
        record_tv.setText(getString(R.string.record_string) + record);


    }

    @Override
    public void onClick(View v) {
        if (v == play) {
            Intent intent = new Intent(this, StartActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}