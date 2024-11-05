package com.exo1.exo1.service;

import com.exo1.exo1.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Page<Task> findAllTasksWithPagination(Pageable pageable);
    Optional<Task> findById(Long id);
    Task save(Task task);
    void deleteById(Long id);

    public void updateTask(Long id, String title, String status);
}
