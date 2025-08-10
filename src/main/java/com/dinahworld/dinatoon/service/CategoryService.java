package com.dinahworld.dinatoon.service;

import com.dinahworld.dinatoon.dto.CategoryDinatoonResponse;
import com.dinahworld.dinatoon.model.Category;
import com.dinahworld.dinatoon.model.User;

import java.util.List;

public interface CategoryService {
    Category createCategory(String name, User user);

    void deleteCategory(Long id);

    Category updateCategoryName(Long id, String name);

    Category getCategory(Long id);

    List<Category> getAllUserCategory(Long userId);

    List<CategoryDinatoonResponse> getCategoryDinatoon(User user);
}