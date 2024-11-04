package com.exo1.exo1.mapper;

import com.exo1.exo1.dto.TaskDTO;
import com.exo1.exo1.enums.TasksStatus;
import com.exo1.exo1.model.Project;
import com.exo1.exo1.model.Task;
import com.exo1.exo1.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-04T14:35:02+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (GraalVM Community)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskDTO toDto(Task task) {
        if ( task == null ) {
            return null;
        }

        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setProjectId( taskProjectId( task ) );
        taskDTO.setUserId( taskUserId( task ) );
        taskDTO.setId( task.getId() );
        taskDTO.setTitle( task.getTitle() );
        if ( task.getStatus() != null ) {
            taskDTO.setStatus( Enum.valueOf( TasksStatus.class, task.getStatus() ) );
        }

        return taskDTO;
    }

    @Override
    public Task toEntity(TaskDTO taskDTO) {
        if ( taskDTO == null ) {
            return null;
        }

        Task.TaskBuilder task = Task.builder();

        task.id( taskDTO.getId() );
        task.title( taskDTO.getTitle() );
        if ( taskDTO.getStatus() != null ) {
            task.status( taskDTO.getStatus().name() );
        }

        return task.build();
    }

    private Long taskProjectId(Task task) {
        if ( task == null ) {
            return null;
        }
        Project project = task.getProject();
        if ( project == null ) {
            return null;
        }
        Long id = project.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long taskUserId(Task task) {
        if ( task == null ) {
            return null;
        }
        User user = task.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
