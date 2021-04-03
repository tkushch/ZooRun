package com.example.zoorun;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {
    private boolean sound, vibration;
    private ToggleButton toggleButtonSound, toggleButtonVibration;

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
        View view = inflater.inflate(R.layout.settings, container, false);
        toggleButtonSound = view.findViewById(R.id.toggleButtonSound);
        toggleButtonVibration = view.findViewById(R.id.toggleButtonVibration);
        toggleButtonSound.setChecked(sound);
        toggleButtonVibration.setChecked(vibration);
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

    public void setSound(boolean sound) {
        this.sound = sound;
        toggleButtonSound.setChecked(sound);
    }

    public boolean isVibration() {
        return vibration;
    }

    public void setVibration(boolean vibration) {
        this.vibration = vibration;
        toggleButtonVibration.setChecked(vibration);
    }
}
