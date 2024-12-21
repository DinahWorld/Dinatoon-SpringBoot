package com.dinahworld.dinatoon.controller;

import com.dinahworld.dinatoon.model.Category;
import com.dinahworld.dinatoon.model.User;
import com.dinahworld.dinatoon.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping()
    @Operation(description = "Create Category")
    public ResponseEntity<Category> createCategory(Authentication authentication, @RequestParam String name) {
        var user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(categoryService.createCategory(name, user));
    }

    @PostMapping("/dinatoon")
    @Operation(description = "Add Dinatoon to a  Category")
    public ResponseEntity<Category> addDinatoonToACategory(Authentication authentication, @RequestParam Long dinatoonId, @RequestParam Long categoryId) {
        var user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(categoryService.addDinatoonToCategory(user, categoryId, dinatoonId));
    }

    @DeleteMapping()
    @Operation(description = "Delete Category From User")
    public ResponseEntity<Void> deleteCategoryFromUser(Authentication authentication, @RequestParam Long categoryId) {
        var user = (User) authentication.getPrincipal();
        categoryService.deleteCategoryFromUser(user, categoryId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/dinatoon")
    @Operation(description = "Delete Dinatoon From Category")
    public ResponseEntity<Void> deleteDinatoonFromCategory(Authentication authentication, @RequestParam Long dinatoonId, @RequestParam Long categoryId) {
        var user = (User) authentication.getPrincipal();
        categoryService.deleteDinatoonFromCategory(user, categoryId, dinatoonId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping()
    @Operation(description = "Update Category Name")
    public ResponseEntity<Category> updateCategory(@RequestParam Long categoryId, @RequestParam String name) {
        return ResponseEntity.ok(categoryService.updateCategoryName(categoryId, name));
    }
}