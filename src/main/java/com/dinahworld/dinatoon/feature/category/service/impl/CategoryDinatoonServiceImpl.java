package com.dinahworld.dinatoon.feature.category.service.impl;

import com.dinahworld.dinatoon.feature.category.model.CategoryDinatoon;
import com.dinahworld.dinatoon.feature.category.repository.CategoryDinatoonRepository;
import com.dinahworld.dinatoon.feature.category.service.CategoryDinatoonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryDinatoonServiceImpl implements CategoryDinatoonService {
    private final CategoryDinatoonRepository categoryDinatoonRepository;


    @Override
    public List<CategoryDinatoon> getByUserDinatoonId(Long id) {
        return categoryDinatoonRepository.findAllByUserDinatoons(id).orElseThrow(() -> new NoSuchElementException("CategoryDinatoon found with id: " + id));
    }

    @Override
    public List<CategoryDinatoon> getAllByCategoryId(Long id) {
        return categoryDinatoonRepository.findAllByCategoryId(id).orElseThrow(() -> new NoSuchElementException("CategoryDinatoon found with id: " + id));
    }
}