package com.mySchool.mobiledev_c196_pa.data.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Instructors")
public class Instructor {
    @PrimaryKey(autoGenerate = true) private long instructorID;
    @NonNull private String name;
    @NonNull private String phone;
    @NonNull private String email;

    public Instructor(@NonNull String name, @NonNull String phone, @NonNull String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public long getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(long instructorID) {
        this.instructorID = instructorID;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getPhone() {
        return phone;
    }

    public void setPhone(@NonNull String phone) {
        this.phone = phone;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }
}
