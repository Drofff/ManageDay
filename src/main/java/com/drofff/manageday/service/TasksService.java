package com.drofff.manageday.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drofff.manageday.entity.Priority;
import com.drofff.manageday.entity.Task;
import com.drofff.manageday.entity.User;
import com.drofff.manageday.exception.MDException;
import com.drofff.manageday.repository.PriorityRepository;
import com.drofff.manageday.repository.TaskRepository;
import com.drofff.manageday.utils.UserContext;
import com.drofff.manageday.utils.ValidationUtils;

@Service
public class TasksService {

	private final PriorityRepository priorityRepository;
	private final TaskRepository taskRepository;

	@Autowired
	public TasksService(PriorityRepository priorityRepository, TaskRepository taskRepository) {
		this.priorityRepository = priorityRepository;
		this.taskRepository = taskRepository;
	}

	public void createTask(String text, Long priorityId) {
		User currentUser = UserContext.getCurrentUser();
		Priority selectedPriority = priorityRepository.findByIdAndOwner(priorityId, currentUser)
				.orElseThrow(() -> new MDException("Invalid priority id"));
		Task task = new Task(text, currentUser, selectedPriority);
		ValidationUtils.validate(task);
		taskRepository.save(task);
	}

	public List<Task> getAllTasksForCurrentUser() {
		User currentUser = UserContext.getCurrentUser();
		return taskRepository.findByOwner(currentUser);
	}

	public void deleteTaskById(Long taskId) {
		Task task = taskRepository.findById(taskId)
				.orElseThrow(() -> new MDException("Task with such id do not exists"));
		validateCurrentUserIsOwnerOfTask(task);
		taskRepository.delete(task);
	}

	private void validateCurrentUserIsOwnerOfTask(Task task) {
		User currentUser = UserContext.getCurrentUser();
		ValidationUtils.validateUserIsOwnerOf(currentUser, task);
	}

}