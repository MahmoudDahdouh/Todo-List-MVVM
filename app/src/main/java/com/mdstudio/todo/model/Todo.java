package com.mdstudio.todo.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Todo")
public class Todo {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private int status;
    @ColumnInfo(name = "task_id")
    private int task_id;


    public Todo(int id, String title, int status, int task_id) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.task_id = task_id;
    }

    @Ignore
    public Todo(String title, int status, int task_id) {
        this.title = title;
        this.status = status;
        this.task_id = task_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
