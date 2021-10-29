package com.mySchool.mobiledev_c196_pa.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mySchool.mobiledev_c196_pa.data.entities.Instructor;

import java.util.List;

@Dao
public interface InstructorDao {
    @Insert
    void insert(Instructor instructor);

    @Update
    void update(Instructor instructor);

    @Delete
    void delete(Instructor instructor);

    @Query("DELETE FROM instructors")
    void deleteAllInstructors();

    @Query("SELECT * FROM instructors")
    LiveData<List<Instructor>> getAllInstructors();
}
