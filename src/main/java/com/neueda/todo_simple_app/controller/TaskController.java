package com.neueda.todo_simple_app.controller;

import com.neueda.todo_simple_app.model.Task;
import com.neueda.todo_simple_app.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TodoService todoService;

    @Autowired
    public TaskController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return todoService.findAll();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return todoService.findById(id);
    }

    @PostMapping
    public Task addTask(@RequestBody Task newTask) {
        return todoService.save(newTask);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        return todoService.update(id, updatedTask);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        todoService.deleteById(id);
    }
}