package com.neueda.todo_simple_app.model;

import jakarta.persistence.*;

@Entity
public class TaskItem {

    @Id
    @GeneratedValue
    private Long id;
    private String description;
    private boolean completed = false;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;


    public TaskItem(Long id, String description, boolean completed, Task task) {
        this.id = id;
        this.description = description;
        this.completed = completed;
        this.task = task;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
