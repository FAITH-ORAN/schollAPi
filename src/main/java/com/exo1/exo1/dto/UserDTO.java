package com.exo1.exo1.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private Set<Long> projectIds;
}
