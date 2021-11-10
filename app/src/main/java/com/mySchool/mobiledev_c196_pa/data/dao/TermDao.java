package com.mySchool.mobiledev_c196_pa.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mySchool.mobiledev_c196_pa.data.entities.Course;
import com.mySchool.mobiledev_c196_pa.data.entities.Term;

import java.util.List;

@Dao
public interface TermDao {
    @Insert
    long insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);

    @Query("DELETE FROM Terms")
    void deleteAllTerms();

    @Query("SELECT * FROM Terms WHERE id = :id")
    LiveData<List<Term>> getTermById(long id);

    @Query("SELECT * FROM Terms")
    LiveData<List<Term>> getAllTerms();

    @Query("SELECT * FROM courses WHERE termID = :id")
    LiveData<List<Course>> getAssociatedCourses(long id);
}
