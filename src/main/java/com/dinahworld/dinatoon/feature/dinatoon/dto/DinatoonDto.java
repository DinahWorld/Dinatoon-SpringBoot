package com.dinahworld.dinatoon.feature.dinatoon.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class DinatoonDto {
    private UUID id;
    private String name;
    private String genre;
    private String imageUrl;
    private String description;
    private Integer totalChapters;
}