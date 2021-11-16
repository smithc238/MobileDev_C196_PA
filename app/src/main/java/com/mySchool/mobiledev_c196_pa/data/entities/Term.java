package com.mySchool.mobiledev_c196_pa.data.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.ZonedDateTime;

@Entity(tableName = "Terms")
public class Term {
    @PrimaryKey(autoGenerate = true) private long id;
    @NonNull private String title;
    private ZonedDateTime start;
    private ZonedDateTime end;

    public Term(@NonNull String title, ZonedDateTime start, ZonedDateTime end) {
        this.title = title;
        this.start = start;
        this.end = end;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }
}