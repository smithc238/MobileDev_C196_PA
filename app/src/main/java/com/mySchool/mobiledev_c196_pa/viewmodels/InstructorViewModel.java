package com.mySchool.mobiledev_c196_pa.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mySchool.mobiledev_c196_pa.data.entities.Instructor;
import com.mySchool.mobiledev_c196_pa.data.repository.InstructorRepo;

import java.util.List;

public class InstructorViewModel extends AndroidViewModel {
    private InstructorRepo repo;
    private LiveData<List<Instructor>> allInstructors;


    public InstructorViewModel(@NonNull Application application) {
        super(application);
        repo = new InstructorRepo(application);
        allInstructors = repo.getAllInstructors();
    }

    public void insert(Instructor instructor) {
        repo.insert(instructor);
    }

    public void update(Instructor instructor) {
        repo.update(instructor);
    }

    public void delete(Instructor instructor) {
        repo.delete(instructor);
    }

    public void deleteAllInstructors() {
        repo.deleteAllInstructors();
    }

    public LiveData<List<Instructor>> getAllInstructors() {
        return allInstructors;
    }
}
