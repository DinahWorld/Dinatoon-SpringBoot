package com.dinahworld.dinatoon.service;

import com.dinahworld.dinatoon.model.CategoryDinatoon;

import java.util.List;

public interface CategoryDinatoonService {
    List<CategoryDinatoon> getByUserDinatoonId(Long id);

    List<CategoryDinatoon> getAllByCategoryId(Long id);
}