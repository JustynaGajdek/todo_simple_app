package com.neueda.todo_simple_app.repository;

import com.neueda.todo_simple_app.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
