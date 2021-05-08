package com.example.zoorun.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.zoorun.R;
import com.example.zoorun.StartActivity;
import com.example.zoorun.interfaces.HidePressedListener;

public class SettingsFragment extends Fragment implements ToggleButton.OnCheckedChangeListener {
    private boolean sound, vibration;
    private ToggleButton toggleButtonSound, toggleButtonVibration;
    private View view;
    private HidePressedListener hidePressedListener;
    private Button buttonHide;

    public SettingsFragment(boolean sound, boolean vibration) {
        this.sound = sound;
        this.vibration = vibration;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // менеджер компоновки, который позволяет получать доступ к layout с наших ресурсов
        view = inflater.inflate(R.layout.settings, container, false);
        toggleButtonSound = view.findViewById(R.id.toggleButtonSound);
        toggleButtonVibration = view.findViewById(R.id.toggleButtonVibration);
        toggleButtonSound.setOnCheckedChangeListener(this);
        toggleButtonVibration.setOnCheckedChangeListener(this);
        toggleButtonSound.setChecked(sound);
        toggleButtonVibration.setChecked(vibration);
        buttonHide = view.findViewById(R.id.buttonhide2);
        buttonHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePressedListener.hide("settings");
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public boolean isSound() {
        return sound;
    }

    public boolean isVibration() {
        return vibration;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == toggleButtonSound) {
            sound = isChecked;
        } else if (buttonView == toggleButtonVibration) {
            vibration = isChecked;
        }
    }

    public void setHidePressedListener(StartActivity startActivity){
        hidePressedListener = startActivity;
    }
}
