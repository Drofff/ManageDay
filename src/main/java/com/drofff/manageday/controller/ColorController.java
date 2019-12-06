package com.drofff.manageday.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drofff.manageday.dto.CreateColorDto;
import com.drofff.manageday.entity.RGBColor;
import com.drofff.manageday.service.ColorService;

@RestController
@RequestMapping("/colors")
public class ColorController {

	private final ColorService colorService;

	@Autowired
	public ColorController(ColorService colorService) {
		this.colorService = colorService;
	}

	@GetMapping
	public ResponseEntity<List<RGBColor>> getMyAllColors() {
		List<RGBColor> colors = colorService.getMyAllColors();
		return ResponseEntity.ok(colors);
	}

	@PostMapping
	public ResponseEntity<String> createColor(@RequestBody CreateColorDto colorDto) {
		colorService.createColor(colorDto.getR(), colorDto.getG(), colorDto.getB());
		return ResponseEntity.ok("Color has been successfully added");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteColorById(@PathVariable Long id) {
		colorService.deleteColorById(id);
		return ResponseEntity.ok("Colors has been successfully deleted");
	}

}
