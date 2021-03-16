package com.example.zoorun;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.os.Bundle;
import androidx.annotation.NonNull;

import java.util.ArrayList;

public class SaveRecordActivity extends Activity implements View.OnClickListener {
    private int record;

    private TextView tvrecord;
    private EditText editTextName;
    private Button save, openbd, back, dbClearAll, dbBack;
    private ListView mListView;
    private DBRecords mDBConnector;
    private Context mContext;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_record);
        setAll();
    }

    protected void setAll(){
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

        mDBConnector = new DBRecords(this);
        mContext = this;
    }

    @Override
    public void onClick(View v) {
        if (v == save) {
            String nickname = editTextName.getText().toString();
            boolean flag = true;
            for (int i = 0; i < nickname.length() && flag; i++) {
                if (!Character.isLetter(nickname.charAt(i))) {
                    flag = false;
                }
            }
            if (nickname.length() == 0) {
                flag = false;
            }
            if (flag) {
                mDBConnector.insert(nickname, record);
                editTextName.setText("Сохранено");
            } else {
                editTextName.setText("Недопустимое имя");
            }
        } else if (v == openbd) {
            setContentView(R.layout.dblist);
            dbClearAll = findViewById(R.id.dblistclearall);
            dbBack = findViewById(R.id.dblistback);
            dbClearAll.setOnClickListener(this);
            dbBack.setOnClickListener(this);


            myAdapter = new MyAdapter(this, mDBConnector.selectAll());
            mListView = findViewById(R.id.dblistview);
            mListView.setAdapter(myAdapter);
            registerForContextMenu(mListView);
        } else if (v == back) {
            Intent intent = new Intent(this, EndActivity.class);
            intent.putExtra("score", record);
            startActivity(intent);
        } else if (v == dbClearAll) {
            mDBConnector.deleteAll();
            myAdapter.setArrayMyData(mDBConnector.selectAll());
            myAdapter.notifyDataSetChanged();
        } else if (v == dbBack) {
            setContentView(R.layout.activity_save_record);
            setAll();
        }
    }


}