package com.exo1.exo1.service.impl;

import com.exo1.exo1.model.Project;
import com.exo1.exo1.repository.ProjectRepository;
import com.exo1.exo1.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
    @Override
    public Page<Project> findAllProjectsWithPagination(Pageable pageable) {
        return projectRepository.findAllProjectsWithUsersAndTasks(pageable);
    }

    @Override
    @Cacheable(value = "projects", key = "#id")
    public Optional<Project> findById(Long id) {
        return projectRepository.findProjectByIdWithUsersAndTasks(id);
    }

    @Override
    @CachePut(value = "projects", key = "#result.id")
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Override
    @Transactional
    public void updateProject(Long id, String name, String description) {
        projectRepository.updateProject(id, name, description);
    }

    @Override
    @CacheEvict(value = "projects", key = "#id")
    public void deleteById(Long id) {
        projectRepository.deleteByIdJPQL(id);
    }
}
