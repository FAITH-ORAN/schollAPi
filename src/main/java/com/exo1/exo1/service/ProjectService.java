package com.exo1.exo1.service;

import com.exo1.exo1.model.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    List<Project> findAll();
    Optional<Project> findById(Long id);
    Project save(Project project);
    void deleteById(Long id);
}
