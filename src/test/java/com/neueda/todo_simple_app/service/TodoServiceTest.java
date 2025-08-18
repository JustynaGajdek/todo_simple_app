package com.neueda.todo_simple_app.service;

import com.neueda.todo_simple_app.model.Task;
import com.neueda.todo_simple_app.model.TaskItem;
import com.neueda.todo_simple_app.repository.TaskItemRepository;
import com.neueda.todo_simple_app.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TodoServiceTest {

    private TaskRepository taskRepository;
    private TaskItemRepository taskItemRepository;
    private TodoService todoService;

    private Task task1;

    @BeforeEach
    void setUp() {
        taskRepository = Mockito.mock(TaskRepository.class);
        taskItemRepository = Mockito.mock(TaskItemRepository.class);
        todoService = new TodoService(taskRepository, taskItemRepository);

        task1 = new Task(1L, "Go shopping");
    }

    @Test
    void findAllTasks_ShouldReturnList() {
        List<Task> tasks = Arrays.asList(task1);
        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = todoService.findAllTasks();
        assertEquals(1, result.size());
        assertEquals("Go shopping", result.get(0).getDescription());
    }

    @Test
    void findTaskById_ShouldReturnTask_WhenExists() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));

        Task result = todoService.findTaskById(1L);
        assertEquals("Go shopping", result.getDescription());
    }

    @Test
    void findTaskById_ShouldThrow_WhenNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> todoService.findTaskById(1L));
    }

    @Test
    void saveTask_ShouldReturnSavedTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(task1);

        Task saved = todoService.saveTask(task1);
        assertEquals("Go shopping", saved.getDescription());
    }

    @Test
    void updateTask_ShouldUpdateDescription() {
        Task updated = new Task(1L, "Updated");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));
        when(taskRepository.save(any(Task.class))).thenReturn(updated);

        Task result = todoService.updateTask(1L, updated);
        assertEquals("Updated", result.getDescription());
    }

    @Test
    void deleteTaskById_ShouldCallRepositoryDelete() {
        todoService.deleteTaskById(1L);
        verify(taskRepository).deleteById(1L);
    }

    @Test
    void saveTaskItem_ShouldSetTaskAndSave() {
        TaskItem newItem = new TaskItem(null, "Subtask", true, task1);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));
        when(taskItemRepository.save(any(TaskItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskItem result = todoService.saveTaskItem(1L, newItem);

        assertEquals(task1, result.getTask());
        assertEquals("Subtask", result.getDescription());
    }

    @Test
    void deleteTaskItemById_ShouldCallRepositoryDelete() {
        Task parent = new Task(10L, "parent");
        TaskItem item = new TaskItem(1L, "sub", false, parent);
        parent.getTaskItems().add(item);

        when(taskItemRepository.findById(1L)).thenReturn(Optional.of(item));

        todoService.deleteTaskItemById(1L);

        verify(taskItemRepository).delete(item);
        assertThat(parent.getTaskItems()).doesNotContain(item);
    }
}