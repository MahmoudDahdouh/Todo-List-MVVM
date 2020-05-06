package com.mdstudio.todo.datebase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mdstudio.todo.model.Todo;

import java.util.List;

@Dao
public interface TodoDao {

    @Insert
    void insert(Todo todo);

    @Update
    void update(Todo todo);

    @Delete
    void delete(Todo todo);

    @Query("SELECT * FROM Todo WHERE task_id = :task_id")
//
    LiveData<List<Todo>> getAllTodos(int task_id);

    @Query("SELECT * FROM Todo WHERE id = :id")
    Todo loadTodoById(int id);

}
