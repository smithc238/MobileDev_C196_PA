package com.mySchool.mobiledev_c196_pa.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mySchool.mobiledev_c196_pa.data.entities.Assessment;
import com.mySchool.mobiledev_c196_pa.data.repository.AssessmentRepo;

import java.util.ArrayList;
import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {
    private AssessmentRepo repo;
    private LiveData<List<Assessment>> allAssessments;
    private MutableLiveData<List<Assessment>> workingList;
    private MutableLiveData<List<Assessment>> pendingDelete;

    public AssessmentViewModel(@NonNull Application application) {
        super(application);
        repo = new AssessmentRepo(application);
        allAssessments = repo.getAllAssessments();
        workingList = new MutableLiveData<>(new ArrayList<>());
        pendingDelete = new MutableLiveData<>(new ArrayList<>());
    }

    public long insert(Assessment assessment) {
        return repo.insert(assessment);
    }

    public void update(Assessment assessment) {
        repo.update(assessment);
    }

    public void delete(Assessment assessment) {
        repo.delete(assessment);
    }

    public void deleteAllAssessments() {
        repo.deleteAllAssessments();
    }

    public LiveData<List<Assessment>> getAllAssessments() {
        return allAssessments;
    }

    public LiveData<List<Assessment>> getAssessmentById(long id) {
        return repo.getAssessmentById(id);
    }

    public void addToWorkingList(Assessment assessment) {
        workingList.getValue().add(assessment);
    }

    public void setWorkingList(List<Assessment> assessments) {
        workingList.setValue(assessments);
    }

    public void removeFromWorkingList(Assessment assessment) {
        //Adjusted to match instructor
        workingList.getValue().removeIf(item ->
                item.getId() == assessment.getId());
    }

    public LiveData<List<Assessment>> getWorkingList() {
        return workingList;
    }

    public void updateAssessmentFKeys(Long id, List<Assessment> assessments) {
        for (Assessment assessment: assessments ) {
            assessment.setCourseId(id);
            update(assessment);
        }
    }

    public void addToPendingDelete(Assessment assessment) {
        pendingDelete.getValue().add(assessment);
    }

    public void deletePending() {
        for (Assessment assessment : pendingDelete.getValue()) {
            delete(assessment);
        }
    }

    public LiveData<List<Assessment>> getPendingDelete() {
        return pendingDelete;
    }
}
