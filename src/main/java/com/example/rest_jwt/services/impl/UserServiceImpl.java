package com.example.rest_jwt.services.impl;

import com.example.rest_jwt.models.Role;
import com.example.rest_jwt.models.Status;
import com.example.rest_jwt.models.User;
import com.example.rest_jwt.repositories.RoleRepository;
import com.example.rest_jwt.repositories.UserRepository;
import com.example.rest_jwt.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        log.info("IN getAllUsers - {} users found", allUsers.size());
        return allUsers;
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if (user == null){
            log.warn("IN findByUsername - user not found by username: {}", username);
        }

        log.info("IN findByUsername - user: {} found by username: {}", user, username);
        return user;
    }

    @Override
    public User findById(Long id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null){
            log.warn("IN findById - user not found by id: {}", id);
        }

        log.info("IN findById - user: {} found by id: {}", user);
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        log.info("IN deleteUser - user with id: {} successfully deleted");
    }
}
