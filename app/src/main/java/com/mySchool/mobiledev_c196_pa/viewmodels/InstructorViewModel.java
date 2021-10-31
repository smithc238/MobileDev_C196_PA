package com.mySchool.mobiledev_c196_pa.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.mySchool.mobiledev_c196_pa.data.entities.Instructor;
import com.mySchool.mobiledev_c196_pa.data.repository.InstructorRepo;

import java.util.List;

public class InstructorViewModel extends AndroidViewModel {
    private InstructorRepo repo;
    private LiveData<List<Instructor>> allInstructors;
    private MutableLiveData<Instructor> instuctor;


    public InstructorViewModel(@NonNull Application application) {
        super(application);
        repo = new InstructorRepo(application);
        allInstructors = repo.getAllInstructors();
        instuctor = new MutableLiveData<>();
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

    public LiveData<List<Instructor>> getInstructorById(long id) {
        return repo.getInstructorById(id);
    }

    public LiveData<List<Instructor>> getAllInstructors() {
        return allInstructors;
    }

    public void setInstructor(Instructor instructor) {
        this.instuctor.setValue(instructor);
    }

    public LiveData<Instructor> getInstructor() {
        return instuctor;
    }
}
