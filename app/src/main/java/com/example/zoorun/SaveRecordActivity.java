package com.example.zoorun;

import android.content.Context;
import android.content.Intent;
import android.view.*;
import android.widget.*;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zoorun.dataBase.DBRecords;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SaveRecordActivity extends AppCompatActivity implements View.OnClickListener {
    private int record;

    private TextView tvrecord;
    private EditText editTextName;
    private Button save, openbd, back, dbClearAll, dbBack;
    private ListView mListView;
    private DBRecords mDBConnector;
    private Context mContext;
    private MyAdapter myAdapter;
    private DatabaseReference mFireDataBase;
    private String RECORDS_KEY = "Records";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_record);
        setAll();
        mFireDataBase = FirebaseDatabase.getInstance("https://cardriving-519ac-default-rtdb.firebaseio.com/").getReference(RECORDS_KEY);
    }

    protected void setAll() {
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
                //db in GoogleFireBase
                mFireDataBase.push().setValue(new User(nickname, record));

                //db on device
                mDBConnector.insert(nickname, record);

                //Toast
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(getApplicationContext(),
                        R.string.saved,
                        duration);
                toast.show();

            } else {
                editTextName.setText("Недопустимое имя");
            }
        } else if (v == openbd) {
            setContentView(R.layout.dblist);
            dbClearAll = findViewById(R.id.dblistclearall);
            dbBack = findViewById(R.id.dblistback);
            dbClearAll.setOnClickListener(this);
            dbBack.setOnClickListener(this);
            ArrayList<Record> records = mDBConnector.selectAll();
            Collections.sort(records, new Comparator<Record>() {
                @Override
                public int compare(Record o1, Record o2) {
                    return -o1.compareTo(o2);
                }
            });
            myAdapter = new MyAdapter(this, records);
            mListView = findViewById(R.id.dblistview);
            mListView.setAdapter(myAdapter);
            registerForContextMenu(mListView);

        } else if (v == back) {
            Intent intent = new Intent(this, EndActivity.class);
            intent.putExtra("score", record);
            startActivity(intent);
        } else if (v == dbClearAll) {
            mDBConnector.deleteAll();
            updateList();
        } else if (v == dbBack) {
            setContentView(R.layout.activity_save_record);
            setAll();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_bd, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.bdmenudeleteitem:
                mDBConnector.delete(info.id);
                updateList();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void updateList() {
        ArrayList<Record> records = mDBConnector.selectAll();
        Collections.sort(records, new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                return -o1.compareTo(o2);
            }
        });
        myAdapter.setArrayMyData(records);
        myAdapter.notifyDataSetChanged();
    }

    public class User {
        private String name;
        private int value;

        public User(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}