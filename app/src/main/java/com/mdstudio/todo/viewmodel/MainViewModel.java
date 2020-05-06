package com.mdstudio.todo.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mdstudio.todo.datebase.AppRepository;
import com.mdstudio.todo.model.Task;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Task>> tasks;

    public MainViewModel(@NonNull Application application) {
        super(application);

        AppRepository appRepository = new AppRepository(application);
        Log.d("TEST_MVVM", "TodoViewModel: setup MainViewModel");
        tasks = appRepository.getAllTasks();
    }

    public LiveData<List<Task>> getTasks() {
        return tasks;
    }
}
