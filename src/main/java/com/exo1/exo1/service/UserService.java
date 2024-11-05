package com.exo1.exo1.service;

import com.exo1.exo1.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Page<User> findAllUsersWithPagination(Pageable pageable);
    Optional<User> findById(Long id);
    User save(User user);
    void deleteById(Long id);
    void updateUser(Long id, String name, String email);


}
