package com.exo1.exo1.mapper;

import com.exo1.exo1.dto.ProjectDTO;
import com.exo1.exo1.model.Project;
import com.exo1.exo1.model.Task;
import com.exo1.exo1.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(target = "userIds", source = "users")
    @Mapping(target = "taskIds", source = "tasks")
    ProjectDTO toDto(Project project);

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    Project toEntity(ProjectDTO projectDTO);

    default Set<Long> mapUsersToIds(Set<User> users) {
        if (users == null) {
            return new HashSet<>();
        }
        return users.stream()
                .map(User::getId)
                .collect(Collectors.toSet());
    }

    default List<Long> mapTasksToIds(List<Task> tasks) {
        if (tasks == null) {
            return new ArrayList<>();
        }
        return tasks.stream()
                .map(Task::getId)
                .collect(Collectors.toList());
    }
}
