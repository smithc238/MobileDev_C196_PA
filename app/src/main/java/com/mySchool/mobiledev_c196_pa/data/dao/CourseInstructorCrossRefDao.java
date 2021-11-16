package com.mySchool.mobiledev_c196_pa.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.mySchool.mobiledev_c196_pa.data.entities.CourseInstructorCrossRef;
import com.mySchool.mobiledev_c196_pa.data.entities.CourseWithInstructors;
import com.mySchool.mobiledev_c196_pa.data.entities.InstructorsWithCourses;

import java.util.List;

@Dao
public interface CourseInstructorCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CourseInstructorCrossRef courseInstructorCrossRef);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(CourseInstructorCrossRef courseInstructorCrossRef);

    @Delete
    void delete(CourseInstructorCrossRef courseInstructorCrossRef);

    @Transaction
    @Query("SELECT * FROM courses")
    LiveData<List<CourseWithInstructors>> getCoursesWithInstructors();

    @Transaction
    @Query("SELECT * FROM instructors")
    LiveData<List<InstructorsWithCourses>> getInstructorsWithCourses();
}
