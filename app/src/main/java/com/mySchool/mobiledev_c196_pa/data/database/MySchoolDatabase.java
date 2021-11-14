package com.mySchool.mobiledev_c196_pa.data.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.mySchool.mobiledev_c196_pa.data.dao.AssessmentDao;
import com.mySchool.mobiledev_c196_pa.data.dao.CourseDao;
import com.mySchool.mobiledev_c196_pa.data.dao.CourseInstructorCrossRefDao;
import com.mySchool.mobiledev_c196_pa.data.dao.InstructorDao;
import com.mySchool.mobiledev_c196_pa.data.dao.TermDao;
import com.mySchool.mobiledev_c196_pa.data.entities.Assessment;
import com.mySchool.mobiledev_c196_pa.data.entities.Course;
import com.mySchool.mobiledev_c196_pa.data.entities.CourseInstructorCrossRef;
import com.mySchool.mobiledev_c196_pa.data.entities.ExamType;
import com.mySchool.mobiledev_c196_pa.data.entities.Instructor;
import com.mySchool.mobiledev_c196_pa.data.entities.Status;
import com.mySchool.mobiledev_c196_pa.data.entities.Term;
import com.mySchool.mobiledev_c196_pa.data.repository.MySchoolExecutorService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Database(entities = {Term.class,Course.class,Instructor.class, Assessment.class,
        CourseInstructorCrossRef.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class MySchoolDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "mySchoolDB";
    private static volatile MySchoolDatabase INSTANCE;

    public static synchronized MySchoolDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    MySchoolDatabase.class,DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(cleanUp)
                    .addCallback(mySchoolCallback)
                    .build();
        }
        return INSTANCE;
    }

    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    public abstract InstructorDao instructorDao();
    public abstract AssessmentDao assessmentDao();
    public abstract CourseInstructorCrossRefDao crossRefDao();

    private static RoomDatabase.Callback mySchoolCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            MySchoolExecutorService.getService().execute(() -> {
                ZonedDateTime today = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8,0), ZoneId.systemDefault());
                INSTANCE.termDao().insert(new Term("Term 1", today, today.plusMonths(6)));
                INSTANCE.termDao().insert(new Term("Term 2",today.plusMonths(6),today.plusMonths(12)));
                INSTANCE.termDao().insert(new Term("Term 3",today.plusMonths(12),today.plusMonths(18)));
                INSTANCE.courseDao().insert(new Course("Course 1", Status.DROPPED,today,today.plusMonths(1),"course 1 note", 1L));
                INSTANCE.courseDao().insert(new Course("Course 2", Status.COMPLETE,today.plusMonths(1),today.plusMonths(2),"course 2 note", 1L));
                INSTANCE.courseDao().insert(new Course("Course 3", Status.IN_PROGRESS,today.plusMonths(6),today.plusMonths(7),"course 3 note", 2L));
                INSTANCE.courseDao().insert(new Course("Course 4", Status.PLAN_TO_TAKE,today.plusMonths(12),today.plusMonths(13),null, 3L));
                INSTANCE.instructorDao().insert(new Instructor("Joe Walker","578-354-2256","joe.walker@mySchool.com"));
                INSTANCE.instructorDao().insert(new Instructor("John Stark","578-354-8843","john.stark@mySchool.com"));
                INSTANCE.instructorDao().insert(new Instructor("Tony Park","578-354-6547","tony.park@mySchool.com"));
                INSTANCE.instructorDao().insert(new Instructor("Peter Banner","578-354-7148","peter.banner@mySchool.com"));
                INSTANCE.instructorDao().insert(new Instructor("Robert Alianovna","578-354-1980","robert.alianovna@mySchool.com"));
                INSTANCE.instructorDao().insert(new Instructor("Natalia Danvers","578-354-9527","natalia.danvers@mySchool.com"));
                INSTANCE.assessmentDao().insert(new Assessment("Course 1 exam",today.plusMonths(1).minusDays(2),today.plusMonths(1).minusDays(1),"Course 1 exam description", ExamType.OBJECTIVE,1L));
                INSTANCE.assessmentDao().insert(new Assessment("Course 2 exam",today.plusMonths(2).minusDays(2),today.plusMonths(2).minusDays(1),"Course 2 exam description", ExamType.OBJECTIVE,2L));
                INSTANCE.assessmentDao().insert(new Assessment("Course 2 exam",today.plusMonths(2).minusDays(2),today.plusMonths(2).minusDays(1),"Course 2 exam description", ExamType.PERFORMANCE,2L));
                INSTANCE.assessmentDao().insert(new Assessment("Course 3 exam",today.plusMonths(7).minusDays(2),today.plusMonths(7).minusDays(1),"Course 3 exam description", ExamType.PERFORMANCE,3L));
                INSTANCE.assessmentDao().insert(new Assessment("Course 4 exam",today.plusMonths(13).minusDays(2),today.plusMonths(13).minusDays(1),"Course 4 exam description", ExamType.OBJECTIVE,4L));
                INSTANCE.assessmentDao().insert(new Assessment("Course 4 exam",today.plusMonths(13).minusDays(2),today.plusMonths(13).minusDays(1),"Course 4 exam description", ExamType.PERFORMANCE,4L));
                INSTANCE.crossRefDao().insert(new CourseInstructorCrossRef(1,1));
                INSTANCE.crossRefDao().insert(new CourseInstructorCrossRef(1,2));
                INSTANCE.crossRefDao().insert(new CourseInstructorCrossRef(2,2));
                INSTANCE.crossRefDao().insert(new CourseInstructorCrossRef(2,3));
                INSTANCE.crossRefDao().insert(new CourseInstructorCrossRef(3,4));
                INSTANCE.crossRefDao().insert(new CourseInstructorCrossRef(3,5));
                INSTANCE.crossRefDao().insert(new CourseInstructorCrossRef(3,6));
                INSTANCE.crossRefDao().insert(new CourseInstructorCrossRef(4,4));
                INSTANCE.crossRefDao().insert(new CourseInstructorCrossRef(4,5));
                INSTANCE.crossRefDao().insert(new CourseInstructorCrossRef(4,6));
            });
        }
    };

    private static RoomDatabase.Callback cleanUp = new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            MySchoolExecutorService.getService().execute(() -> {
                //If backpressed or app shutdown before a save can be processed can leave
                // bad data that's inaccessible to the user to remove.
                INSTANCE.assessmentDao().cleanAssessments();
                INSTANCE.courseDao().cleanCourses();
            });
        }
    };
}
