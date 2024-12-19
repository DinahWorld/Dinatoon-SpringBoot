package com.dinahworld.dinatoon.dto;

import lombok.Data;

@Data
public class DinatoonDto {
    private String name;
    private String genre;
    private String imageUrl;
    private String description;
    private Integer totalChapters;
}