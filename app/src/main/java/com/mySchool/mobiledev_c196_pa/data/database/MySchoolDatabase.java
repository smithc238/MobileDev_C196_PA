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
import com.mySchool.mobiledev_c196_pa.data.dao.InstructorDao;
import com.mySchool.mobiledev_c196_pa.data.dao.TermDao;
import com.mySchool.mobiledev_c196_pa.data.entities.Assessment;
import com.mySchool.mobiledev_c196_pa.data.entities.Course;
import com.mySchool.mobiledev_c196_pa.data.entities.ExamType;
import com.mySchool.mobiledev_c196_pa.data.entities.Instructor;
import com.mySchool.mobiledev_c196_pa.data.entities.Status;
import com.mySchool.mobiledev_c196_pa.data.entities.Term;
import com.mySchool.mobiledev_c196_pa.data.repository.MySchoolExecutorService;

import java.time.ZonedDateTime;

@Database(entities = {Term.class,Course.class,Instructor.class, Assessment.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class MySchoolDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "mySchoolDB";
    private static volatile MySchoolDatabase INSTANCE;

    public static synchronized MySchoolDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    MySchoolDatabase.class,DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(mySchoolCallback)
                    .build();
        }
        return INSTANCE;
    }

    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    public abstract InstructorDao instructorDao();
    public abstract AssessmentDao assessmentDao();

    private static RoomDatabase.Callback mySchoolCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            MySchoolExecutorService.getService().execute(() -> {
                ZonedDateTime today = ZonedDateTime.now();
                INSTANCE.termDao().insert(new Term("Term 1", today, today.plusMonths(1)));
                INSTANCE.termDao().insert(new Term("Term 2",today.plusMonths(1),today.plusMonths(2)));
                INSTANCE.termDao().insert(new Term("Term 3",today.plusMonths(2),today.plusMonths(3)));
                INSTANCE.courseDao().insert(new Course("Course 1", Status.DROPPED,today,today.plusWeeks(1),"course 1 note", 1L));
                INSTANCE.courseDao().insert(new Course("Course 2", Status.COMPLETE,today.plusWeeks(1),today.plusWeeks(2),"course 2 note", 1L));
                INSTANCE.courseDao().insert(new Course("Course 3", Status.IN_PROGRESS,today.plusWeeks(2),today.plusWeeks(3),"course 3 note", 2L));
                INSTANCE.courseDao().insert(new Course("Course 4", Status.PLAN_TO_TAKE,today.plusWeeks(3),today.plusWeeks(4),null, 3L));
                INSTANCE.instructorDao().insert(new Instructor("Angus Young","578-354-2256","angus.young@mySchool.com"));
                INSTANCE.instructorDao().insert(new Instructor("Bon Scott","578-354-8843","bon.scott@mySchool.com"));
                INSTANCE.instructorDao().insert(new Instructor("Phil Rudd","578-354-6547","phil.rudd@mySchool.com"));
                INSTANCE.instructorDao().insert(new Instructor("Cliff Williams","578-354-7148","cliff.williams@mySchool.com"));
                INSTANCE.instructorDao().insert(new Instructor("Brian Johnson","578-354-1980","brain.johnson@mySchool.com"));
                INSTANCE.instructorDao().insert(new Instructor("Malcolm Young","578-354-9527","malcolm.young@mySchool.com"));
                INSTANCE.assessmentDao().insert(new Assessment("Course 1 exam",today.plusWeeks(1).minusDays(1),today.plusWeeks(1).minusDays(1),"Course 1 exam description", ExamType.OBJECTIVE,1L));
                INSTANCE.assessmentDao().insert(new Assessment("Course 2 exam",today.plusWeeks(2).minusDays(1),today.plusWeeks(2).minusDays(1),"Course 2 exam description", ExamType.OBJECTIVE,2L));
                INSTANCE.assessmentDao().insert(new Assessment("Course 2 exam",today.plusWeeks(2).minusDays(1),today.plusWeeks(2).minusDays(1),"Course 2 exam description", ExamType.PERFORMANCE,2L));
                INSTANCE.assessmentDao().insert(new Assessment("Course 3 exam",today.plusWeeks(3).minusDays(1),today.plusWeeks(3).minusDays(1),"Course 3 exam description", ExamType.PERFORMANCE,3L));
                INSTANCE.assessmentDao().insert(new Assessment("Course 4 exam",today.plusWeeks(4).minusDays(1),today.plusWeeks(4).minusDays(1),"Course 4 exam description", ExamType.OBJECTIVE,4L));
                INSTANCE.assessmentDao().insert(new Assessment("Course 4 exam",today.plusWeeks(4).minusDays(1),today.plusWeeks(4).minusDays(1),"Course 4 exam description", ExamType.PERFORMANCE,4L));
            });
        }
    };
}
