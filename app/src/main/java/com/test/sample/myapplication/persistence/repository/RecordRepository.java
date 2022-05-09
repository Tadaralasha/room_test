package com.test.sample.myapplication.persistence.repository;

import androidx.lifecycle.LiveData;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.test.sample.myapplication.persistence.SampleDatabase;
import com.test.sample.myapplication.persistence.dao.RecordDao;
import com.test.sample.myapplication.persistence.entity.Record;

import io.reactivex.rxjava3.core.Observable;

public class RecordRepository {
    private RecordDao recordDao;
    private LiveData<PagingData<Record>> records;
    private static final int PAGE_SIZE = 20;

    public RecordRepository(SampleDatabase database) {
        recordDao = database.getRecordDao();
    }

    public Observable<PagingData<Record>> getRecords() {
        PagingConfig pagingConfig = new PagingConfig(PAGE_SIZE);
        Pager<Integer, Record> integerListPager = new Pager<>(pagingConfig, null, () -> recordDao.query());
        return PagingRx.getObservable(integerListPager);
    }

    public void saveRecord(Record record) {
        recordDao.insertRecord(record);
    }

    public void deleteRecord(Record record) {
        recordDao.deleteRecord(record);
    }
}
