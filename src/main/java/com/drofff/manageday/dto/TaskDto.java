package com.drofff.manageday.dto;

public class TaskDto {

	private Long id;

	private String text;

	private PriorityDto priority;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public PriorityDto getPriority() {
		return priority;
	}

	public void setPriority(PriorityDto priority) {
		this.priority = priority;
	}

}
