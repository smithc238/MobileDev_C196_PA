package com.mySchool.mobiledev_c196_pa.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.mySchool.mobiledev_c196_pa.data.dao.TermDao;
import com.mySchool.mobiledev_c196_pa.data.database.MySchoolDatabase;
import com.mySchool.mobiledev_c196_pa.data.entities.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class TermRepo {
    private TermDao termDao;
    private LiveData<List<Term>> allTerms;
    private final ExecutorService dbExecutor;


    public TermRepo(Application application) {
        MySchoolDatabase db = MySchoolDatabase.getInstance(application);
        termDao = db.termDao();
        allTerms = termDao.getAllTerms();
        dbExecutor = MySchoolExecutorService.getService();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insert(Term term) {
       dbExecutor.execute(() -> {
           termDao.insert(term);
       });
    }

    public void update(Term term) {
        dbExecutor.execute(()-> {
            termDao.update(term);
        });
    }

    public void delete(Term term) {
        dbExecutor.execute(()-> {
            termDao.delete(term);
        });
    }

    public void deleteAllTerms() {
        dbExecutor.execute(()-> {
            termDao.deleteAllTerms();
        });
    }

    public LiveData<List<Term>> getAllTerms() {
        return allTerms;
    }
}
