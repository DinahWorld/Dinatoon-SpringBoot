package com.dinahworld.dinatoon.startup;

import com.dinahworld.dinatoon.enums.RoleEnum;
import com.dinahworld.dinatoon.model.User;
import com.dinahworld.dinatoon.repository.UserRepository;
import com.dinahworld.dinatoon.service.RoleService;
import com.dinahworld.dinatoon.service.UserDinatoonService;
import com.dinahworld.dinatoon.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DbInit {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserDinatoonService userDinatoonService;
    @Value("${admin.password}")
    private String password;

    @PostConstruct
    private void postConstruct() {
        if (userRepository.findByEmail("admin@admin.com").isEmpty()) {
            var user = new User();
            user.setId(1L);
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode(password));
            user.setEnabled(true);
            user.setEmail("admin@admin.com");
            user.setRole(roleService.getRoleByName(RoleEnum.ADMIN));
            user.setProvider("LOCAL");
            userService.saveUser(user);

            userDinatoonService.saveMangaByDefault(UUID.fromString("32d76d19-8a05-4db0-9fc2-e0b0648fe9d0"), user);
            userDinatoonService.saveMangaByDefault(UUID.fromString("83644dad-1f46-47c7-8a75-484cf311a4c4"), user);
            userDinatoonService.saveMangaByDefault(UUID.fromString("5fc51376-8142-4d03-bb99-fe784e1e1c45"), user);
            userDinatoonService.saveMangaByDefault(UUID.fromString("596191eb-69ee-4401-983e-cc07e277fa17"), user);
            userDinatoonService.saveMangaByDefault(UUID.fromString("9a414441-bbad-43f1-a3a7-dc262ca790a3"), user);
            userDinatoonService.saveMangaByDefault(UUID.fromString("57e1d491-1dc9-4854-83bf-7a9379566fb2"), user);


        }
    }
}