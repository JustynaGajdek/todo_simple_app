package com.neueda.todo_simple_app.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Task {
    @Id
    @GeneratedValue
    private Long id;
    private String description;

    public Task() {
    }

    public Task(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Task(String description) {
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

    @OneToMany(mappedBy =  "task", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<TaskItem> taskItems = new ArrayList<>();

    public  List<TaskItem> getTaskItems(){
        return taskItems;
    }

    public void setTaskItems(List<TaskItem> taskItems) {
        this.taskItems = taskItems;
    }
}
