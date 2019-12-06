package com.drofff.manageday.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drofff.manageday.entity.RGBColor;
import com.drofff.manageday.entity.User;
import com.drofff.manageday.exception.MDException;
import com.drofff.manageday.repository.RGBColorRepository;
import com.drofff.manageday.utils.UserContext;
import com.drofff.manageday.utils.ValidationUtils;

@Service
public class ColorService {

	private static final Integer MAX_COLORS_COUNT = 10;

	private final RGBColorRepository rgbColorRepository;

	@Autowired
	public ColorService(RGBColorRepository rgbColorRepository) {
		this.rgbColorRepository = rgbColorRepository;
	}

	public List<RGBColor> getMyAllColors() {
		User currentUser = UserContext.getCurrentUser();
		return rgbColorRepository.findByOwner(currentUser);
	}

	public void createColor(int r, int g, int b) {
		User currentUser = UserContext.getCurrentUser();
		validateColorsCountForUser(currentUser);
		RGBColor rgbColor = RGBColor.of(r, g, b);
		rgbColor.setDefault(false);
		rgbColor.setOwner(currentUser);
		rgbColorRepository.save(rgbColor);
	}

	private void validateColorsCountForUser(User user) {
		List<RGBColor> colors = rgbColorRepository.findByOwner(user);
		if(colors.size() >= MAX_COLORS_COUNT) {
			throw new MDException("Colors list is full. Maximum colors count is " + MAX_COLORS_COUNT);
		}
	}

	public void deleteColorById(Long colorId) {
		User currentUser = UserContext.getCurrentUser();
		RGBColor rgbColor = rgbColorRepository.findById(colorId)
				.orElseThrow(() -> new MDException("Color with such id do not exists"));
		ValidationUtils.validateUserIsOwnerOf(currentUser, rgbColor);
		validateColorIsNotDefault(rgbColor);
		rgbColorRepository.delete(rgbColor);
	}

	private void validateColorIsNotDefault(RGBColor color) {
		if(color.isDefault()) {
			throw new MDException("Default color can not be deleted");
		}
	}

}
