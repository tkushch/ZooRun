package com.example.zoorun.dataBase;

public class User {
    private String name = "";
    private int value = 0;


    public User(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public User() {
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
