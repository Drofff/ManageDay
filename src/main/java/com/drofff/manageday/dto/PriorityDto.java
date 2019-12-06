package com.drofff.manageday.dto;

import javax.persistence.Id;

import com.drofff.manageday.entity.RGBColor;

public class PriorityDto {

	@Id
	private Long id;

	private int priorityLevel;

	private RGBColor color;

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

	public RGBColor getColor() {
		return color;
	}

	public void setColor(RGBColor color) {
		this.color = color;
	}

}
