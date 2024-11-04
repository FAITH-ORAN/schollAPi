package com.exo1.exo1.mapper;

import com.exo1.exo1.dto.UserDTO;
import com.exo1.exo1.dto.ProjectDTO;
import com.exo1.exo1.model.User;
import com.exo1.exo1.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "projects", expression = "java(mapProjectsToProjectDTOs(user.getProjects()))")
    UserDTO toDto(User user);

    @Mapping(target = "projects", ignore = true)
    User toEntity(UserDTO userDTO);

    default Set<ProjectDTO> mapProjectsToProjectDTOs(Set<Project> projects) {
        return projects != null ? projects.stream()
                .map(project -> {
                    ProjectDTO projectDTO = new ProjectDTO();
                    projectDTO.setId(project.getId());
                    projectDTO.setName(project.getName());
                    projectDTO.setDescription(project.getDescription());
                    return projectDTO;
                })
                .collect(Collectors.toSet()) : Set.of();
    }
}
