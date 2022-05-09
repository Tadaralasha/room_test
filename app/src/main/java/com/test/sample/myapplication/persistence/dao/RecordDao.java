package com.test.sample.myapplication.persistence.dao;

import androidx.paging.PagingSource;
import androidx.paging.rxjava3.RxPagingSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.test.sample.myapplication.persistence.entity.Record;

@Dao
public interface RecordDao {
    @Query("select * from " + Record.TABLE_NAME)
    PagingSource<Integer, Record> query();

    @Insert
    void insertRecord(Record record);

    @Delete
    int deleteRecord(Record record);
}
