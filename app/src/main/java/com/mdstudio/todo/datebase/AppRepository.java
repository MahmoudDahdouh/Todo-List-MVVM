package com.mdstudio.todo.datebase;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.mdstudio.todo.model.Task;
import com.mdstudio.todo.model.Todo;

import java.util.List;

public class AppRepository {

    private TodoDao todoDao;
    private TaskDao taskDao;

    private LiveData<List<Todo>> getAllTodos;
    private LiveData<List<Task>> getAllTasks;


    public AppRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        todoDao = appDatabase.getTodoDao();
        taskDao = appDatabase.getTaskDao();

    }


    // operations for Task
    // insert
    public void insertTask(Task task) {
        new InsertTask(taskDao).execute(task);
    }

    // update
    public void updateTask(Task task) {
        new UpdateTask(taskDao).execute(task);
    }

    // delete
    public void deleteTask(Task task) {
        new DeleteTask(taskDao).execute(task);
    }

    // getAllTasks
    public LiveData<List<Task>> getAllTasks() {
        getAllTasks = taskDao.getAllTasks();
        return getAllTasks;
    }

    //-----------------------------------------------------------------------//

    // operations for Todo
    // insert
    public void insertTodo(Todo todo) {
        new InsertTodo(todoDao).execute(todo);
    }

    // update
    public void updateTodo(Todo todo) {
        new UpdateTodo(todoDao).execute(todo);
    }

    // delete
    public void deleteTodo(Todo todo) {
        new DeleteTodo(todoDao).execute(todo);
    }

    // getAllTodos
    public LiveData<List<Todo>> getAllTodos(int task_id) {
        getAllTodos = todoDao.getAllTodos(task_id);
        return getAllTodos;
    }

//----------------------------------------------------------------------------------//

    /// Async's for Task
    // InsertTask
    private static class InsertTask extends AsyncTask<Task, Void, Void> {

        private TaskDao taskDao;

        public InsertTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... task) {
            taskDao.insert(task[0]);
            return null;
        }
    }

    // UpdateTask
    private static class UpdateTask extends AsyncTask<Task, Void, Void> {

        private TaskDao taskDao;

        public UpdateTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... task) {
            taskDao.update(task[0]);
            return null;
        }
    }

    // DeleteTask
    private static class DeleteTask extends AsyncTask<Task, Void, Void> {

        private TaskDao taskDao;

        public DeleteTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... task) {
            taskDao.delete(task[0]);
            return null;
        }
    }


    //-------------------------------------------------------------------------------------//
    /// Async's for Todo
    // InsertTodo
    private static class InsertTodo extends AsyncTask<Todo, Void, Void> {

        private TodoDao todoDao;

        public InsertTodo(TodoDao todoDao) {
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Todo... task) {
            todoDao.insert(task[0]);
            return null;
        }
    }

    // UpdateTodo
    private static class UpdateTodo extends AsyncTask<Todo, Void, Void> {

        private TodoDao todoDao;

        public UpdateTodo(TodoDao todoDao) {
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Todo... task) {
            todoDao.update(task[0]);
            return null;
        }
    }

    // DeleteTodo
    private static class DeleteTodo extends AsyncTask<Todo, Void, Void> {

        private TodoDao todoDao;

        public DeleteTodo(TodoDao todoDao) {
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Todo... task) {
            todoDao.update(task[0]);
            return null;
        }
    }

}
