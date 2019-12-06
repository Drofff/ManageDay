package com.drofff.manageday.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Priority implements Ownable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private int priorityLevel;

	@ManyToOne
	@JoinColumn(name = "color_id")
	private RGBColor color;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private User owner;

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

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
}
