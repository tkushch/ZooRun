package com.example.zoorun;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

public class SaveRecordActivity extends Activity implements View.OnClickListener {
    private int record;

    private TextView tvrecord;
    private EditText editTextName;
    private Button save, openbd, back;

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
        save = findViewById(R.id.savetobdsaveact);
        save.setOnClickListener(this);
        openbd = findViewById(R.id.seebasedatabutton);
        openbd.setOnClickListener(this);
        back = findViewById(R.id.button3back);
        back.setOnClickListener(this);

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
        if (v == openbd){
            setContentView(R.layout.dblist);
            ArrayList<Records> r = mDBConnector.selectAll();
            ArrayList<Integer> rec = new ArrayList<>();
            for (Records record: r){
                rec.add(record.getValue());
            }
            //ДОПИСАТЬ
            ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, new ArrayList<Integer>(Arrays.asList(1, 5, 4, 2, 4)));

            ListView lv = findViewById(R.id.dblistview);
            lv.setAdapter(adapter);
        }
        if (v == back){
            Intent intent = new Intent(this, StartActivity.class);
            startActivity(intent);
        }
    }
}