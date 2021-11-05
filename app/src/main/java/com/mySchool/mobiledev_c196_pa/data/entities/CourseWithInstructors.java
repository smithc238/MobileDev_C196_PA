package com.mySchool.mobiledev_c196_pa.data.entities;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class CourseWithInstructors {
    @Embedded public Course course;
    @Relation(
            parentColumn = "courseID",
            entity = Instructor.class,
            entityColumn = "instructorID",
            associateBy = @Junction(CourseInstructorCrossRef.class)
    )
    public List<Instructor> instructors;
}
