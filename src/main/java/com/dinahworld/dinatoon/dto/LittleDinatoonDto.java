package com.dinahworld.dinatoon.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class LittleDinatoonDto {
    private UUID id;
    private String name;
    private String imageUrl;
}