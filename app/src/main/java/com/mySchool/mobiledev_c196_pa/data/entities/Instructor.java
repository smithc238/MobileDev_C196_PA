package com.mySchool.mobiledev_c196_pa.data.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Instructors")
public class Instructor {
    @PrimaryKey(autoGenerate = true) private long id;
    @NonNull private String name;
    @NonNull private String phone;
    @NonNull private String email;

}
