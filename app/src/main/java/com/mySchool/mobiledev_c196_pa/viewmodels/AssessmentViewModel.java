package com.mySchool.mobiledev_c196_pa.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mySchool.mobiledev_c196_pa.data.entities.Assessment;
import com.mySchool.mobiledev_c196_pa.data.repository.AssessmentRepo;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {
    private AssessmentRepo repo;
    private LiveData<List<Assessment>> allAssessments;

    public AssessmentViewModel(@NonNull Application application) {
        super(application);
        repo = new AssessmentRepo(application);
        allAssessments = repo.getAllAssessments();
    }

    public void insert(Assessment assessment) {repo.insert(assessment);}

    public void update(Assessment assessment) {repo.update(assessment);}

    public void delete(Assessment assessment) {repo.delete(assessment);}

    public void deleteAllAssessments() {repo.deleteAllAssessments();}

    public LiveData<List<Assessment>> getAllAssessments() {return allAssessments;}

    public LiveData<List<Assessment>> getAssessmentById(long id) {
        return repo.getAssessmentById(id);
    }
}
