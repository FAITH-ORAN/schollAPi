package com.exo1.exo1.controller;

import com.exo1.exo1.dto.UserDTO;
import com.exo1.exo1.mapper.UserMapper;
import com.exo1.exo1.model.Project;
import com.exo1.exo1.model.User;
import com.exo1.exo1.service.ProjectService;
import com.exo1.exo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProjectService projectService;


    @Operation(summary = "Get all users", description = "Retrieve a list of all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        return userService.findAllUsersWithPagination(pageable)
                .map(userMapper::toDto); // Map each User to UserDTO
    }

    @Operation(summary = "Get user by ID", description = "Retrieve a user by their unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        return user.map(value -> ResponseEntity.ok(userMapper.toDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a user", description = "Create a new user with the provided details")
    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);

        if (userDTO.getProjects() != null && !userDTO.getProjects().isEmpty()) {
            Set<Project> projects = userDTO.getProjects().stream()
                    .map(projectDto -> projectService.findById(projectDto.getId()).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            user.setProjects(projects);
        }

        User createdUser = userService.save(user);
        return userMapper.toDto(createdUser);
    }

    @Operation(summary = "Update an existing user", description = "Update the details of an existing user")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        Optional<User> existingUserOpt = userService.findById(id);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setName(userDTO.getName());
            existingUser.setEmail(userDTO.getEmail());

            if (userDTO.getProjects() != null && !userDTO.getProjects().isEmpty()) {
                Set<Project> projects = userDTO.getProjects().stream()
                        .map(projectDto -> projectService.findById(projectDto.getId()).orElse(null))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());
                existingUser.setProjects(projects);
            } else {
                existingUser.getProjects().clear();
            }

            User updatedUser = userService.save(existingUser);
            return ResponseEntity.ok(userMapper.toDto(updatedUser));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a user", description = "Delete a user by their unique identifier")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.findById(id).isPresent()) {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
