package com.exo1.exo1.mapper;

import com.exo1.exo1.dto.TaskDTO;
import com.exo1.exo1.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(target = "projectId", source = "project.id") // Mappe l'id du projet
    @Mapping(target = "userId", source = "user.id") // Mappe l'id de l'utilisateur assigné
    TaskDTO toDto(Task task);

    @Mapping(target = "project", ignore = true) // Ignore pour éviter les cycles
    @Mapping(target = "user", ignore = true) // Ignore pour éviter les cycles
    Task toEntity(TaskDTO taskDTO);
}
