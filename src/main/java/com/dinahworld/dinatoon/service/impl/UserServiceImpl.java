package com.dinahworld.dinatoon.service.impl;

import com.dinahworld.dinatoon.exception.UserException;
import com.dinahworld.dinatoon.model.User;
import com.dinahworld.dinatoon.repository.UserRepository;
import com.dinahworld.dinatoon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        return userRepository.findById(id).orElseThrow(() -> new UserException(USER_NOT_FOUND));
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

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, User user) {
        userRepository.findById(id).orElseThrow(() -> new UserException(USER_NOT_FOUND));
        user.setId(id);
        userRepository.deleteById(id);
        userRepository.save(user);
        return user;
    }
}