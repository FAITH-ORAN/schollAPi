package com.exo1.exo1.dto;

import com.exo1.exo1.enums.TasksStatus;
import lombok.Data;

@Data
public class TaskDTO {

    private Long id;
    private String title;
    private TasksStatus status;
    private Long projectId;
    private Long userId;
}
