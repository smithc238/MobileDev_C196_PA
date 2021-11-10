package com.mySchool.mobiledev_c196_pa.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mySchool.mobiledev_c196_pa.data.entities.Assessment;

import java.util.List;

@Dao
public interface AssessmentDao {
    @Insert
    long insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("DELETE FROM assessments")
    void deleteAllAssessments();

    @Query("SELECT * FROM assessments")
    LiveData<List<Assessment>> getAllAssessments();

    @Query("SELECT * FROM assessments WHERE id = :id")
    LiveData<List<Assessment>> getAssessmentById(long id);

    @Query("DELETE FROM assessments WHERE courseId IS NULL")
    void cleanAssessments();
}
