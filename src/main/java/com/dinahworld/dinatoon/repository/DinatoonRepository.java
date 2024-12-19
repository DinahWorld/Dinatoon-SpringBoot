package com.dinahworld.dinatoon.repository;

import com.dinahworld.dinatoon.model.Dinatoon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DinatoonRepository extends JpaRepository<Dinatoon, Long> {
}