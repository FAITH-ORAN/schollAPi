package com.exo1.exo1.mapper;

import com.exo1.exo1.dto.ProjectDTO;
import com.exo1.exo1.dto.TaskDTO;
import com.exo1.exo1.dto.UserDTO;
import com.exo1.exo1.model.Project;
import com.exo1.exo1.model.Task;
import com.exo1.exo1.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {TaskMapper.class})
public interface ProjectMapper {

    @Mapping(target = "users", expression = "java(mapUsersToUserDTOs(project.getUsers()))")
    @Mapping(target = "tasks", source = "tasks")
    ProjectDTO toDto(Project project);

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    Project toEntity(ProjectDTO projectDTO);


    default Set<UserDTO> mapUsersToUserDTOs(Set<User> users) {
        return users != null ? users.stream()
                .map(user -> {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setId(user.getId());
                    userDTO.setName(user.getName());
                    userDTO.setEmail(user.getEmail());
                    return userDTO;
                })
                .collect(Collectors.toSet()) : Set.of();
    }

    // Méthode pour mapper les informations de base des tâches
    default List<TaskDTO> mapTasksBasicInfo(List<Task> tasks) {
        return tasks != null ? tasks.stream()
                .map(task -> {
                    TaskDTO taskDTO = new TaskDTO();
                    taskDTO.setId(task.getId());
                    taskDTO.setTitle(task.getTitle());
                    taskDTO.setStatus(task.getStatus());
                    return taskDTO;
                })
                .collect(Collectors.toList()) : List.of();
    }
}
