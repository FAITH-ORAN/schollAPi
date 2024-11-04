package com.exo1.exo1.repository;

import com.exo1.exo1.model.Project;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository  extends JpaRepository<Project, Long> {

    @EntityGraph(attributePaths = {"users", "tasks"})
    List<Project> findAll();
}
