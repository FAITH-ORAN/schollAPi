package com.exo1.exo1.service;

import com.exo1.exo1.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    Page<Project> findAllProjectsWithPagination(Pageable pageable);
    Optional<Project> findById(Long id);
    Project save(Project project);
    void deleteById(Long id);
    void updateProject(Long id, String name, String description);
}
