package com.exo1.exo1.mapper;

import com.exo1.exo1.dto.ProjectDTO;
import com.exo1.exo1.dto.TaskDTO;
import com.exo1.exo1.model.Project;
import com.exo1.exo1.model.Task;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-04T16:20:21+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (GraalVM Community)"
)
@Component
public class ProjectMapperImpl implements ProjectMapper {

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public ProjectDTO toDto(Project project) {
        if ( project == null ) {
            return null;
        }

        ProjectDTO projectDTO = new ProjectDTO();

        projectDTO.setTasks( taskListToTaskDTOList( project.getTasks() ) );
        projectDTO.setId( project.getId() );
        projectDTO.setName( project.getName() );
        projectDTO.setDescription( project.getDescription() );

        projectDTO.setUsers( mapUsersToUserDTOs(project.getUsers()) );

        return projectDTO;
    }

    @Override
    public Project toEntity(ProjectDTO projectDTO) {
        if ( projectDTO == null ) {
            return null;
        }

        Project.ProjectBuilder project = Project.builder();

        project.id( projectDTO.getId() );
        project.name( projectDTO.getName() );
        project.description( projectDTO.getDescription() );

        return project.build();
    }

    protected List<TaskDTO> taskListToTaskDTOList(List<Task> list) {
        if ( list == null ) {
            return null;
        }

        List<TaskDTO> list1 = new ArrayList<TaskDTO>( list.size() );
        for ( Task task : list ) {
            list1.add( taskMapper.toDto( task ) );
        }

        return list1;
    }
}
