package com.example.zoorun;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SaveRecordActivity extends Activity implements View.OnClickListener {
    private int record;

    private TextView tvrecord;
    private EditText editTextName;
    private Button save;

    private DBRecords mDBConnector;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_record);
        record = getIntent().getIntExtra("record", 0);
        tvrecord = findViewById(R.id.tvrecord);
        tvrecord.setText(String.valueOf(record));
        editTextName = findViewById(R.id.edit_text_name);
        save = findViewById(R.id.savetobd);

        mDBConnector=new DBRecords(this);
        mContext=this;
    }


    @Override
    public void onClick(View v) {
        if (v == save){
            String nickname = editTextName.getText().toString();
            boolean flag = true;
            for (int i = 0; i < nickname.length() && flag; i++){
                if (!Character.isLetter(nickname.charAt(i))){
                    flag = false;
                }
            }
            if (flag){
//                Records note = new Records(0, nickname, record); //  Дописать (ID)
                mDBConnector.insert(nickname, record);
            }
        }
    }
}