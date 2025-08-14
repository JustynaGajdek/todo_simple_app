package com.neueda.todo_simple_app.controller;

import com.neueda.todo_simple_app.model.Task;
import com.neueda.todo_simple_app.model.TaskItem;
import com.neueda.todo_simple_app.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        return todoService.findAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return todoService.findTaskById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task addTask(@RequestBody Task newTask) {
        return todoService.saveTask(newTask);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        return todoService.updateTask(id, updatedTask);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        todoService.deleteTaskById(id);
    }

    @PostMapping("/{taskId}/item")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskItem saveTaskItem(@PathVariable Long taskId, @RequestBody TaskItem newItem) {
        return todoService.saveTaskItem(taskId, newItem);
    }
}