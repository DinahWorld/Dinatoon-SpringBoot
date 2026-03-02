package com.dinahworld.dinatoon.feature.category.repository;

import com.dinahworld.dinatoon.feature.category.model.CategoryDinatoon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryDinatoonRepository extends JpaRepository<CategoryDinatoon, Long> {
    Optional<List<CategoryDinatoon>> findAllByUserDinatoons(Long id);

    Optional<List<CategoryDinatoon>> findAllByCategoryId(Long id);
}