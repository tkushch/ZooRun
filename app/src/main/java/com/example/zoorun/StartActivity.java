package com.example.zoorun;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.zoorun.fragments.InfoFragment;
import com.example.zoorun.fragments.SettingsFragment;
import com.example.zoorun.interfaces.HidePressedListener;


public class StartActivity extends AppCompatActivity implements View.OnClickListener, HidePressedListener, RadioGroup.OnCheckedChangeListener {
    private Button start_button, info_button, settings_button;
    private RadioGroup radioGroup;
    private boolean isFragmentOnScreen;
    private int level;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private SettingsFragment fragmentSettings;
    private InfoFragment fragmentInfo;
    private boolean sound = true;
    private boolean vibration = true;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.start);
        start_button = findViewById(R.id.start_button);
        start_button.setOnClickListener(this);
        info_button = findViewById(R.id.info);
        info_button.setOnClickListener(this);
        radioGroup = findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(this);
        settings_button = findViewById(R.id.settings_button);
        settings_button.setOnClickListener(this);
        fragmentManager = getSupportFragmentManager();
        isFragmentOnScreen = false;
        imageView = findViewById(R.id.imageView2);

        loadSavedPreferences(); //level, sound, vibration


    }


    @Override
    public void onClick(View v) {
        if (v == start_button) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        } else if (v == info_button) {
            fragmentTransaction = fragmentManager.beginTransaction();
            if (!isFragmentOnScreen) {
                settings_button.setAlpha(0);
                settings_button.setEnabled(false);
                addFragment("info");
            } else {
                settings_button.setAlpha(1);
                settings_button.setEnabled(true);
                removeFragment("info");
            }

        } else if (v == settings_button) {
            fragmentTransaction = fragmentManager.beginTransaction();
            if (!isFragmentOnScreen) {
                addFragment("settings");
                info_button.setAlpha(0);
                info_button.setEnabled(false);
            } else {
                removeFragment("settings");
                info_button.setAlpha(1);
                info_button.setEnabled(true);
            }
        }
    }


    @Override
    public void hide(String which) {
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (which) {
            case "info":
                settings_button.setAlpha(1);
                settings_button.setEnabled(true);
                break;
            case "settings":
                info_button.setAlpha(1);
                info_button.setEnabled(true);
                break;
        }
        removeFragment(which);


    }


    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("level", level);
        outState.putBoolean("sound", sound);
        outState.putBoolean("vibration", vibration);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        level = savedInstanceState.getInt("level", 1);
        sound = savedInstanceState.getBoolean("sound", true);
        vibration = savedInstanceState.getBoolean("vibration", true);
    }

    protected void addFragment(String which) {
        if (which.equals("info")) {
            fragmentInfo = new InfoFragment();
            fragmentInfo.setHidePressedListener(this);
            fragmentTransaction.add(R.id.myfragmentcontainer, fragmentInfo);
        } else if (which.equals("settings")) {
            fragmentSettings = new SettingsFragment(sound, vibration);
            fragmentSettings.setHidePressedListener(this);
            fragmentTransaction.add(R.id.myfragmentcontainer, fragmentSettings);
        }
        isFragmentOnScreen = !isFragmentOnScreen;
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
        hideElements();


    }

    protected void removeFragment(String which) {

        if (which.equals("info")) {

            fragmentTransaction.remove(fragmentInfo);
        } else if (which.equals("settings")) {

            sound = fragmentSettings.isSound();
            vibration = fragmentSettings.isVibration();
            fragmentTransaction.remove(fragmentSettings);
            savePreferences("sound", sound);
            savePreferences("vibration", vibration);
        }
        isFragmentOnScreen = !isFragmentOnScreen;
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
        restoreElements();
    }

    protected void savePreferences(String key, boolean value) {
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    protected void savePreferences(String key, Integer value) {
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    protected void loadSavedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);
        level = sharedPreferences.getInt("level", 1);
        sound = sharedPreferences.getBoolean("sound", true);
        vibration = sharedPreferences.getBoolean("vibration", true);
        setRadioGroupCheck();
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb1:
                level = 1;
                break;
            case R.id.rb2:
                level = 2;
                break;
            case R.id.rb3:
                level = 3;
                break;
        }
        savePreferences("level", level);

    }

    public void setRadioGroupCheck() {
        switch (level) {
            case 1:
                ((RadioButton) findViewById(R.id.rb1)).setChecked(true);
                break;
            case 2:
                ((RadioButton) findViewById(R.id.rb2)).setChecked(true);
                break;
            case 3:
                ((RadioButton) findViewById(R.id.rb3)).setChecked(true);
        }
    }

    public void hideElements() {
        start_button.setEnabled(false);
        radioGroup.setEnabled(false);
        radioGroup.setAlpha(0f);
        imageView.setAlpha(0f);
    }

    public void restoreElements() {
        start_button.setEnabled(true);
        radioGroup.setEnabled(true);
        radioGroup.setAlpha(1f);
        imageView.setAlpha(1f);
    }
}


