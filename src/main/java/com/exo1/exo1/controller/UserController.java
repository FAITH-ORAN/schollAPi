package com.exo1.exo1.controller;

import com.exo1.exo1.dto.UserDTO;
import com.exo1.exo1.mapper.UserMapper;
import com.exo1.exo1.model.Project;
import com.exo1.exo1.model.User;
import com.exo1.exo1.service.ProjectService;
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
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        return user.map(value -> ResponseEntity.ok(userMapper.toDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);

        if (userDTO.getProjectIds() != null && !userDTO.getProjectIds().isEmpty()) {
            Set<Project> projects = userDTO.getProjectIds().stream()
                    .map(projectId -> projectService.findById(projectId).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            user.setProjects(projects);
        }

        User createdUser = userService.save(user);
        return userMapper.toDto(createdUser);
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        Optional<User> existingUserOpt = userService.findById(id);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setName(userDTO.getName());
            existingUser.setEmail(userDTO.getEmail());


            if (userDTO.getProjectIds() != null && !userDTO.getProjectIds().isEmpty()) {
                Set<Project> projects = userDTO.getProjectIds().stream()
                        .map(projectId -> projectService.findById(projectId).orElse(null))
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
