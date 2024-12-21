package com.dinahworld.dinatoon.service;

import com.dinahworld.dinatoon.dto.DinatoonDto;
import com.dinahworld.dinatoon.model.Dinatoon;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

public interface DinatoonService {
    Dinatoon createDinatoon(DinatoonDto dto);

    Dinatoon getDinatoonById(Long id);

    void deleteUserDinatoon(Long userId, Long dinatoonId);

    @Transactional
    void deleteDinatoon(Long id);

    List<Dinatoon> getAllDinatoons();

    Dinatoon updateDinatoon(Long id, DinatoonDto dto);

    Mono<List<Dinatoon>> searchDinatoon(String title);

    Dinatoon saveManga(DinatoonDto dto, Long id);

    List<Dinatoon> getAllUserDinatoon(Long id);
}