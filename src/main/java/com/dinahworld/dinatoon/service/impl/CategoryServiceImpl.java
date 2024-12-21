package com.dinahworld.dinatoon.service.impl;

import com.dinahworld.dinatoon.exception.DinatoonException;
import com.dinahworld.dinatoon.model.Category;
import com.dinahworld.dinatoon.model.Dinatoon;
import com.dinahworld.dinatoon.model.User;
import com.dinahworld.dinatoon.repository.CategoryRepository;
import com.dinahworld.dinatoon.service.CategoryService;
import com.dinahworld.dinatoon.service.DinatoonService;
import com.dinahworld.dinatoon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final DinatoonService dinatoonService;
    private final UserService userService;

    @Override
    @Transactional
    public Category createCategory(String name, User user) {
        var category = categoryRepository.save(new Category(name, user));
        return category;
    }

    @Override
    @Transactional
    public Category addDinatoonToCategory(User user, Long categoryId, Long dinatoonId) {
        var dinatoon = dinatoonService.getDinatoonById(dinatoonId);
        var category = getCategory(categoryId);
        category.getDinatoons().add(dinatoon);
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void deleteDinatoonFromCategory(User user, Long categoryId, Long dinatoonId) {
        var dinatoon = dinatoonService.getDinatoonById(dinatoonId);
        var category = getCategory(categoryId);
        category.getDinatoons().remove(dinatoon);
        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public List<Dinatoon> getDinatoonFromCategory(Long id) {
        var category = getCategory(id);
        return category.getDinatoons();
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteCategoryFromUser(User user, Long categoryId) {
        var category = getCategory(categoryId);
        user.getCategories().remove(category);
    }

    @Override
    @Transactional
    public Category updateCategoryName(Long id, String name) {
        var category = getCategory(id);
        category.setName(name);
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public Category getCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new DinatoonException("Category not found"));
    }

}