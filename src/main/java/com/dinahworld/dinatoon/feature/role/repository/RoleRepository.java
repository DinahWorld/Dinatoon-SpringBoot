package com.dinahworld.dinatoon.feature.role.repository;


import com.dinahworld.dinatoon.feature.role.RoleEnum;
import com.dinahworld.dinatoon.feature.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);
}