package com.dinahworld.dinatoon.service;


import com.dinahworld.dinatoon.enums.RoleEnum;
import com.dinahworld.dinatoon.model.Role;

import java.util.List;

public interface RoleService {
    Role save(Role role);

    Role getRoleByName(RoleEnum roleEnum);

    Role getRoleById(Long id);

    List<Role> getAllRoles();
}