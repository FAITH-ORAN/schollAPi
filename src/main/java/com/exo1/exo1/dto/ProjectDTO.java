package com.exo1.exo1.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private Set<Long> userIds = new HashSet<>();
    private List<Long> taskIds = new ArrayList<>();

}
