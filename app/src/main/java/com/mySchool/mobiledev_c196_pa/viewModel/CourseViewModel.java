package com.mySchool.mobiledev_c196_pa.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mySchool.mobiledev_c196_pa.data.entities.Course;
import com.mySchool.mobiledev_c196_pa.data.repository.CourseRepo;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    private CourseRepo repo;
    private LiveData<List<Course>> allCourses;
    private LiveData<List<Course>> associatedCourses;

    public CourseViewModel(@NonNull Application application) {
        super(application);
        repo = new CourseRepo(application);
        allCourses = repo.getAllCourses();
    }

    public void insert(Course course) {repo.insert(course);}

    public void update(Course course) {repo.update(course);}

    public void delete(Course course) {repo.delete(course);}

    public void deleteAllCourses(Course course) {repo.deleteAllCourses();}

    public LiveData<List<Course>> getAssociatedCourses(long id) {
        associatedCourses = repo.getAssociatedCourses(id);
        return associatedCourses;
    }

    public LiveData<List<Course>> getAllCourses() {return allCourses;}
}
