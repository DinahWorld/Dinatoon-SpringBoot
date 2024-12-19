package com.dinahworld.dinatoon.repository;


import com.dinahworld.dinatoon.enums.RoleEnum;
import com.dinahworld.dinatoon.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);
}