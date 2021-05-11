package com.example.zoorun;

import java.io.Serializable;
import java.util.Comparator;

public class Record implements Serializable, Comparable<Record> {
    private long id;
    private String name;
    private int value;
    private String strId;

    public Record(long id, String name, int value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public Record(long id, String strId, String name, int value) {
        this.id = id;
        this.strId = strId;
        this.name = name;
        this.value = value;
    }

    public String getStrId() {
        return strId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }


    @Override
    public int compareTo(Record o) {
        return value - o.value;
    }
}