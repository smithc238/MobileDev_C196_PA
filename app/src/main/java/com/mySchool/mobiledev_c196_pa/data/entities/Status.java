package com.mySchool.mobiledev_c196_pa.data.entities;

public enum Status {
    PLAN_TO_TAKE(0, "Plan to take"),
    IN_PROGRESS(1, "In progress"),
    COMPLETE(2, "Complete"),
    DROPPED(3, "Dropped");

    private String status;
    private int num;
    Status(int num, String status) {
        this.num = num;
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }

    public String getStatus() {
        return status;
    }

    public int getNum() {
        return num;
    }
}
