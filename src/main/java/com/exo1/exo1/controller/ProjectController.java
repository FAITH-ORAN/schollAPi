package com.exo1.exo1.controller;

import com.exo1.exo1.dto.ProjectDTO;
import com.exo1.exo1.mapper.ProjectMapper;
import com.exo1.exo1.model.Project;
import com.exo1.exo1.model.Task;
import com.exo1.exo1.model.User;
import com.exo1.exo1.service.ProjectService;
import com.exo1.exo1.service.TaskService;
import com.exo1.exo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<ProjectDTO> getAllProjects() {
        return projectService.findAll().stream()
                .map(projectMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id) {
        Optional<Project> project = projectService.findById(id);
        return project.map(value -> ResponseEntity.ok(projectMapper.toDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProjectDTO createProject(@RequestBody ProjectDTO projectDTO) {
        Project project = projectMapper.toEntity(projectDTO);

        if (projectDTO.getUsers() != null && !projectDTO.getUsers().isEmpty()) {
            Set<User> users = projectDTO.getUsers().stream()
                    .map(userDto -> userService.findById(userDto.getId()).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            project.setUsers(users);
        }

        if (projectDTO.getTasks() != null && !projectDTO.getTasks().isEmpty()) {
            List<Task> tasks = projectDTO.getTasks().stream()
                    .map(taskDto -> taskService.findById(taskDto.getId()).orElse(null))
                    .filter(Objects::nonNull)
                    .peek(task -> task.setProject(project))
                    .collect(Collectors.toList());
            project.setTasks(tasks);
        }

        Project createdProject = projectService.save(project);
        return projectMapper.toDto(createdProject);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Long id, @RequestBody ProjectDTO projectDTO) {
        Optional<Project> existingProjectOpt = projectService.findById(id);
        if (existingProjectOpt.isPresent()) {
            Project existingProject = existingProjectOpt.get();
            existingProject.setName(projectDTO.getName());
            existingProject.setDescription(projectDTO.getDescription());

            if (projectDTO.getUsers() != null && !projectDTO.getUsers().isEmpty()) {
                Set<User> users = projectDTO.getUsers().stream()
                        .map(userDto -> userService.findById(userDto.getId()).orElse(null))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());
                existingProject.setUsers(users);
            } else {
                existingProject.getUsers().clear();
            }

            if (projectDTO.getTasks() != null && !projectDTO.getTasks().isEmpty()) {
                List<Task> tasks = projectDTO.getTasks().stream()
                        .map(taskDto -> taskService.findById(taskDto.getId()).orElse(null))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                existingProject.setTasks(tasks);
            } else {
                existingProject.getTasks().clear();
            }

            Project updatedProject = projectService.save(existingProject);
            return ResponseEntity.ok(projectMapper.toDto(updatedProject));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        if (projectService.findById(id).isPresent()) {
            projectService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
