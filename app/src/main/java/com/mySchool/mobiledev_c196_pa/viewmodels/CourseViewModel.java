package com.mySchool.mobiledev_c196_pa.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mySchool.mobiledev_c196_pa.data.entities.Assessment;
import com.mySchool.mobiledev_c196_pa.data.entities.Course;
import com.mySchool.mobiledev_c196_pa.data.entities.Instructor;
import com.mySchool.mobiledev_c196_pa.data.repository.CourseRepo;

import java.util.ArrayList;
import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    private CourseRepo repo;
    private LiveData<List<Course>> allCourses;
    private MutableLiveData<List<Course>> workingList;

    public CourseViewModel(@NonNull Application application) {
        super(application);
        repo = new CourseRepo(application);
        allCourses = repo.getAllCourses();
        workingList = new MutableLiveData<>(new ArrayList<>());
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

    public void addToWorkingList(Course course) {
        workingList.getValue().add(course);
    }

    public void setWorkingList(List<Course> courses) {
        workingList.setValue(courses);
    }

    public void removeFromWorkingList(Course course) {
        workingList.getValue().removeIf(item ->
                item.getCourseID() == course.getCourseID());
    }

    public void updateFKs(long termId, List<Course> courses) {
        for (Course course : courses) {
            course.setTermID(termId);
            update(course);
        }
    }

    public LiveData<List<Course>> getWorkingList() {
        return workingList;
    }
}
