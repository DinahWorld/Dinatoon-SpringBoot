package com.dinahworld.dinatoon.feature.role.service;


import com.dinahworld.dinatoon.feature.role.RoleEnum;
import com.dinahworld.dinatoon.feature.role.model.Role;

import java.util.List;

public interface RoleService {
    Role save(Role role);

    Role getRoleByName(RoleEnum roleEnum);

    Role getRoleById(Long id);

    List<Role> getAllRoles();
}