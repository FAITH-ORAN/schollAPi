package com.exo1.exo1.repository;

import com.exo1.exo1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // all users
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.projects")
    List<User> findAllUsersWithProjects();

    // user by id
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.projects WHERE u.id = :id")
    Optional<User> findUserByIdWithProjects(@Param("id") Long id);

    // delete a user
    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.id = :id")
    void deleteByIdJPQL(@Param("id") Long id);

    // update user
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.name = :name, u.email = :email WHERE u.id = :id")
    void updateUser(@Param("id") Long id, @Param("name") String name, @Param("email") String email);
}
