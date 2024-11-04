package com.exo1.exo1.mapper;

import com.exo1.exo1.dto.ProjectDTO;
import com.exo1.exo1.model.Project;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-04T14:35:03+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (GraalVM Community)"
)
@Component
public class ProjectMapperImpl implements ProjectMapper {

    @Override
    public ProjectDTO toDto(Project project) {
        if ( project == null ) {
            return null;
        }

        ProjectDTO projectDTO = new ProjectDTO();

        projectDTO.setUserIds( mapUsersToIds( project.getUsers() ) );
        projectDTO.setTaskIds( mapTasksToIds( project.getTasks() ) );
        projectDTO.setId( project.getId() );
        projectDTO.setName( project.getName() );
        projectDTO.setDescription( project.getDescription() );

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
}
