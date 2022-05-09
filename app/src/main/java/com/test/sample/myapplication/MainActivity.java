package com.test.sample.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.sample.myapplication.persistence.entity.Record;
import com.test.sample.myapplication.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {
    MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        PagingDataAdapter<Record, UserViewHolder> adapter = new RecordAdapter();
        rv.setAdapter(adapter);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getRecords().observe(this, pagingData -> adapter.submitData(MainActivity.this.getLifecycle(), pagingData));

        findViewById(R.id.button1).setOnClickListener(v -> mainViewModel.saveRecord(new Record()));
        findViewById(R.id.button2).setOnClickListener(v -> mainViewModel.deleteRecord(new Record()));
    }

    private static class UserViewHolder extends RecyclerView.ViewHolder {

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Record record) {

        }
    }

    private static class UserComparator extends DiffUtil.ItemCallback<Record> {
        @Override
        public boolean areItemsTheSame(@NonNull Record oldItem,
                                       @NonNull Record newItem) {
            // Id is unique.
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Record oldItem,
                                          @NonNull Record newItem) {
            return (oldItem.getName() == null && newItem.getName() == null) || (oldItem.getName().equals(newItem.getName()));
        }
    }

    private static class RecordAdapter extends PagingDataAdapter<Record, UserViewHolder> {

        public RecordAdapter() {
            super(new UserComparator());
        }

        @NonNull
        @Override
        public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record_list, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
            Record item = getItem(position);
            holder.bind(item);
        }
    }
}