package com.example.myapplication3.beans;

public class ItemBean {
    public long count;
    public String text;
    public ItemBean() {
        this.count = 0;
        this.text = "";
    }
    public ItemBean(long count, String text) {
        this.count = count;
        this.text = text;
    }
}
