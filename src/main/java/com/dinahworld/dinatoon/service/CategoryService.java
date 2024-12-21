package com.dinahworld.dinatoon.service;

import com.dinahworld.dinatoon.model.Category;
import com.dinahworld.dinatoon.model.Dinatoon;
import com.dinahworld.dinatoon.model.User;

import java.util.List;

public interface CategoryService {
    Category createCategory(String name, User user);

    Category addDinatoonToCategory(User user, Long categoryId, Long dinatoonId);

    void deleteDinatoonFromCategory(User user, Long categoryId, Long dinatoonId);

    List<Dinatoon> getDinatoonFromCategory(Long id);

    void deleteCategory(Long id);

    void deleteCategoryFromUser(User user, Long category);

    Category updateCategoryName(Long id, String name);

    Category getCategory(Long id);
}