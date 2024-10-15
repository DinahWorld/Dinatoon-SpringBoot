package com.dinahworld.dinatoon.application.ports.output;

import com.dinahworld.dinatoon.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    User save(User user);

    void deleteById(Long id);
}