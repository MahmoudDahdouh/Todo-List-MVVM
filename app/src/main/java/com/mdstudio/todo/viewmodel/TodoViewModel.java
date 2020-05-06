package com.mdstudio.todo.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mdstudio.todo.datebase.AppRepository;
import com.mdstudio.todo.model.Todo;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {

    private LiveData<List<Todo>> todos;
    private AppRepository appRepository;

    public TodoViewModel(@NonNull Application application) {
        super(application);
        Log.d("TEST_MVVM", "TodoViewModel: setup TodoViewModel");
        appRepository = new AppRepository(application);

    }

    public LiveData<List<Todo>> getTodos(int task_id) {
        todos = appRepository.getAllTodos(task_id);
        return todos;
    }

}
