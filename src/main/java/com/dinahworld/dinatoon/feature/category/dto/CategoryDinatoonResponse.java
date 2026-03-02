package com.dinahworld.dinatoon.feature.category.dto;

import com.dinahworld.dinatoon.feature.dinatoon.dto.LittleDinatoonDto;
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
