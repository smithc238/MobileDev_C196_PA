package com.mySchool.mobiledev_c196_pa.data.entities;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class InstructorsWithCourses {
    @Embedded public Instructor instructor;
    @Relation(
            parentColumn = "instructorID",
            entity = Course.class,
            entityColumn = "courseID",
            associateBy = @Junction(CourseInstructorCrossRef.class)
    )
    public List<Course> courses;
}
