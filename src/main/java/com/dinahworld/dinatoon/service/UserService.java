package com.dinahworld.dinatoon.service;

import com.dinahworld.dinatoon.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User getUserById(Long id);
    void deleteUser(Long id);
    User getUserByEmail(String email);

    List<User> getAllUsers();

    User updateUser(Long id, User user);
}