package com.example.zoorun;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.zoorun.R;
import com.example.zoorun.Record;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private ArrayList<Record> arrayMyRecords;

    public MyAdapter(Context ctx, ArrayList<Record> arr) {
        mLayoutInflater = LayoutInflater.from(ctx);
        setArrayMyData(arr);
    }

    public void setArrayMyData(ArrayList<Record> arr){
        this.arrayMyRecords = arr;
    }


    @Override
    public int getCount() {
        return arrayMyRecords.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        Record mr = arrayMyRecords.get(position);
        if (mr != null) {
            return mr.getId();
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item, null);

        }

        TextView tvName = convertView.findViewById(R.id.tvbditemname);
        TextView tvValue = convertView.findViewById(R.id.tvbditemvalue);

        Record record = arrayMyRecords.get(position);
        tvName.setText(record.getName());
        tvValue.setText(String.valueOf(record.getValue()));

        return convertView;
    }
}
