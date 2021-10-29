package com.mySchool.mobiledev_c196_pa.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.mySchool.mobiledev_c196_pa.data.dao.InstructorDao;
import com.mySchool.mobiledev_c196_pa.data.database.MySchoolDatabase;
import com.mySchool.mobiledev_c196_pa.data.entities.Instructor;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class InstructorRepo {
    private InstructorDao instructorDao;
    private final ExecutorService dbExecutor;

    public InstructorRepo(Application application) {
        MySchoolDatabase db = MySchoolDatabase.getInstance(application);
        instructorDao = db.instructorDao();
        dbExecutor = MySchoolExecutorService.getService();
    }
    public void insert(Instructor instructor) {
        dbExecutor.execute(() -> instructorDao.insert(instructor));
    }

    public void update(Instructor instructor) {
        dbExecutor.execute(() -> instructorDao.update(instructor));
    }

    public void delete(Instructor instructor) {
        dbExecutor.execute(() -> instructorDao.delete(instructor));
    }

    public void deleteAllInstructors() {
        dbExecutor.execute(() -> instructorDao.deleteAllInstructors());
    }

    public LiveData<List<Instructor>> getAllInstructors() {
        return instructorDao.getAllInstructors();
    }
}
