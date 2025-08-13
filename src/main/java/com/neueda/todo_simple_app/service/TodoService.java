package com.neueda.todo_simple_app.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import com.neueda.todo_simple_app.model.Task;
import java.util.List;

@Service
public class TodoService {

    private final List<Task> tasks = new ArrayList<>();
    private long nextId = 1;

    public TodoService() {

        tasks.add(new Task(nextId++, "Go shopping"));
        tasks.add(new Task(nextId++, "Go to sleep :)"));
    }

    public List<Task> findAll() {
        return tasks;
    }

    public Task findById(Long id) {
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        return null;
    }

    public Task save(Task newTask) {

        newTask.setId(nextId++);
        tasks.add(newTask);
        return newTask;
    }

    public Task update(Long id, Task updatedTask) {
        Task existingTask = findById(id);
        if (existingTask != null) {
            existingTask.setDescription(updatedTask.getDescription());
            return existingTask;
        }
        return null;
    }

    public void deleteById(Long id) {
        tasks.removeIf(task -> task.getId().equals(id));
    }
}