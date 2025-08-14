package com.neueda.todo_simple_app.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

public class Task {

    private Long id;
    private String description;

    public Task() {
    }

    public Task(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(mappedBy =  "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskItem> taskItems = new ArrayList<>();

    public  List<TaskItem> getTaskItems(){
        return taskItems;
    }

    public void setTaskItems(List<TaskItem> taskItems) {
        this.taskItems = taskItems;
    }
}
