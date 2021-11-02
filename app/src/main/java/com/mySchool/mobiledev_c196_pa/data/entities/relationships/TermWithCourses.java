package com.mySchool.mobiledev_c196_pa.data.entities.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.mySchool.mobiledev_c196_pa.data.entities.Course;
import com.mySchool.mobiledev_c196_pa.data.entities.Term;

import java.util.List;

public class TermWithCourses {
    @Embedded public Term term;
    @Relation(
            parentColumn = "id",
            entityColumn = "termId"
    )
    public List<Course> termCourses;
}
