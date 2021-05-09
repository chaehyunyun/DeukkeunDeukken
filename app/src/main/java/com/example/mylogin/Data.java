package com.example.mylogin;

public class Data {

    private int index;
    private int resId;
    private int count;
    private int set;

    public Data() {
    }

    public Data(int index, int resId, int count, int set) {
        this.index = index;
        this.resId = resId;
        this.count = count;
        this.set = set;
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

    public int getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public int getSet() {
        return set;
    }

    public void setSet(Integer set) {
        this.set = set;
    }

}