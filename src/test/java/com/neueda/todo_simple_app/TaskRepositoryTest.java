package com.neueda.todo_simple_app;

import com.neueda.todo_simple_app.model.Task;
import com.neueda.todo_simple_app.repository.TaskRepository;
import com.neueda.todo_simple_app.service.TodoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class TaskRepositoryTest {

    @MockBean
    private TodoService todoService;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void shouldSaveAndFindTask() {
        // given
        Task task = new Task();
        task.setDescription("Test task");

        // when
        Task saved = taskRepository.save(task);
        Optional<Task> found = taskRepository.findById(saved.getId());

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getDescription()).isEqualTo("Test task");
    }

    @Test
    void shouldFindAllTasks() {
        // given
        Task task1 = new Task();
        task1.setDescription("Task 1");
        Task task2 = new Task();
        task2.setDescription("Task 2");

        taskRepository.save(task1);
        taskRepository.save(task2);

        // when
        List<Task> tasks = taskRepository.findAll();

        // then
        assertThat(tasks).hasSize(2);
        assertThat(tasks).extracting(Task::getDescription)
                .containsExactlyInAnyOrder("Task 1", "Task 2");
    }

    @Test
    void shouldDeleteTask() {
        // given
        Task task = new Task();
        task.setDescription("Task to delete");
        Task saved = taskRepository.save(task);

        // when
        taskRepository.deleteById(saved.getId());

        // then
        Optional<Task> found = taskRepository.findById(saved.getId());
        assertThat(found).isNotPresent();
    }
}
