package com.drofff.manageday.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drofff.manageday.dto.PriorityDto;
import com.drofff.manageday.entity.Priority;
import com.drofff.manageday.mapper.PriorityMapper;
import com.drofff.manageday.service.PriorityService;

@RestController
@RequestMapping("/priorities")
public class PriorityController {

	private final PriorityService priorityService;
	private final PriorityMapper priorityMapper;

	@Autowired
	public PriorityController(PriorityService priorityService, PriorityMapper priorityMapper) {
		this.priorityService = priorityService;
		this.priorityMapper = priorityMapper;
	}

	@GetMapping
	public ResponseEntity<List<PriorityDto>> getMyAllPriorities() {
		List<Priority> priorities = priorityService.getMyAllPriorities();
		List<PriorityDto> priorityDtos = priorities.stream()
				.map(priorityMapper::toDto)
				.collect(Collectors.toList());
		return ResponseEntity.ok(priorityDtos);
	}

	@PostMapping("/")
	public ResponseEntity<String> createPriority(Integer priorityLevel) {
		priorityService.createPriority(priorityLevel);
		return ResponseEntity.ok("Priority has been successfully created");
	}

	@PutMapping("/{id}/color")
	public ResponseEntity<String> changeColorToPriority(@PathVariable Long id, Long colorId) {
		priorityService.setColorToPriority(colorId, id);
		return ResponseEntity.ok("Priority's color has been successfully changed");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePriorityById(@PathVariable Long id) {
		priorityService.deletePriorityById(id);
		return ResponseEntity.ok("Priority has been successfully deleted");
	}

}
