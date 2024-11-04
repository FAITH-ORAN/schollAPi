package com.exo1.exo1.mapper;

import com.exo1.exo1.dto.UserDTO;
import com.exo1.exo1.model.Project;
import com.exo1.exo1.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "projectIds", source = "projects") // Map la collection projects vers projectIds
    UserDTO toDto(User user);

    @Mapping(target = "projects", ignore = true) // On ignore projects ici pour éviter les boucles
    User toEntity(UserDTO userDTO);

    default Set<Long> mapProjectsToIds(Set<Project> projects) {
        return (projects != null)
                ? projects.stream().map(Project::getId).collect(Collectors.toSet())
                : new HashSet<>();
    }
}
