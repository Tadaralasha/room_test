package com.test.sample.myapplication.persistence.entity;

import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Record.TABLE_NAME)
public class Record {
    public static final String TABLE_NAME = "record";
    public static final String COLUMN_NAME_NAME = "name";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BaseColumns._ID)
    long id;

    @ColumnInfo(name = COLUMN_NAME_NAME)
    String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
