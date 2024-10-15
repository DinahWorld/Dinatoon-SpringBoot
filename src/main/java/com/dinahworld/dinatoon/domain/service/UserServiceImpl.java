package com.dinahworld.dinatoon.domain.service;

import com.dinahworld.dinatoon.application.ports.input.UserService;
import com.dinahworld.dinatoon.application.ports.output.UserRepository;
import com.dinahworld.dinatoon.domain.exception.UserException;
import com.dinahworld.dinatoon.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String USER_NOT_FOUND = "User not found with ID : ";
    private final UserRepository userRepository;

    @Override
    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserException(USER_NOT_FOUND));
    }
}