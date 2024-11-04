package com.exo1.exo1.mapper;

import com.exo1.exo1.dto.TaskDTO;
import com.exo1.exo1.model.Task;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-04T16:20:21+0100",
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

        taskDTO.setId( task.getId() );
        taskDTO.setTitle( task.getTitle() );
        taskDTO.setStatus( task.getStatus() );

        taskDTO.setProject( mapProjectToProjectDTO(task.getProject()) );
        taskDTO.setUser( mapUserToUserDTO(task.getUser()) );

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
        task.status( taskDTO.getStatus() );

        return task.build();
    }
}
