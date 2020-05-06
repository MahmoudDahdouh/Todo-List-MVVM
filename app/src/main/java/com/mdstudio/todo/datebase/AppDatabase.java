package com.mdstudio.todo.datebase;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.mdstudio.todo.model.Task;
import com.mdstudio.todo.model.Todo;


@Database(entities = {Task.class, Todo.class},
        version = 1,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "Todo App";
    public static AppDatabase instance;
    public static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new InsertDummyData(instance).execute();
        }
    };

    public static synchronized AppDatabase getInstance(Application application) {
        if (instance == null) {
            instance = Room.databaseBuilder(application.getApplicationContext(),
                    AppDatabase.class,
                    DATABASE_NAME)
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    // Dao for operations
    public abstract TodoDao getTodoDao();

    public abstract TaskDao getTaskDao();

    private static class InsertDummyData extends AsyncTask<Void, Void, Void> {

        private TaskDao taskDao;

        public InsertDummyData(AppDatabase database) {
            taskDao = database.getTaskDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            taskDao.insert(new Task("Task 1"));
            taskDao.insert(new Task("Task 2"));
            taskDao.insert(new Task("Task 3"));
            return null;
        }
    }

}
