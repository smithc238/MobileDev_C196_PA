package com.mySchool.mobiledev_c196_pa.data.entities;

public enum ExamType {
    OBJECTIVE("Objective"),
    PERFORMANCE("Performance");

    private String type;
    ExamType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
