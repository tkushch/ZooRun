package com.example.zoorun.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.zoorun.R;
import com.example.zoorun.StartActivity;
import com.example.zoorun.interfaces.HidePressedListener;

public class InfoFragment extends Fragment {
    private Button buttonHide;
    private HidePressedListener hidePressedListener;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // менеджер компоновки, который позволяет получать доступ к layout с наших ресурсов
        View view = inflater.inflate(R.layout.info, container, false);
        buttonHide = view.findViewById(R.id.buttonhide1);
        buttonHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePressedListener.hide("info");
            }
        });
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    public void setHidePressedListener(StartActivity startActivity){
        hidePressedListener = startActivity;
    }
}
