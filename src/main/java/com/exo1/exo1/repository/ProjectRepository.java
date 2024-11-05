package com.exo1.exo1.repository;

import com.exo1.exo1.model.Project;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository  extends JpaRepository<Project, Long> {

    @EntityGraph(attributePaths = {"users", "tasks"})
    @Query("SELECT p FROM Project p")
    List<Project> findAllProjectsWithUsersAndTasks();

    //update by id
    @EntityGraph(attributePaths = {"users", "tasks"})
    @Query("SELECT p FROM Project p WHERE p.id = :id")
    Optional<Project> findProjectByIdWithUsersAndTasks(@Param("id") Long id);

    //update project
    @Modifying
    @Transactional
    @Query("UPDATE Project p SET p.name = :name, p.description = :description WHERE p.id = :id")
    void updateProject(@Param("id") Long id, @Param("name") String name, @Param("description") String description);

    //delete a project
    @Modifying
    @Transactional
    @Query("DELETE FROM Project p WHERE p.id = :id")
    void deleteByIdJPQL(@Param("id") Long id);
}
