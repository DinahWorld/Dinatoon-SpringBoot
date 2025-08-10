package com.dinahworld.dinatoon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDinatoonResponse {
    private Long id;
    private String name;
    private List<LittleDinatoonDto> userDinatoon;

    public CategoryDinatoonResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}