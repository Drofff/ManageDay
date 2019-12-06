package com.drofff.manageday.mapper;

import java.util.Objects;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.drofff.manageday.dto.PriorityDto;
import com.drofff.manageday.dto.TaskDto;
import com.drofff.manageday.entity.Priority;
import com.drofff.manageday.entity.Task;

@Component
public class TaskMapper implements Mapper<Task, TaskDto> {

	private final ModelMapper modelMapper;
	private final TypeMap<Task, TaskDto> toDtoTypeMap;
	private final PriorityMapper priorityMapper;

	@Autowired
	public TaskMapper(ModelMapper modelMapper, PriorityMapper priorityMapper) {
		this.priorityMapper = priorityMapper;
		this.modelMapper = modelMapper;
		toDtoTypeMap = modelMapper.createTypeMap(Task.class, TaskDto.class)
					.addMappings(mapping -> mapping.when(context -> Objects.nonNull(context.getSource()))
							.using(getPriorityToDtoConverter())
							.map(Task::getPriority, TaskDto::setPriority));
	}

	private Converter<Priority, PriorityDto> getPriorityToDtoConverter() {
		return context -> priorityMapper.toDto(context.getSource());
	}

	@Override
	public Task toEntity(TaskDto dto) {
		return modelMapper.map(dto, Task.class);
	}

	@Override
	public TaskDto toDto(Task entity) {
		return toDtoTypeMap.map(entity);
	}

}
