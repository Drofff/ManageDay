package com.drofff.manageday.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drofff.manageday.entity.Priority;
import com.drofff.manageday.entity.RGBColor;
import com.drofff.manageday.entity.User;
import com.drofff.manageday.exception.MDException;
import com.drofff.manageday.repository.PriorityRepository;
import com.drofff.manageday.repository.RGBColorRepository;
import com.drofff.manageday.utils.UserContext;
import com.drofff.manageday.utils.ValidationUtils;

@Service
public class PriorityService {

	private static final int MAX_PRIORITIES_COUNT = 30;

	private final PriorityRepository priorityRepository;
	private final RGBColorRepository rgbColorRepository;

	@Autowired
	public PriorityService(PriorityRepository priorityRepository, RGBColorRepository rgbColorRepository) {
		this.priorityRepository = priorityRepository;
		this.rgbColorRepository = rgbColorRepository;
	}

	public void createPriority(int priorityLevel) {
		User currentUser = UserContext.getCurrentUser();
		validatePrioritiesCountForOwner(currentUser);
		validatePriorityLevelIsAvailableForOwner(priorityLevel, currentUser);
		Priority priority = buildPriorityObject(priorityLevel, currentUser);
		priorityRepository.save(priority);
	}

	private void validatePrioritiesCountForOwner(User owner) {
		List<Priority> priorities = priorityRepository.findByOwner(owner);
		if(priorities.size() >= MAX_PRIORITIES_COUNT) {
			throw new MDException("Priorities list is full. Maximum priorities count is " + MAX_PRIORITIES_COUNT);
		}
	}

	private void validatePriorityLevelIsAvailableForOwner(int priorityLevel, User owner) {
		Optional<Priority> priorityOptional = priorityRepository.findByPriorityLevelAndOwner(priorityLevel, owner);
		if(priorityOptional.isPresent()) {
			throw new MDException("Priority with such level already exists");
		}
	}

	private Priority buildPriorityObject(int priorityLevel, User owner) {
		Priority priority = new Priority();
		priority.setPriorityLevel(priorityLevel);
		priority.setOwner(owner);
		priority.setColor(getDefaultRGBColor());
		return priority;
	}

	private RGBColor getDefaultRGBColor() {
		return rgbColorRepository.findFirstByIsDefault(Boolean.TRUE);
	}

	public void setColorToPriority(Long colorId, Long priorityId) {
		User currentUser = UserContext.getCurrentUser();
		Priority priority = priorityRepository.findById(priorityId)
				.orElseThrow(() -> new MDException("Priority with such id do not exists"));
		ValidationUtils.validateUserIsOwnerOf(currentUser, priority);
		RGBColor rgbColor = rgbColorRepository.findById(colorId)
				.orElseThrow(() -> new MDException("Color with such id do not exists"));
		ValidationUtils.validateUserIsOwnerOf(currentUser, rgbColor);
		priority.setColor(rgbColor);
		priorityRepository.save(priority);
	}

	public List<Priority> getMyAllPriorities() {
		User currentUser = UserContext.getCurrentUser();
		return priorityRepository.findByOwner(currentUser);
	}

	public void deletePriorityById(Long id) {
		User currentUser = UserContext.getCurrentUser();
		Priority priority = priorityRepository.findById(id)
				.orElseThrow(() -> new MDException("Priority with such id do not exists"));
		ValidationUtils.validateUserIsOwnerOf(currentUser, priority);
		priorityRepository.delete(priority);
	}

}
