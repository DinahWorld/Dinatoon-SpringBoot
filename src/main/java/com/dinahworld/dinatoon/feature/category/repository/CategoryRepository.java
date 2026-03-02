package com.dinahworld.dinatoon.feature.category.repository;

import com.dinahworld.dinatoon.feature.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<List<Category>> findAllByUserId(Long userId);
}