package com.mySchool.mobiledev_c196_pa.data.database;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.mySchool.mobiledev_c196_pa.data.dao.CourseDao;
import com.mySchool.mobiledev_c196_pa.data.dao.InstructorDao;
import com.mySchool.mobiledev_c196_pa.data.dao.TermDao;
import com.mySchool.mobiledev_c196_pa.data.entities.Course;
import com.mySchool.mobiledev_c196_pa.data.entities.Instructor;
import com.mySchool.mobiledev_c196_pa.data.entities.Status;
import com.mySchool.mobiledev_c196_pa.data.entities.Term;
import com.mySchool.mobiledev_c196_pa.data.repository.MySchoolExecutorService;

import java.time.ZonedDateTime;

@Database(entities = {Term.class,Course.class,Instructor.class}, version = 1)
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

    private static RoomDatabase.Callback mySchoolCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            MySchoolExecutorService.getService().execute(() -> {
                ZonedDateTime today = ZonedDateTime.now();
                INSTANCE.termDao().insert(new Term("Term 0", today, today.plusMonths(1)));
                INSTANCE.termDao().insert(new Term("Term 1",today.plusMonths(1),today.plusMonths(2)));
                INSTANCE.termDao().insert(new Term("Term 2",today.plusMonths(2),today.plusMonths(3)));
                INSTANCE.courseDao().insert(new Course("Course 1", Status.DROPPED,today,today.plusWeeks(1),"course 1 note",1));
                INSTANCE.courseDao().insert(new Course("Course 2", Status.COMPLETE,today.plusWeeks(1),today.plusWeeks(2),"course 2 note",1));
                INSTANCE.courseDao().insert(new Course("Course 3", Status.IN_PROGRESS,today.plusWeeks(2),today.plusWeeks(3),"course 3 note",2));
                INSTANCE.courseDao().insert(new Course("Course 4", Status.PLAN_TO_TAKE,today.plusWeeks(3),today.plusWeeks(4),null,3));
                INSTANCE.instructorDao().insert(new Instructor("John Doe","578-354-2256","john.doe@mySchool.com"));
                INSTANCE.instructorDao().insert(new Instructor("Jane Doe","578-354-8843","jane.doe@mySchool.com"));
                INSTANCE.instructorDao().insert(new Instructor("Joe Snuffy","578-354-1775","joe.snuffy@mySchool.com"));
            });
        }
    };
}
