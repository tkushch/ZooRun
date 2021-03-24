package com.example.zoorun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class StartActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private Button start_button, info_button;
    private boolean isFragmentOnScreen;
    private SeekBar seekBar;
    private int level;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment fragment;

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
        fragmentManager = getSupportFragmentManager();
        isFragmentOnScreen = false;


        level = getIntent().getIntExtra("level", 1);
        seekBar.setProgress(level);

    }


    @Override
    public void onClick(View v) {
        if (v == start_button) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("level", level);
            startActivity(intent);

        } else if (v == info_button) {
            fragmentTransaction = fragmentManager.beginTransaction();
            if (!isFragmentOnScreen) {
                fragment = new StartPageFragment();
                fragmentTransaction.add(R.id.myfragmentcontainer, fragment);
                info_button.setText("СПРЯТАТЬ");
            } else {
                fragmentTransaction.remove(fragment);
                info_button.setText("ИНФОРМАЦИЯ");
            }
            isFragmentOnScreen = !isFragmentOnScreen;
            fragmentTransaction.commit();
            fragmentTransaction.addToBackStack(null);

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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("level", level);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        level = savedInstanceState.getInt("level", 1);
    }


}


