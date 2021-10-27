package com.mySchool.mobiledev_c196_pa.data.entities;

import androidx.room.Entity;

import java.time.ZonedDateTime;

@Entity(tableName = "Assessments")
public class Assessment {
    private String title;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private String description;
    private ExamType type;
}
