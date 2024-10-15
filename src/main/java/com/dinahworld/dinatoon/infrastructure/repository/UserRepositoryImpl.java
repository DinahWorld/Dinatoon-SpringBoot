package com.dinahworld.dinatoon.infrastructure.repository;

import com.dinahworld.dinatoon.application.ports.output.UserRepository;
import com.dinahworld.dinatoon.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryImpl extends UserRepository, JpaRepository<User, Long> {
}