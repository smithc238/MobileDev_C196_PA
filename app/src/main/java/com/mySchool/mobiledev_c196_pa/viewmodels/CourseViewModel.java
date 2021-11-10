package com.mySchool.mobiledev_c196_pa.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mySchool.mobiledev_c196_pa.data.entities.Assessment;
import com.mySchool.mobiledev_c196_pa.data.entities.Course;
import com.mySchool.mobiledev_c196_pa.data.entities.Instructor;
import com.mySchool.mobiledev_c196_pa.data.repository.CourseRepo;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    private CourseRepo repo;
    private LiveData<List<Course>> allCourses;

    public CourseViewModel(@NonNull Application application) {
        super(application);
        repo = new CourseRepo(application);
        allCourses = repo.getAllCourses();
    }

    public long insert(Course course) {
        return repo.insert(course);
    }

    public void update(Course course) {
        repo.update(course);
    }

    public void delete(Course course) {
        repo.delete(course);
    }

    public void deleteAllCourses() {
        repo.deleteAllCourses();
    }

    public LiveData<List<Course>> getCourseById(long id) {
        return repo.getCourseById(id);
    }

    public LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }

    public LiveData<List<Instructor>> getAssociatedInstructors(long id) {
        return repo.getAssociatedInstructors(id);
    }

    public LiveData<List<Assessment>> getAssociatedAssessments(long id) {
        return repo.getAssociatedAssessments(id);
    }
}
