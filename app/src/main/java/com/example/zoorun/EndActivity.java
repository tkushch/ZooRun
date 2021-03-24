package com.example.zoorun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.*;
import java.util.Scanner;

public class EndActivity extends AppCompatActivity implements View.OnClickListener {
    private Button play, save;
    private TextView tv_score;
    private int record, score, level;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_activity);
        play = findViewById(R.id.play_again);
        play.setOnClickListener(this);
        save = findViewById(R.id.savetobd);
        save.setOnClickListener(this);
        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);
        level = intent.getIntExtra("level", 1);
        tv_score = findViewById(R.id.text_view_results);
        tv_score.setText(tv_score.getText() + " " + String.valueOf(score));
        record = 0;

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
            intent.putExtra("level", level);
            startActivity(intent);
        }
        else if (v == save){
            Intent intent = new Intent(this, SaveRecordActivity.class);
            intent.putExtra("record", score);
            intent.putExtra("level", level);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, StartActivity.class);
        intent.putExtra("level", level);
        startActivity(intent);
    }


}