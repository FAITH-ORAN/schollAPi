package com.exo1.exo1.mapper;

import com.exo1.exo1.dto.ProjectDTO;
import com.exo1.exo1.dto.TaskDTO;
import com.exo1.exo1.dto.UserDTO;
import com.exo1.exo1.model.Project;
import com.exo1.exo1.model.Task;
import com.exo1.exo1.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "project", expression = "java(mapProjectToProjectDTO(task.getProject()))")
    @Mapping(target = "user", expression = "java(mapUserToUserDTO(task.getUser()))")
    TaskDTO toDto(Task task);

    @Mapping(target = "project", ignore = true)
    @Mapping(target = "user", ignore = true)
    Task toEntity(TaskDTO taskDTO);

    // Conversion de Project en ProjectDTO
    default ProjectDTO mapProjectToProjectDTO(Project project) {
        if (project == null) return null;
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        projectDTO.setDescription(project.getDescription());
        return projectDTO;
    }

    // Conversion de User en UserDTO
    default UserDTO mapUserToUserDTO(User user) {
        if (user == null) return null;
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
}
