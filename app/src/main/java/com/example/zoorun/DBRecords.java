package com.example.zoorun;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBRecords {
    public static final String DATABASE_NAME = "simple.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "tableRecords";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_VALUE = "Value";


    public static final int NUM_COLUMN_ID = 0;
    public static final int NUM_COLUMN_NAME = 1;
    public static final int NUM_COLUMN_VALUE = 2;

    private SQLiteDatabase mDataBase;

    public DBRecords(Context context){
        OpenHelper mOpenHelper = new OpenHelper(context);
        mDataBase = mOpenHelper.getWritableDatabase();
    }

    public long insert(String name, int value) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_VALUE, value);
        return mDataBase.insert(TABLE_NAME, null, cv);
    }

    public int update(Record record) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_NAME, record.getName());
        cv.put(COLUMN_VALUE, record.getValue());
        return mDataBase.update(TABLE_NAME, cv, COLUMN_ID + " = ?",new String[] { String.valueOf(record.getId())});
    }
    public void deleteAll() {
        mDataBase.delete(TABLE_NAME, null, null);
    }

    public void delete(long id) {
        mDataBase.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { String.valueOf(id) });
    }

    public Record select(long id) {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        mCursor.moveToFirst();
        String name = mCursor.getString(NUM_COLUMN_NAME);
        int value = mCursor.getInt(NUM_COLUMN_VALUE);
        return new Record(id, name, value);
    }

    public ArrayList<Record> selectAll() {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, null, null, null, null, null);

        ArrayList<Record> arr = new ArrayList<Record>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_ID);
                String name = mCursor.getString(NUM_COLUMN_NAME);
                int value = mCursor.getInt(NUM_COLUMN_VALUE);
                arr.add(new Record(id, name, value));
            } while (mCursor.moveToNext());
        }
        return arr;
    }

    private class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME+ " TEXT, " +
                    COLUMN_VALUE + " INT);";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
