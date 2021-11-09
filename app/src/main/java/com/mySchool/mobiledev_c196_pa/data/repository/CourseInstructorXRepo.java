package com.mySchool.mobiledev_c196_pa.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.mySchool.mobiledev_c196_pa.data.dao.CourseInstructorCrossRefDao;
import com.mySchool.mobiledev_c196_pa.data.database.MySchoolDatabase;
import com.mySchool.mobiledev_c196_pa.data.entities.CourseInstructorCrossRef;
import com.mySchool.mobiledev_c196_pa.data.entities.CourseWithInstructors;
import com.mySchool.mobiledev_c196_pa.data.entities.InstructorsWithCourses;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class CourseInstructorXRepo {
    private CourseInstructorCrossRefDao crossRefDao;
    private final ExecutorService dbExecutor;

    public CourseInstructorXRepo(Application application) {
        MySchoolDatabase db = MySchoolDatabase.getInstance(application);
        crossRefDao = db.crossRefDao();
        dbExecutor = MySchoolExecutorService.getService();
    }

    public void insert(CourseInstructorCrossRef crossRef) {
        dbExecutor.execute(() -> crossRefDao.insert(crossRef));
    }

    public void update(CourseInstructorCrossRef crossRef) {
        dbExecutor.execute(() -> crossRefDao.update(crossRef));
    }

    public void delete(CourseInstructorCrossRef crossRef) {
        dbExecutor.execute(() -> crossRefDao.delete(crossRef));
    }

    public void removeAllCourseInstructors(long courseId) {
        dbExecutor.execute(() -> crossRefDao.removeAllCourseInstructors(courseId));
    }

    public LiveData<List<CourseWithInstructors>> getCoursesWithInstructors() {
        return crossRefDao.getCoursesWithInstructors();
    }

    public LiveData<List<InstructorsWithCourses>> getInstructorsWithCourses() {
        return crossRefDao.getInstructorsWithCourses();
    }
}
