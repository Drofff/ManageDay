package com.drofff.manageday.dto;

public class CreateTaskDto {

	private String text;

	private Long priorityId;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getPriorityId() {
		return priorityId;
	}

	public void setPriorityId(Long priorityId) {
		this.priorityId = priorityId;
	}
}
