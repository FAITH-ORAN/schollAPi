package com.exo1.exo1.service.impl;

import com.exo1.exo1.model.User;
import com.exo1.exo1.repository.UserRepository;
import com.exo1.exo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAllUsersWithProjects();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findUserByIdWithProjects(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteByIdJPQL(id);
    }
    @Override
    @Transactional
    public void updateUser(Long id, String name, String email) {
        userRepository.updateUser(id, name, email);
    }
}
