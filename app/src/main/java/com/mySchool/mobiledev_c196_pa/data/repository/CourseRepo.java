package com.mySchool.mobiledev_c196_pa.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mySchool.mobiledev_c196_pa.data.dao.CourseDao;
import com.mySchool.mobiledev_c196_pa.data.database.MySchoolDatabase;
import com.mySchool.mobiledev_c196_pa.data.entities.Assessment;
import com.mySchool.mobiledev_c196_pa.data.entities.Course;
import com.mySchool.mobiledev_c196_pa.data.entities.Instructor;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class CourseRepo {
    private CourseDao courseDao;
    private final ExecutorService dbExecutor;
    private MutableLiveData<Long> rowID;

    public CourseRepo(Application application) {
        MySchoolDatabase db = MySchoolDatabase.getInstance(application);
        courseDao = db.courseDao();
        dbExecutor = MySchoolExecutorService.getService();
        rowID = new MutableLiveData<>();
    }

    public LiveData<Long> insert(Course course) {
        dbExecutor.execute(() -> {
            this.rowID.postValue(courseDao.insert(course));
        });
        return rowID;
    }

    public void update(Course course) {
        dbExecutor.execute(() -> courseDao.update(course));
    }

    public void delete(Course course) {
        dbExecutor.execute(() -> courseDao.delete(course));
    }

    public void deleteAllCourses() {
        dbExecutor.execute(() -> courseDao.deleteAllCourses());
    }

    public LiveData<List<Course>> getAllCourses() {
        return courseDao.getAllCourses();
    }

    public LiveData<List<Course>> getCourseById(long id) {
        return courseDao.getCourseById(id);
    }

    public LiveData<List<Instructor>> getAssociatedInstructors(long id) {
        return courseDao.getAssociatedInstructors(id);
    }

    public LiveData<List<Assessment>> getAssociatedAssessments(long id) {
        return courseDao.getAssociatedAssessments(id);
    }
}