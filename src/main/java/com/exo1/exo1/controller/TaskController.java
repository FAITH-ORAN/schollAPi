package com.exo1.exo1.controller;

import com.exo1.exo1.dto.TaskDTO;
import com.exo1.exo1.mapper.TaskMapper;
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
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.findAll().stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskService.findById(id);
        return task.map(value -> ResponseEntity.ok(taskMapper.toDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public TaskDTO createTask(@RequestBody TaskDTO taskDTO) {
        Task task = taskMapper.toEntity(taskDTO);

        if (taskDTO.getProject() != null) {
            Project project = projectService.findById(taskDTO.getProject().getId())
                    .orElseThrow(() -> new RuntimeException("Project not found"));
            task.setProject(project);
        }

        if (taskDTO.getUser() != null) {
            User user = userService.findById(taskDTO.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            task.setUser(user);
        }

        Task createdTask = taskService.save(task);
        return taskMapper.toDto(createdTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        Optional<Task> existingTaskOpt = taskService.findById(id);
        if (existingTaskOpt.isPresent()) {
            Task existingTask = existingTaskOpt.get();
            existingTask.setTitle(taskDTO.getTitle());
            existingTask.setStatus(taskDTO.getStatus());

            if (taskDTO.getProject() != null) {
                Project project = projectService.findById(taskDTO.getProject().getId())
                        .orElseThrow(() -> new RuntimeException("Project not found"));
                existingTask.setProject(project);
            }

            if (taskDTO.getUser() != null) {
                User user = userService.findById(taskDTO.getUser().getId())
                        .orElseThrow(() -> new RuntimeException("User not found"));
                existingTask.setUser(user);
            }

            Task updatedTask = taskService.save(existingTask);
            return ResponseEntity.ok(taskMapper.toDto(updatedTask));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (taskService.findById(id).isPresent()) {
            taskService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
