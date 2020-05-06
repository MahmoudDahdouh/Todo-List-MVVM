package com.mdstudio.todo.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Task")
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    public Task(int id, String title) {
        this.id = id;
        this.title = title;
    }

    @Ignore
    public Task(String title) {
        this.title = title;
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
}
