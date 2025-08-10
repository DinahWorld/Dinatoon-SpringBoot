package com.dinahworld.dinatoon.service;

import com.dinahworld.dinatoon.dto.DinatoonDto;
import com.dinahworld.dinatoon.dto.DinatoonUserDto;
import com.dinahworld.dinatoon.dto.LittleDinatoonDto;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface DinatoonService {
    Mono<List<DinatoonUserDto>> searchDinatoonByTitle(String title);

    Mono<List<DinatoonDto>> searchDinatoonById(UUID id);

    Mono<LittleDinatoonDto> searchLittleDinatoonById(UUID id);
}