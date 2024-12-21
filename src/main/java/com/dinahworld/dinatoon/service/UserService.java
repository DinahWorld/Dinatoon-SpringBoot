package com.dinahworld.dinatoon.service;

import com.dinahworld.dinatoon.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User getUserById(Long id);
    void deleteUser(Long id);
    User getUserByEmail(String email);
    List<User> getAllUsers();
    User updateUser(Long id, User user);

    UserDetails createUserOAuth2(String email, String google);
}