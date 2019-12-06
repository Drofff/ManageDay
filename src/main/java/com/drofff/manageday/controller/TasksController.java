package com.drofff.manageday.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drofff.manageday.dto.CreateTaskDto;
import com.drofff.manageday.dto.TaskDto;
import com.drofff.manageday.entity.Task;
import com.drofff.manageday.mapper.TaskMapper;
import com.drofff.manageday.service.TasksService;

@RestController
@RequestMapping("/tasks")
public class TasksController {

	private final TasksService tasksService;
	private final TaskMapper taskMapper;

	@Autowired
	public TasksController(TasksService tasksService, TaskMapper taskMapper) {
		this.tasksService = tasksService;
		this.taskMapper = taskMapper;
	}

	@GetMapping
	public ResponseEntity<List<TaskDto>> getMyTasks() {
		List<Task> tasks = tasksService.getAllTasksForCurrentUser();
		List<TaskDto> taskDtos = tasks.stream()
				.map(taskMapper::toDto)
				.collect(Collectors.toList());
		return ResponseEntity.ok(taskDtos);
	}

	@PostMapping
	public ResponseEntity<String> createTask(@RequestBody CreateTaskDto createTaskDto) {
		tasksService.createTask(createTaskDto.getText(), createTaskDto.getPriorityId());
		return ResponseEntity.ok("Task has been successfully created");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTask(@PathVariable Long id) {
		tasksService.deleteTaskById(id);
		return ResponseEntity.ok("Task has been successfully deleted");
	}

}
