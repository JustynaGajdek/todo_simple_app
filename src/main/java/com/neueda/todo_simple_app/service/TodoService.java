package com.neueda.todo_simple_app.service;

import com.neueda.todo_simple_app.model.TaskItem;
import com.neueda.todo_simple_app.repository.TaskItemRepository;
import com.neueda.todo_simple_app.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import com.neueda.todo_simple_app.model.Task;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {


    private final TaskRepository taskRepository;
    private final TaskItemRepository taskItemRepository;


    public TodoService(TaskRepository taskRepository, TaskItemRepository taskItemRepository) {
        this.taskRepository = taskRepository;
        this.taskItemRepository = taskItemRepository;
    }

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

    @Transactional
    public void deleteTaskItemById(Long id) {
        TaskItem item = taskItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TaskItem not found: " + id));

        Task parent = item.getTask();
        if (parent != null) {
            parent.getTaskItems().remove(item);
        }
        taskItemRepository.delete(item);
    }

    public TaskItem updateTaskItem(Long itemId, TaskItem updatedItem) {
        Optional<TaskItem> optionalTaskItem = taskItemRepository.findById(itemId);
        if (optionalTaskItem.isPresent()) {
            TaskItem existingItem = optionalTaskItem.get();
            existingItem.setDescription(updatedItem.getDescription());
            return taskItemRepository.save(existingItem);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TaskItem not found with ID: " + itemId);
        }
    }
}