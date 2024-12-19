package com.dinahworld.dinatoon.service;

import com.dinahworld.dinatoon.dto.DinatoonDto;
import com.dinahworld.dinatoon.model.Dinatoon;
import reactor.core.publisher.Mono;

import java.util.List;

public interface DinatoonService {
    Dinatoon createDinatoon(DinatoonDto dto);

    Dinatoon getDinatoonById(Long id);

    void deleteDinatoon(Long id);

    List<Dinatoon> getAllDinatoons();

    Dinatoon updateDinatoon(Long id, DinatoonDto dto);

    Mono<List<Dinatoon>> searchDinatoon(String title);
}