package com.dinahworld.dinatoon.service.impl;

import com.dinahworld.dinatoon.enums.RoleEnum;
import com.dinahworld.dinatoon.model.Role;
import com.dinahworld.dinatoon.repository.RoleRepository;
import com.dinahworld.dinatoon.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;


    @Override
    public Role save(Role role) {
        Role save = roleRepository.save(role);
        log.info("Inserted RoleEnum: {}", role);
        return save;
    }

    @Override
    public Role getRoleByName(RoleEnum name) {
        Role role = roleRepository.findByName(name).orElseThrow(() -> new NoSuchElementException("RoleEnum not found with name: " + name));
        log.info("Retrieved role by name {} : {}", name, role);
        return role;
    }


    @Override
    public Role getRoleById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new NoSuchElementException("RoleEnum not found with ID: " + id));
        log.info("Retrieved role by ID {} : {}", id, role);
        return role;
    }


    @Override
    public List<Role> getAllRoles() {
        log.info("Retrieving all roles.");
        return roleRepository.findAll();
    }
}