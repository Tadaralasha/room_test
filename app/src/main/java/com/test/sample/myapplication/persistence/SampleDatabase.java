package com.test.sample.myapplication.persistence;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.test.sample.myapplication.persistence.dao.RecordDao;
import com.test.sample.myapplication.persistence.entity.Record;

@Database(version = 1, entities = {Record.class}, exportSchema = false)
public abstract class SampleDatabase extends RoomDatabase {
    private static SampleDatabase instance;
    private static final String DATABASE_NAME = "database_name";

    public abstract RecordDao getRecordDao();

    public static SampleDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (SampleDatabase.class) {
                instance = buildDatabase(context);
            }
        }

        return instance;
    }

    private static SampleDatabase buildDatabase(@NonNull Context context) {
        return Room.databaseBuilder(context, SampleDatabase.class, DATABASE_NAME)
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    }
                }).build();
    }
}
