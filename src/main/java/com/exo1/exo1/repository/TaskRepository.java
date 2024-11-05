package com.exo1.exo1.repository;

import com.exo1.exo1.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    //all tasks
    @Query("SELECT DISTINCT t FROM Task t LEFT JOIN FETCH t.user LEFT JOIN FETCH t.project")
    List<Task> findAllTasksWithUserAndProject();

    //task with id
    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.user LEFT JOIN FETCH t.project WHERE t.id = :id")
    Optional<Task> findTaskByIdWithUserAndProject(@Param("id") Long id);

    //update
    @Modifying
    @Transactional
    @Query("UPDATE Task t SET t.title = :title, t.status = :status WHERE t.id = :id")
    void updateTask(@Param("id") Long id, @Param("title") String title, @Param("status") String status);

    //delete
    @Modifying
    @Transactional
    @Query("DELETE FROM Task t WHERE t.id = :id")
    void deleteByIdJPQL(@Param("id") Long id);
}
