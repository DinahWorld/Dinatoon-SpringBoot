package com.dinahworld.dinatoon.feature.category.service;

import com.dinahworld.dinatoon.feature.category.dto.CategoryDinatoonResponse;
import com.dinahworld.dinatoon.feature.category.model.Category;
import com.dinahworld.dinatoon.feature.user.model.User;

import java.util.List;

public interface CategoryService {
    Category createCategory(String name, User user);

    void deleteCategory(Long id);

    Category updateCategoryName(Long id, String name);

    Category getCategory(Long id);

    List<Category> getAllUserCategory(Long userId);

    List<CategoryDinatoonResponse> getCategoryDinatoon(User user);
}