package com.mySchool.mobiledev_c196_pa.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.mySchool.mobiledev_c196_pa.data.dao.TermDao;
import com.mySchool.mobiledev_c196_pa.data.database.MySchoolDatabase;
import com.mySchool.mobiledev_c196_pa.data.entities.Course;
import com.mySchool.mobiledev_c196_pa.data.entities.Term;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class TermRepo {
    private TermDao termDao;
    private final ExecutorService dbExecutor;

    public TermRepo(Application application) {
        MySchoolDatabase db = MySchoolDatabase.getInstance(application);
        termDao = db.termDao();
        dbExecutor = MySchoolExecutorService.getService();
    }

    public long insert(Term term) {
        long rowID = 0;
        Callable<Long> insertCallable = () -> termDao.insert(term);
        Future<Long> future = dbExecutor.submit(insertCallable);
        try {
            rowID = future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return rowID;
    }

    public void update(Term term) {
        dbExecutor.execute(()-> termDao.update(term));
    }

    public void delete(Term term) {
        dbExecutor.execute(()-> termDao.delete(term));
    }

    public void deleteAllTerms() {
        dbExecutor.execute(()-> termDao.deleteAllTerms());
    }

    public LiveData<List<Term>> getTermById(long id) {
        return termDao.getTermById(id);
    }

    public LiveData<List<Term>> getAllTerms() {
        return termDao.getAllTerms();
    }

    public LiveData<List<Course>> getAssociatedCourses(long id) {
        return termDao.getAssociatedCourses(id);
    }
}
