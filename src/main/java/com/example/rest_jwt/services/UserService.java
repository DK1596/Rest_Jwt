package com.example.rest_jwt.services;

import com.example.rest_jwt.models.User;

import java.util.List;

public interface UserService {
    User register(User user);

    List<User> getAllUsers();

    User findByUsername(String name);

    User findById(Long id);

    void deleteUser(Long id);
}
