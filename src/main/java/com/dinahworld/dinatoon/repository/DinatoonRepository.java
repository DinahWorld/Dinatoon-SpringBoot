package com.dinahworld.dinatoon.repository;

import com.dinahworld.dinatoon.model.Dinatoon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DinatoonRepository extends JpaRepository<Dinatoon, Long> {
    Optional<Dinatoon> findByName(String name);
}