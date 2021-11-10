package com.mySchool.mobiledev_c196_pa.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.mySchool.mobiledev_c196_pa.data.dao.AssessmentDao;
import com.mySchool.mobiledev_c196_pa.data.database.MySchoolDatabase;
import com.mySchool.mobiledev_c196_pa.data.entities.Assessment;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class AssessmentRepo {
    private AssessmentDao assessmentDao;
    private final ExecutorService dbExecutor;

    public AssessmentRepo(Application application) {
        MySchoolDatabase db = MySchoolDatabase.getInstance(application);
        assessmentDao = db.assessmentDao();
        dbExecutor = MySchoolExecutorService.getService();
    }

    public long insert(Assessment assessment) {
        long rowID = 0;
        Callable<Long> insertCallable = () -> assessmentDao.insert(assessment);
        Future<Long> future = dbExecutor.submit(insertCallable);
        try {
            rowID = future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return rowID;
    }

    public void update(Assessment assessment) {
        dbExecutor.execute(() -> assessmentDao.update(assessment));
    }

    public void delete(Assessment assessment) {
        dbExecutor.execute(() -> assessmentDao.delete(assessment));
    }

    public void deleteAllAssessments() {
        dbExecutor.execute(() -> assessmentDao.deleteAllAssessments());
    }

    public LiveData<List<Assessment>> getAssessmentById(long id) {
        return assessmentDao.getAssessmentById(id);
    }

    public LiveData<List<Assessment>> getAllAssessments() {
        return assessmentDao.getAllAssessments();
    }
}
