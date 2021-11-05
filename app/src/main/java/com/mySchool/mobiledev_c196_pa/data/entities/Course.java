package com.mySchool.mobiledev_c196_pa.data.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.ZonedDateTime;

@Entity(tableName = "Courses",
        foreignKeys = @ForeignKey(entity = Term.class,
        parentColumns = "id", childColumns = "termID",
                onDelete = CASCADE))
public class Course {
    @PrimaryKey(autoGenerate = true) private long id;
    @NonNull private String title;
    private Status status;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private String note;
    @ColumnInfo(defaultValue = "null", index = true)
    @Nullable
    private Long termID;

    public Course(@NonNull String title, Status status, ZonedDateTime start, ZonedDateTime end, String note, @Nullable Long termID) {
        this.title = title;
        this.status = status;
        this.start = start;
        this.end = end;
        this.note = note;
        this.termID = termID;
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

    @NonNull
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getTermID() {
        return termID;
    }

    public void setTermID(long termID) {
        this.termID = termID;
    }
}
