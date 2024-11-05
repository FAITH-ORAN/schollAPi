package com.exo1.exo1.controller;

import com.exo1.exo1.dto.TaskDTO;
import com.exo1.exo1.mapper.TaskMapper;
import com.exo1.exo1.model.Project;
import com.exo1.exo1.model.Task;
import com.exo1.exo1.model.User;
import com.exo1.exo1.service.ProjectService;
import com.exo1.exo1.service.TaskService;
import com.exo1.exo1.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get all tasks", description = "Retrieve a list of all tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.findAll().stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get task by ID", description = "Retrieve a task by their unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task found"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskService.findById(id);
        return task.map(value -> ResponseEntity.ok(taskMapper.toDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @Operation(summary = "Create a task", description = "Create a new task with the provided details")
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

    @Operation(summary = "Update an existing task", description = "Update the details of an existing task")
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

    @Operation(summary = "Delete a task", description = "Delete a task by their unique identifier")
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
