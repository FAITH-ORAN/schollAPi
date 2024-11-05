package com.exo1.exo1.model;

import com.exo1.exo1.enums.TasksStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tasks", indexes = {
        @Index(name = "idx_task_user_id", columnList = "user_id"),
        @Index(name = "idx_task_project_id", columnList = "project_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private TasksStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
