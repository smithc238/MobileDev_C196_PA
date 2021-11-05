package com.mySchool.mobiledev_c196_pa.data.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(primaryKeys = {"courseID", "instructorID"},
foreignKeys = {
        @ForeignKey(entity = Course.class,
        parentColumns = "courseID",
        childColumns = "courseID",
        onDelete = CASCADE,
        onUpdate = CASCADE),
        @ForeignKey(entity = Instructor.class,
        parentColumns = "instructorID",
        childColumns = "instructorID",
        onDelete = CASCADE,
        onUpdate = CASCADE)},
indices = {@Index("courseID"),@Index("instructorID")})
public class CourseInstructorCrossRef {
    public long courseID;
    public long instructorID;

    public CourseInstructorCrossRef(long courseID, long instructorID) {
        this.courseID = courseID;
        this.instructorID = instructorID;
    }

    public long getCourseID() {
        return courseID;
    }

    public void setCourseID(long courseID) {
        this.courseID = courseID;
    }

    public long getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(long instructorID) {
        this.instructorID = instructorID;
    }
}
