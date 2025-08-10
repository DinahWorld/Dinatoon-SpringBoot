package com.dinahworld.dinatoon.repository;

import com.dinahworld.dinatoon.model.CategoryDinatoon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryDinatoonRepository extends JpaRepository<CategoryDinatoon, Long> {
    Optional<List<CategoryDinatoon>> findAllByUserDinatoons(Long id);

    Optional<List<CategoryDinatoon>> findAllByCategoryId(Long id);
}