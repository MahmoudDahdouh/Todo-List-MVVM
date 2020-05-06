package com.mdstudio.todo.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mdstudio.title.R;
import com.mdstudio.todo.adapter.TaskAdapter;
import com.mdstudio.todo.datebase.AppDatabase;
import com.mdstudio.todo.datebase.AppRepository;
import com.mdstudio.todo.model.Task;
import com.mdstudio.todo.viewmodel.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Room database
    private AppDatabase mDb;
    private AppRepository mRepository;


    private FloatingActionButton fab_task;
    private RecyclerView rv_task;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        mRepository = new AppRepository(getApplication());
        fab_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddTaskActivity.class));
            }
        });

        //Room database
        mDb = AppDatabase.getInstance(getApplication());

        setupRecycler();

/// swipe to delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mRepository.deleteTask(taskAdapter.getNoteAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(rv_task);

        //setupViewModel
        setupViewModel();
    }

    private void setupRecycler() {
        rv_task = findViewById(R.id.rv_task);

        taskAdapter = new TaskAdapter();

        rv_task.setAdapter(taskAdapter);
        rv_task.setLayoutManager(new LinearLayoutManager(this));
        rv_task.setHasFixedSize(true);

        //on click item on Recycler View
        taskAdapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String title, int id) {
                Intent intent = new Intent(MainActivity.this, TodoActivity.class);
                intent.putExtra(TodoActivity.EXTRA_ID, id);
                intent.putExtra(TodoActivity.EXTRA_TITLE, title);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        fab_task = findViewById(R.id.fab_task);
    }


    private void setupViewModel() {

        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                Log.d("TEST_MVVM", "onChanged: Data changed !");
                taskAdapter.submitList(tasks);
            }
        });
    }
}
