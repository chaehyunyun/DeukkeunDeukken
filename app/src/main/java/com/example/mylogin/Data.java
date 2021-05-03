package com.example.mylogin;

public class Data {

    private int index;
    private int resId;

    public Data() {
    }

    public Data(int index, int resId) {
        this.index = index;
        this.resId = resId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

}