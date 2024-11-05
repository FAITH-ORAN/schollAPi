package com.exo1.exo1.service.impl;

import com.exo1.exo1.model.Task;
import com.exo1.exo1.repository.TaskRepository;
import com.exo1.exo1.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    @Override
    public Page<Task> findAllTasksWithPagination(Pageable pageable) {
        return taskRepository.findAllTasksWithUserAndProject(pageable);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return taskRepository.findTaskByIdWithUserAndProject(id);
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public void updateTask(Long id, String title, String status) {
        taskRepository.updateTask(id, title, status);
    }

    @Override
    public void deleteById(Long id) {
        taskRepository.deleteByIdJPQL(id);
    }
}
