package com.dinahworld.dinatoon.controller;

import com.dinahworld.dinatoon.dto.CategoryDinatoonResponse;
import com.dinahworld.dinatoon.model.Category;
import com.dinahworld.dinatoon.model.User;
import com.dinahworld.dinatoon.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/user-dinatoon")
    @Operation(description = "Get Category List With Dinatoon")
    public ResponseEntity<List<CategoryDinatoonResponse>> addDinatoonToACategory(Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(categoryService.getCategoryDinatoon(user));
    }


    @PutMapping()
    @Operation(description = "Update Category Name")
    public ResponseEntity<Category> updateCategory(@RequestParam Long categoryId, @RequestParam String name) {
        return ResponseEntity.ok(categoryService.updateCategoryName(categoryId, name));
    }
}