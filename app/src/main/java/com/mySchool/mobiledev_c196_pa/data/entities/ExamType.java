package com.mySchool.mobiledev_c196_pa.data.entities;

import androidx.annotation.NonNull;

public enum ExamType {
    OBJECTIVE(0,"Objective"),
    PERFORMANCE(1,"Performance");

    private String type;
    private int num;
    ExamType(int num, String type) {
        this.num = num;
        this.type = type;
    }

    @NonNull
    @Override
    public String toString() {
        return type;
    }

    public String getType() {
        return type;
    }

    public int getNum() {
        return num;
    }
}
