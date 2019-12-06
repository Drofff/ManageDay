package com.drofff.manageday.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Task implements Ownable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String text;

	@ManyToOne
	@JoinColumn(name = "priority_id")
	private Priority priority;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private User owner;

	public Task() {}

	public Task(String text, User owner, Priority priority) {
		this.text = text;
		this.owner = owner;
		this.priority = priority;
	}

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

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

}
