package com.dinahworld.dinatoon.application.ports.input;

import com.dinahworld.dinatoon.domain.model.User;

public interface UserService {
    User createUser(User user);

    User getUserById(Long id);

    void deleteUser(Long id);

    User getUserByEmail(String email);
}