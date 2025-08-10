package com.dinahworld.dinatoon.service.impl;

import com.dinahworld.dinatoon.enums.RoleEnum;
import com.dinahworld.dinatoon.exception.UserException;
import com.dinahworld.dinatoon.model.Category;
import com.dinahworld.dinatoon.model.User;
import com.dinahworld.dinatoon.repository.CategoryRepository;
import com.dinahworld.dinatoon.repository.UserRepository;
import com.dinahworld.dinatoon.service.RoleService;
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
    private final RoleService roleService;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public User saveUser(User user) {
        var newUser = userRepository.save(user);
        categoryRepository.save(new Category("default", newUser));
        categoryRepository.save(new Category("inprogress", newUser));
        categoryRepository.save(new Category("finished", newUser));
        categoryRepository.save(new Category("liked", newUser));
        return newUser;
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

    @Override
    @Transactional
    public User createUserOAuth2(String email, String authType) {
        if (userRepository.findByEmail(email).isPresent()) {
            return userRepository.findByEmail(email).get();
        }
        User user = new User();
        user.setProvider(authType);
        user.setEmail(email);
        user.setUsername(email.substring(0, email.indexOf("@")));
        user.setRole(roleService.getRoleByName(RoleEnum.USER));

        User createdUser = userRepository.save(user);
        return createdUser;
    }
}