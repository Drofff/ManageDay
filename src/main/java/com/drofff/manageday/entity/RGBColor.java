package com.drofff.manageday.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "rgb_color")
public class RGBColor implements Ownable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Max(255)
	@Min(0)
	private int r;

	@Max(255)
	@Min(0)
	private int g;

	@Max(255)
	@Min(0)
	private int b;

	private boolean isDefault;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private User owner;

	public static RGBColor of(int r, int g, int b) {
		RGBColor rgbColor = new RGBColor();
		rgbColor.r = r;
		rgbColor.g = g;
		rgbColor.b = b;
		return rgbColor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
}