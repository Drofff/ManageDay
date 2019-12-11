package com.drofff.manageday.dto;

import javax.persistence.Id;

public class PriorityDto {

	@Id
	private Long id;

	private int priorityLevel;

	private String htmlColor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPriorityLevel() {
		return priorityLevel;
	}

	public void setPriorityLevel(int priorityLevel) {
		this.priorityLevel = priorityLevel;
	}

	public String getHtmlColor() {
		return htmlColor;
	}

	public void setHtmlColor(String htmlColor) {
		this.htmlColor = htmlColor;
	}
}
