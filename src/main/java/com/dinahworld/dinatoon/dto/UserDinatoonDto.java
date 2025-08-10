package com.dinahworld.dinatoon.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserDinatoonDto {
    private UUID dinatoonId;
    private String comment;
    private Double rating;
    private Long listId;
    private String currentChapter;
}