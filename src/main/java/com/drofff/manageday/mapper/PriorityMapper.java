package com.drofff.manageday.mapper;

import java.util.Objects;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.drofff.manageday.dto.PriorityDto;
import com.drofff.manageday.entity.Priority;
import com.drofff.manageday.entity.RGBColor;
import com.drofff.manageday.utils.FormattingUtils;

@Component
public class PriorityMapper implements Mapper<Priority, PriorityDto> {

	private final ModelMapper modelMapper;
	private final TypeMap<Priority, PriorityDto> toDtoTypeMap;

	@Autowired
	public PriorityMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
		toDtoTypeMap = modelMapper.createTypeMap(Priority.class, PriorityDto.class)
				.addMappings(mapping -> mapping.when(context -> Objects.nonNull(context.getSource()))
				.using(rgbToHtmlColorStrConverter()).map(Priority::getColor, PriorityDto::setHtmlColor));
	}

	private Converter<RGBColor, String> rgbToHtmlColorStrConverter() {
		return context -> FormattingUtils.convertRgbColorToHtmlColorStr(context.getSource());
	}

	@Override
	public Priority toEntity(PriorityDto dto) {
		return modelMapper.map(dto, Priority.class);
	}

	@Override
	public PriorityDto toDto(Priority entity) {
		return toDtoTypeMap.map(entity);
	}

}
