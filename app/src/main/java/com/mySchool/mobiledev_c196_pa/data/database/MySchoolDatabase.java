package com.mySchool.mobiledev_c196_pa.data.database;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.mySchool.mobiledev_c196_pa.data.dao.TermDao;
import com.mySchool.mobiledev_c196_pa.data.entities.Term;
import com.mySchool.mobiledev_c196_pa.data.repository.MySchoolExecutorService;

import java.time.ZonedDateTime;

@Database(entities = Term.class, version = 1)
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

    private static RoomDatabase.Callback mySchoolCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            MySchoolExecutorService.getService().execute(() -> {
                ZonedDateTime today = ZonedDateTime.now();
                INSTANCE.termDao().insert(new Term("Term 0", today, today.plusMonths(1)));
                INSTANCE.termDao().insert(new Term("Term 1",today.plusMonths(1),today.plusMonths(2)));
                INSTANCE.termDao().insert(new Term("Term 2",today.plusMonths(2),today.plusMonths(3)));
            });
        }
    };
}
