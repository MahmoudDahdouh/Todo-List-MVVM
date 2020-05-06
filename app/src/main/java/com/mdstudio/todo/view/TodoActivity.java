package com.mdstudio.todo.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mdstudio.title.R;
import com.mdstudio.todo.adapter.TodoAdapter;
import com.mdstudio.todo.datebase.AppRepository;
import com.mdstudio.todo.model.Todo;
import com.mdstudio.todo.viewmodel.TodoViewModel;

import java.util.List;

public class TodoActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    private int task_id;

    private TextView tv_task_id;
    private RecyclerView rv_todo;
    private EditText ed_add_todo;
    private Button btn_add_todo;

    //
    private TodoAdapter mTodoAdapter;

    //
    private AppRepository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        initViews();
        Intent intent = getIntent();

        if (intent.getExtras() != null) {
            String title = intent.getStringExtra(EXTRA_TITLE);
            task_id = intent.getIntExtra(EXTRA_ID, 0);
            tv_task_id.setText("Task : " + title + " ID : " + task_id);
        }

        mRepository = new AppRepository(getApplication());


        // setup recycler
        setupRecycler();

        // Retrieve Data
        setupViewModel();

        // Add todo
        btn_add_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTodo();
            }
        });
    }

    private void addTodo() {
        String title = ed_add_todo.getText().toString().trim();
        mRepository.insertTodo(new Todo(title, 0, task_id));
    }

    private void initViews() {
        ed_add_todo = findViewById(R.id.ed_add_todo);
        tv_task_id = findViewById(R.id.tv_task_id);
        btn_add_todo = findViewById(R.id.btn_add_todo);
    }

    private void setupViewModel() {

        TodoViewModel todoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);
        todoViewModel.getTodos(task_id).observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {
                Log.d("TEST_MVVM", "onChanged: Todo changed !");
                mTodoAdapter.submitList(todos);
            }
        });
    }


    // setup Recycler
    private void setupRecycler() {
        rv_todo = findViewById(R.id.rv_todo);

        mTodoAdapter = new TodoAdapter(mRepository);

        rv_todo.setAdapter(mTodoAdapter);
        rv_todo.setLayoutManager(new LinearLayoutManager(this));
        rv_todo.setHasFixedSize(true);

        //on click item on Recycler View
        mTodoAdapter.setOnItemClickListener(new TodoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String title, int id) {
            }
        });
    }

}
