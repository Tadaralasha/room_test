package com.test.sample.myapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagingData;

import com.test.sample.myapplication.persistence.SampleDatabase;
import com.test.sample.myapplication.persistence.entity.Record;
import com.test.sample.myapplication.persistence.repository.RecordRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {
    private RecordRepository recordRepository;
    private LiveData<PagingData<Record>> records;

    public MainViewModel(@NonNull Application application) {
        super(application);
        recordRepository = new RecordRepository(SampleDatabase
                .getInstance(application.getApplicationContext()));
    }

    private static final class MyLiveData extends LiveData<PagingData<Record>> {

        public final void refreshPagingData(PagingData<Record> pagingData) {
            this.setValue(pagingData);
        }

    }

    public void saveRecord(Record record) {
        new Thread(() -> recordRepository.saveRecord(record)).start();
    }

    public void deleteRecord(Record record) {
        new Thread(() -> recordRepository.deleteRecord(record)).start();
    }

    public LiveData<PagingData<Record>> getRecords() {
        if (records == null) {
            final MyLiveData liveData = new MyLiveData();
            recordRepository.getRecords().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<PagingData<Record>>() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@io.reactivex.rxjava3.annotations.NonNull PagingData<Record> recordPagingData) {
                            liveData.refreshPagingData(recordPagingData);
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

            records = liveData;
        }
        return records;
    }

    protected void onCleared() {
    }
}
