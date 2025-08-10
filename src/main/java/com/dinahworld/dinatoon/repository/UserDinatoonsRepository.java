package com.dinahworld.dinatoon.repository;

import com.dinahworld.dinatoon.model.UserDinatoons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDinatoonsRepository extends JpaRepository<UserDinatoons, Long> {
    Optional<UserDinatoons> findByDinatoonId(UUID dinatoonId);

    Optional<UserDinatoons> findByUserId(Long userId);

    Optional<List<UserDinatoons>> findAllByUserId(Long userId);
}