package com.dinahworld.dinatoon.feature.category.service;

import com.dinahworld.dinatoon.feature.category.model.CategoryDinatoon;

import java.util.List;

public interface CategoryDinatoonService {
    List<CategoryDinatoon> getByUserDinatoonId(Long id);

    List<CategoryDinatoon> getAllByCategoryId(Long id);
}