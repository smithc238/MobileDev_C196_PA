package com.mySchool.mobiledev_c196_pa.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mySchool.mobiledev_c196_pa.data.entities.CourseInstructorCrossRef;
import com.mySchool.mobiledev_c196_pa.data.entities.CourseWithInstructors;
import com.mySchool.mobiledev_c196_pa.data.entities.Instructor;
import com.mySchool.mobiledev_c196_pa.data.entities.InstructorsWithCourses;
import com.mySchool.mobiledev_c196_pa.data.repository.CourseInstructorXRepo;

import java.util.List;

public class CourseInstructorViewModel extends AndroidViewModel {
    private CourseInstructorXRepo repo;
    private LiveData<List<CourseWithInstructors>> courseWithInstructors;
    private LiveData<List<InstructorsWithCourses>> instructorsWithCourses;

    public CourseInstructorViewModel(@NonNull Application application) {
        super(application);
        repo = new CourseInstructorXRepo(application);
        courseWithInstructors = repo.getCoursesWithInstructors();
        instructorsWithCourses = repo.getInstructorsWithCourses();
    }

    public void insert(CourseInstructorCrossRef crossRef) {
        repo.insert(crossRef);
    }

    public void update(CourseInstructorCrossRef crossRef) {
        repo.update(crossRef);
    }

    public void delete(CourseInstructorCrossRef crossRef) {
        repo.delete(crossRef);
    }

    public LiveData<List<CourseWithInstructors>> getCoursesWithInstructors() {
        return courseWithInstructors;
    }

    public LiveData<List<InstructorsWithCourses>> getInstructorsWithCourses() {
        return instructorsWithCourses;
    }

    public void insertInstructorsForCourse(long courseId, List<Instructor> instructors) {
        for (Instructor instructor : instructors) {
            insert(new CourseInstructorCrossRef(courseId, instructor.getInstructorID()));
        }
    }

    public void removeInstructorsFromCourse(long courseId, List<Instructor> instructors) {
        for (Instructor instructor : instructors) {
            delete(new CourseInstructorCrossRef(courseId,instructor.getInstructorID()));
        }
    }
}
