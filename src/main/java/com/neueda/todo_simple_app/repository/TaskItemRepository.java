package com.neueda.todo_simple_app.repository;

import com.neueda.todo_simple_app.model.TaskItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskItemRepository extends JpaRepository<TaskItem, Long> {
}
