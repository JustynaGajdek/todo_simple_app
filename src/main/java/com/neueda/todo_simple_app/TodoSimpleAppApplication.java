package com.neueda.todo_simple_app;

import com.neueda.todo_simple_app.model.Task;
import com.neueda.todo_simple_app.model.TaskItem;
import com.neueda.todo_simple_app.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class TodoSimpleAppApplication {

	private TodoService todoService;

	public TodoSimpleAppApplication(TodoService todoService) {
		this.todoService = todoService;
	}

	public static void main(String[] args) {
		SpringApplication.run(TodoSimpleAppApplication.class, args);
	}

	@Bean
	@Profile("!test")
	public CommandLineRunner loadData() {
		return (args) -> {

			if (todoService.findAllTasks().isEmpty()) {
				Task shoppingTask = new Task("Go shopping");
				Task homeworkTask = new Task("Do homework");

				shoppingTask = todoService.saveTask(shoppingTask);
				homeworkTask = todoService.saveTask(homeworkTask);

				todoService.saveTaskItem(shoppingTask.getId(), new TaskItem("Milk"));
				todoService.saveTaskItem(shoppingTask.getId(), new TaskItem("Bread"));
				todoService.saveTaskItem(homeworkTask.getId(), new TaskItem("Write a report"));
				todoService.saveTaskItem(homeworkTask.getId(), new TaskItem("Read a book"));
			}
		};
	}
}
