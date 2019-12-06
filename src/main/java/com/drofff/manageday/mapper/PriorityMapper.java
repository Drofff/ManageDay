package com.drofff.manageday.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.drofff.manageday.dto.PriorityDto;
import com.drofff.manageday.entity.Priority;

@Component
public class PriorityMapper implements Mapper<Priority, PriorityDto> {

	private final ModelMapper modelMapper;

	@Autowired
	public PriorityMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public Priority toEntity(PriorityDto dto) {
		return modelMapper.map(dto, Priority.class);
	}

	@Override
	public PriorityDto toDto(Priority entity) {
		return modelMapper.map(entity, PriorityDto.class);
	}

}
