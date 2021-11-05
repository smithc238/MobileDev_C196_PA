package com.mySchool.mobiledev_c196_pa.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.mySchool.mobiledev_c196_pa.data.entities.Assessment;
import com.mySchool.mobiledev_c196_pa.data.entities.Course;
import com.mySchool.mobiledev_c196_pa.data.entities.Instructor;
import com.mySchool.mobiledev_c196_pa.data.entities.InstructorsWithCourses;

import java.util.List;

@Dao
public interface CourseDao {
    @Insert
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("DELETE FROM courses")
    void deleteAllCourses();

    @Query("SELECT * FROM courses")
    LiveData<List<Course>> getAllCourses();

    @Query("SELECT * FROM courses WHERE courseID = :id")
    LiveData<List<Course>> getCourseById(long id);

    @Transaction
    @Query("SELECT * FROM instructors WHERE instructorID IN (SELECT instructorID FROM CourseInstructorcrossref WHERE courseID = :id);")
    LiveData<List<Instructor>> getAssociatedInstructors(long id);

    @Query("SELECT * FROM assessments WHERE courseId = :id")
    LiveData<List<Assessment>> getAssociatedAssessments(long id);
}
