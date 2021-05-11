package com.example.zoorun;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.zoorun.dataBase.DBRecords;
import com.example.zoorun.dataBase.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SaveRecordActivity extends AppCompatActivity implements View.OnClickListener {
    private int record;

    private TextView tvrecord;
    private EditText editTextName;
    private Button save, openbd, back, dbClearAll, dbBack, openNetBD;
    private ListView mListView;
    private DBRecords mDBConnector;
    private MyAdapter myAdapter;
    private FirebaseDatabase mFireDB;
    private DatabaseReference myRef;
    private String RECORDS_KEY = "Records";
    private Boolean isLocalBase = true;
    private ArrayList<Record> records;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_record);
        setAll();
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
        openNetBD = findViewById(R.id.firebase_button);
        openNetBD.setOnClickListener(this);
        back = findViewById(R.id.button3back);
        back.setOnClickListener(this);
        mDBConnector = new DBRecords(this);

        mFireDB = FirebaseDatabase.getInstance("https://cardriving-519ac-default-rtdb.firebaseio.com/");
        myRef = mFireDB.getReference(RECORDS_KEY);

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
                myRef.push().setValue(new User(nickname, record));

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
            isLocalBase = true;
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


        } else if (v == openNetBD) {
            setContentView(R.layout.dblist);
            isLocalBase = false;
            dbClearAll = findViewById(R.id.dblistclearall);
            dbBack = findViewById(R.id.dblistback);
            dbClearAll.setOnClickListener(this);
            dbBack.setOnClickListener(this);
            records = new ArrayList<>();
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
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (records.size() > 0) records.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        User user = ds.getValue(User.class);
                        String id = ds.getKey();
                        assert user != null;
                        Record record = new Record((long) id.hashCode(), id, user.getName(), user.getValue());
                        records.add(record);
                    }
                    Collections.sort(records, new Comparator<Record>() {
                        @Override
                        public int compare(Record o1, Record o2) {
                            return -o1.compareTo(o2);
                        }
                    });
                    myAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            myRef.addValueEventListener(valueEventListener);

        } else if (v == back) {
            Intent intent = new Intent(this, EndActivity.class);
            intent.putExtra("score", record);
            startActivity(intent);
        } else if (v == dbClearAll) {
            if (isLocalBase) {
                mDBConnector.deleteAll();
                updateList();
            } else {
                myRef.removeValue();
            }

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
                if (isLocalBase) {
                    mDBConnector.delete(info.id);
                    updateList();
                }
                else{
                    for (Record record: records){
                        if (record.getId() == info.id){
                            myRef.child(record.getStrId()).removeValue();
                            break;
                        }
                    }
                }
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

}