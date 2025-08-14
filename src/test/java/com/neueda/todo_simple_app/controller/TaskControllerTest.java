package com.neueda.todo_simple_app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neueda.todo_simple_app.model.Task;
import com.neueda.todo_simple_app.model.TaskItem;
import com.neueda.todo_simple_app.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        task1 = new Task(1L, "Go shopping");
        task2 = new Task(2L, "Go to sleep :)");
    }

    @Test
    void getAllTasks_ShouldReturnListOfTasks() throws Exception {
        List<Task> tasks = Arrays.asList(task1, task2);
        Mockito.when(todoService.findAllTasks()).thenReturn(tasks);

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].description").value("Go shopping"))
                .andExpect(jsonPath("$[1].description").value("Go to sleep :)"));
    }

    @Test
    void getTaskById_ShouldReturnTask() throws Exception {
        Mockito.when(todoService.findTaskById(1L)).thenReturn(task1);

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Go shopping"));
    }

    @Test
    void addTask_ShouldReturnCreatedTask() throws Exception {
        Mockito.when(todoService.saveTask(any(Task.class))).thenReturn(task1);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("Go shopping"));
    }

    @Test
    void updateTask_ShouldReturnUpdatedTask() throws Exception {
        Task updated = new Task(1L, "Updated task");
        Mockito.when(todoService.updateTask(eq(1L), any(Task.class))).thenReturn(updated);

        mockMvc.perform(put("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Updated task"));
    }

    @Test
    void deleteTask_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isOk());
        Mockito.verify(todoService).deleteTaskById(1L);
    }

    @Test
    void saveTaskItem_ShouldReturnCreatedItem() throws Exception {
        TaskItem newItem = new TaskItem(1L, "Subtask", null);
        Mockito.when(todoService.saveTaskItem(eq(1L), any(TaskItem.class))).thenReturn(newItem);

        mockMvc.perform(post("/api/tasks/1/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newItem)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("Subtask"));
    }
}