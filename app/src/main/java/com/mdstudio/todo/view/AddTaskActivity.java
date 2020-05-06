package com.mdstudio.todo.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.mdstudio.title.R;
import com.mdstudio.todo.datebase.AppRepository;
import com.mdstudio.todo.model.Task;

public class AddTaskActivity extends AppCompatActivity {

    private EditText ed_add_task;
    private Button btn_add_task;

    // Room database
    private AppRepository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // initViews
        initViews();

        mRepository = new AppRepository(getApplication());

        btn_add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task_name = ed_add_task.getText().toString().trim();
                mRepository.insertTask(new Task(task_name));
                finish();
            }
        });


    }

    private void initViews() {
        ed_add_task = findViewById(R.id.ed_task);
        btn_add_task = findViewById(R.id.btn_add_task);
    }
}
