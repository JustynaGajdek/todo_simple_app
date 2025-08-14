package com.neueda.todo_simple_app.service;

import com.neueda.todo_simple_app.model.TaskItem;
import com.neueda.todo_simple_app.repository.TaskItemRepository;
import com.neueda.todo_simple_app.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import com.neueda.todo_simple_app.model.Task;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TodoService {


    private final TaskRepository taskRepository;
    private final TaskItemRepository taskItemRepository;


    public TodoService(TaskRepository taskRepository, TaskItemRepository taskItemRepository) {
        this.taskRepository = taskRepository;
        this.taskItemRepository = taskItemRepository;
    }

//    private final List<Task> tasks = new ArrayList<>();
//    private long nextId = 1;
//
//    public TodoService() {
//
//        tasks.add(new Task(nextId++, "Go shopping"));
//        tasks.add(new Task(nextId++, "Go to sleep :)"));
//    }

    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    public Task findTaskById(Long id) {

        return taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found with id: " + id));
    }

    public Task saveTask(Task task) {

        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updatedTask) {
        Task existingTask = findTaskById(id);
        if (existingTask != null) {
            existingTask.setDescription(updatedTask.getDescription());
            return taskRepository.save(existingTask);
        }
        return null;
    }

    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    public TaskItem saveTaskItem (Long taskId, TaskItem taskItem){
        Task task = findTaskById(taskId);
        taskItem.setTask(task);
        return taskItemRepository.save(taskItem);
    }

    public void deleteTaskItemById(Long id){
        taskItemRepository.deleteById(id);
    }
}