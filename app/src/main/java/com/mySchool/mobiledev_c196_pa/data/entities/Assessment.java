package com.mySchool.mobiledev_c196_pa.data.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.ZonedDateTime;

@Entity(tableName = "Assessments", foreignKeys = @ForeignKey(
        entity = Course.class, parentColumns = "courseID",
        childColumns = "courseId", onDelete = CASCADE))
public class Assessment {
    @PrimaryKey(autoGenerate = true) private long id;
    @NonNull private String title;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private String description;
    @NonNull private ExamType type;
    private Long courseId;

    public Assessment(@NonNull String title, ZonedDateTime start, ZonedDateTime end, String description, @NonNull ExamType type, Long courseId) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.description = description;
        this.type = type;
        this.courseId = courseId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    public ExamType getType() {
        return type;
    }

    public void setType(@NonNull ExamType type) {
        this.type = type;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
