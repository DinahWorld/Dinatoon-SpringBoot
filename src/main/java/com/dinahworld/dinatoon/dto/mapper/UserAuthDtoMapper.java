package com.dinahworld.dinatoon.dto.mapper;

import com.dinahworld.dinatoon.dto.UserAuthDto;
import com.dinahworld.dinatoon.enums.RoleEnum;
import com.dinahworld.dinatoon.model.User;
import com.dinahworld.dinatoon.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class UserAuthDtoMapper implements Function<UserAuthDto, User> {
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public User apply(UserAuthDto dto) {
        User user = new User();
        user.setEmail(dto.email());
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(roleService.getRoleByName(RoleEnum.USER));
        user.setEnabled(false);
        user.setProvider("DATABASE");
        return user;
    }
}